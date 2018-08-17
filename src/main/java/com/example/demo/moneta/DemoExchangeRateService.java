package com.example.demo.moneta;

import com.example.demo.ServiceException;

import javax.money.convert.ExchangeRateProvider;
import java.math.BigDecimal;

/**
 * Demo exchange rate service
 *
 * @author Kisong
 */
public interface DemoExchangeRateService {

    /**
     * Gets the ExchangeRateProvider with base currency code and exchange rates from xe.com
     *
     * @param base base currency code
     * @return instance of ExchangeRateProvider
     * @throws ServiceException error occurred
     */
    ExchangeRateProvider exchangeRateProvider(final String base) throws ServiceException;

    /**
     * Gets the spread ExchangeRateProvider with base currency code and exchange rates from xe.com
     *
     * @param base base currency code
     * @param spread spread ratio
     * @return instance of ExchangeRateProvider
     * @throws ServiceException error occurred
     */
    ExchangeRateProvider exchangeRateProviderWithSpread(final String base, final BigDecimal spread) throws ServiceException;

}
