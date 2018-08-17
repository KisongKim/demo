package com.example.demo.moneta;

import com.example.demo.ServiceException;
import com.example.demo.model.XeExchangeRate;
import com.example.demo.model.repository.XeExchangeRateRepository;
import com.example.demo.moneta.convert.DemoRateProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.money.convert.ExchangeRateProvider;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DemoExchangeRateServiceImpl implements DemoExchangeRateService {

    private XeExchangeRateRepository xeExchangeRateRepository;

    private static final Logger logger = LoggerFactory.getLogger(DemoExchangeRateServiceImpl.class);

    @Autowired
    public DemoExchangeRateServiceImpl(final XeExchangeRateRepository xeExchangeRateRepository) {
        this.xeExchangeRateRepository = xeExchangeRateRepository;
    }

    @Override
    public ExchangeRateProvider exchangeRateProvider(final String base) throws ServiceException {
        List<XeExchangeRate> exchangeRates = xeExchangeRateRepository.findAllByBase(base);
        List<DemoExchangeRate> demoExchangeRates = new ArrayList<>();
        for (XeExchangeRate exchangeRate : exchangeRates) {
            BigDecimal rate = exchangeRate.getRate();
            BigDecimal scaledRate = rate.setScale(8, RoundingMode.HALF_EVEN);

            logger.debug("[exchangeRateProvider] Read rate={}, scaledRate={}", rate, scaledRate);

            demoExchangeRates.add(new DemoExchangeRate(exchangeRate.getBase(), exchangeRate.getTerm(), scaledRate));
        }
        return DemoRateProviderFactory.of(base, demoExchangeRates, false);
    }

    @Override
    public ExchangeRateProvider exchangeRateProviderWithSpread(final String base, final BigDecimal spread) throws ServiceException {
        List<XeExchangeRate> exchangeRates = xeExchangeRateRepository.findAllByBase(base);
        List<DemoExchangeRate> demoExchangeRates = new ArrayList<>();
        for (XeExchangeRate exchangeRate : exchangeRates) {
            BigDecimal rate = exchangeRate.getRate();
            BigDecimal scaledRate = rate.multiply(spread).setScale(8, RoundingMode.HALF_EVEN);

            logger.debug("[exchangeRateProviderWithSpread] Read rate={}, scaledRate={}", rate, scaledRate);

            demoExchangeRates.add(new DemoExchangeRate(exchangeRate.getBase(), exchangeRate.getTerm(), scaledRate));
        }
        return DemoRateProviderFactory.of(base, demoExchangeRates, false);
    }

}
