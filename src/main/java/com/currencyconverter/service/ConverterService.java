package com.currencyconverter.service;

import com.currencyconverter.exception.DataNotFoundException;
import com.currencyconverter.model.Converter;
import com.currencyconverter.repository.ConverterRepository;
import com.currencyconverter.util.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class ConverterService {
    @Autowired
    ConverterRepository converterRepository;

    @Autowired
    EmailService emailService;

    public List<Converter> getAllData() {
        return (List<Converter>) converterRepository.findAll();
    }

    public Optional<Converter> getDataById(Integer id) throws DataNotFoundException {
        Optional<Converter> converter= converterRepository.findById(id);
        if(converter.isPresent()){
            log.info("Fetch specific data by id:"+id);
            return converter;
        }else{
            log.info("Data not found with id:"+id);
            throw new DataNotFoundException("Data not found with id:"+id);
        }
    }

    public Optional<Converter> getDataByCurrency(String currency) {
        Optional<Converter> converter=converterRepository.findBycurrencyChange(currency);
        if(converter.isPresent()){
            log.info("Fetch specific data by currency:"+currency);
            return converter;
        }else{
            log.info("Data not found with currency:"+currency);
            throw new DataNotFoundException("Data not found with currency:"+currency);
        }
    }

    public double getResult(String currency, double amount) {
        Optional<Converter> rt = converterRepository.findBycurrencyChange(currency);
        if(rt.isPresent()){
            log.info("Currency change "+currency+" with amount "+amount+"...");
            double rate=rt.get().getRate();
            return amount*rate;
        }else{
            log.info("Rate is not found in database with currency "+currency);
            throw new DataNotFoundException("Conversion rate is not available in database of currency "+currency);
        }
    }

    public void deleteData(Integer id) {
        Optional<Converter> converter = converterRepository.findById(id);
        if (converter.isPresent()) {
            log.info("Delete tuple with id:"+id);
            converterRepository.delete(converter.get());
            emailService.sendEmail("ritik.gupta@cstinfotech.co.in", "Data Delete in Currency Converter",
                    "Data is deleted successfully with id " + id);
        }else{
            log.info("Tuple is not found with id:"+id);
            throw new DataNotFoundException("Tuple is not available in database with id:"+id);
        }
    }

    public ResponseEntity<Converter> saveOrUpdateData(Converter newConverter) {
        Converter converter = new Converter();
        if(ObjectUtils.isNotEmpty(newConverter)) {
            if (ObjectUtils.isNotEmpty(newConverter.getId())) {

                Optional<Converter> converterOptional = converterRepository.findById(newConverter.getId());
                if (converterOptional.isPresent()) {
                    converter = converterOptional.get();
                }else{
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
                converter = new Converter();
                converter.setCreatedDate(new Date());
                converter.setCurrencyChange(newConverter.getCurrencyChange());
            }
            converter.setRate(newConverter.getRate());
            converter.setLastModifiedDate(new Date());
            log.info("Saving data");
            Converter saveData = converterRepository.save(converter);
            emailService.sendEmail("ritik.gupta@cstinfotech.co.in",
                    "Data Insert/update in Currency Converter",
                    "New data insert/update successfully with currency " + saveData.getCurrencyChange());
            return new ResponseEntity<>(saveData, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}