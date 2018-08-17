package com.example.demo.moneta.convert;

import org.javamoney.moneta.convert.ExchangeRateBuilder;
import org.javamoney.moneta.spi.AbstractRateProvider;
import org.javamoney.moneta.spi.DefaultNumberValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.convert.ConversionContext;
import javax.money.convert.ConversionQuery;
import javax.money.convert.CurrencyConversionException;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ProviderContext;
import java.math.MathContext;
import java.util.Map;
import java.util.Objects;

/**
 * Abstract demo rate provider. this abstract class extends from AbstractRateProvider
 * 
 * @author Kisong
 */
public abstract class AbstractDemoRateProvider extends AbstractRateProvider {

	protected final String contextKey;

	protected final ProviderContext context;

	// base currency code
	protected final String baseCurrency;

	protected final CurrencyUnit baseCurrencyUnit;

	// exchange rate map, Key value is term currency code
	protected final Map<String, ExchangeRate> rates;

	private static final Logger logger = LoggerFactory.getLogger(AbstractDemoRateProvider.class);

	AbstractDemoRateProvider(String contextKey, ProviderContext context, String baseCurrencyCode, Map<String, ExchangeRate> rates) {
		super(context);
		this.contextKey = contextKey;
		this.context = context;
		this.baseCurrency = baseCurrencyCode;
		this.baseCurrencyUnit = Monetary.getCurrency(baseCurrencyCode);
		this.rates = rates;
	}
	
	@Override
	public ExchangeRate getExchangeRate(ConversionQuery conversionQuery) {
		String baseCurrency = conversionQuery.getBaseCurrency().getCurrencyCode();
		String currency = conversionQuery.getCurrency().getCurrencyCode();
		
		ExchangeRateBuilder builder = getExchangeRateBuilder(conversionQuery);
		ExchangeRate sourceRate = rates.get(baseCurrency);
		ExchangeRate targetRate = rates.get(currency);
		return createExchangeRate(conversionQuery, builder, sourceRate, targetRate);
	}
	
	protected abstract String getDataId();
	
	protected ExchangeRateBuilder getExchangeRateBuilder(ConversionQuery query) {
		ConversionContext context = getExchangeContext(contextKey);
		logger.debug("[getExchangeRateBuilder] context={}", context);
		
		ExchangeRateBuilder builder = new ExchangeRateBuilder(context);
        builder.setBase(query.getBaseCurrency());
        builder.setTerm(query.getCurrency());
        return builder;
	}
	
	private ExchangeRate createExchangeRate(ConversionQuery conversionQuery,
                                            ExchangeRateBuilder builder,
                                            ExchangeRate sourceRate,
                                            ExchangeRate target) {
		if (areBothBaseCurrencies(conversionQuery)) {
			builder.setFactor(DefaultNumberValue.ONE);
			return builder.build();
		} else if (baseCurrency.equals(conversionQuery.getCurrency().getCurrencyCode())) {
			if (Objects.isNull(sourceRate)) {
				return null;
			}
			return reverse(sourceRate);
		} else if (baseCurrency.equals(conversionQuery.getBaseCurrency().getCurrencyCode())) {
			return target;
		} else {
			ExchangeRate rate1 = getExchangeRate(conversionQuery.toBuilder()
					.setTermCurrency(Monetary.getCurrency(baseCurrency))
					.build());
			ExchangeRate rate2 = getExchangeRate(conversionQuery.toBuilder()
					.setBaseCurrency(Monetary.getCurrency(baseCurrency))
					.setTermCurrency(conversionQuery.getCurrency())
					.build());
			if (Objects.nonNull(rate1) && Objects.nonNull(rate2)) {
				builder.setFactor(multiply(rate1.getFactor(), rate2.getFactor()));
				builder.setRateChain(rate1, rate2);
				return builder.build();
			}
			throw new CurrencyConversionException(conversionQuery.getBaseCurrency(), conversionQuery.getCurrency(), sourceRate.getContext());
		}
	}

	private boolean areBothBaseCurrencies(ConversionQuery query) {
		return baseCurrency.equals(query.getBaseCurrency().getCurrencyCode()) &&
				baseCurrency.equals(query.getCurrency().getCurrencyCode());
	}
	
	private ExchangeRate reverse(ExchangeRate rate) {
        if (Objects.isNull(rate)) {
        	logger.error("[reverse] Param rate is null");
            throw new IllegalArgumentException("Rate null is not reversible.");
        }
        return new ExchangeRateBuilder(rate)
        		.setRate(rate)
        		.setBase(rate.getCurrency())
        		.setTerm(rate.getBaseCurrency())
        		.setFactor(divide(DefaultNumberValue.ONE, rate.getFactor(), MathContext.DECIMAL32))
                .build();
    }
	
}

