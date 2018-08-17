package com.example.demo.moneta.convert;

import javax.money.convert.ExchangeRate;
import javax.money.convert.ProviderContext;
import java.io.Serializable;
import java.util.Map;

/**
 * Demo exchange rate provider
 *
 * @author Kisong
 */
public class DemoRateProvider extends AbstractDemoRateProvider implements Serializable {

    private static final String DATA_ID = DemoRateProvider.class.getSimpleName();

    /*
     * package private static factory method of DemoRateProvider
     */
    static DemoRateProvider of(final String contextKey,
                               final ProviderContext context,
                               final String baseCurrency,
                               final Map<String, ExchangeRate> rate) {
        return new DemoRateProvider(contextKey, context, baseCurrency, rate);
    }

    DemoRateProvider(final String contextKey,
                     final ProviderContext context,
                     final String baseCurrency,
                     final Map<String, ExchangeRate> rates) {
        super(contextKey, context, baseCurrency, rates);
    }

    @Override
    protected String getDataId() {
        return DATA_ID;
    }

}
