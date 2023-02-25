package com.currencyconverter.repository;

import com.currencyconverter.model.Converter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConverterRepository extends JpaRepository<Converter,Long> {
   // public List<Converter> findBycurrencyChange(String currency);
    Optional<Converter> findBycurrencyChange(String currency);
}
