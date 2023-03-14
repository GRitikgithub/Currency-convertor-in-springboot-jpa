package com.currencyconverter.controller;

import com.currencyconverter.exception.DataNotFoundException;
import com.currencyconverter.model.Converter;
import com.currencyconverter.repository.ConverterRepository;
import com.currencyconverter.service.ConverterService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@Slf4j
public class ConverterController {
    @Autowired
    ConverterRepository converterRepository;

    @Autowired
    ConverterService converterService;

    @Value("${app.check}")
    private String appcheck;

    @GetMapping("/getAllData")
    public List<Converter> getData(){
        log.info("Fetch all data from database.....");
        return converterService.getAllData();
    }

    @GetMapping("/getDataById/{id}")
    public Optional<Converter> getData(@PathVariable("id") Integer id) throws DataNotFoundException {
        return converterService.getDataById(id);
    }

    @GetMapping("/getDataByCurrency/{currency}")
    public Optional<Converter> getData(@PathVariable("currency") String currency){
        return converterService.getDataByCurrency(currency);
    }
    @GetMapping("/currencyChange/{currency}/{amount}")
    public double getRate(@PathVariable String currency, @PathVariable double amount){
        return converterService.getResult(currency,amount);
    }

    @PostMapping("/insertData")
    public String addData(@RequestBody @Valid Converter converter){
        this.converterService.saveOrUpdateData(converter);
        return "Data is insert successfully....";
    }
    @DeleteMapping("/deleteDataById/{id}")
    public String delete(@PathVariable Integer id){
        this.converterService.deleteData(id);
        return "Conversion data is is deleted successfully with id "+id;
    }
    @PutMapping("/updateRateById/{id}")
    public String update(@PathVariable Integer id,@RequestBody @Valid Converter newConverter){
        log.info("Modify rate by particular id:"+id);
        newConverter.setId(id);
        this.converterService.saveOrUpdateData(newConverter);
        return "Rate update successfully...";
    }

    @GetMapping("/check")
    public String healthCheck(){
        log.info("HealthCheck");
        return appcheck;
    }
}
