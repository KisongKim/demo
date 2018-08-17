package com.example.demo.model.repository;

import com.example.demo.model.XeExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XeExchangeRateRepository extends JpaRepository<XeExchangeRate, Long> {

    List<XeExchangeRate> findAllByBase(final String base);

}
