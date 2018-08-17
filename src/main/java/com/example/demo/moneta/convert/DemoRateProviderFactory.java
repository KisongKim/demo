package com.example.demo.moneta.convert;

import com.example.demo.ServiceError;
import com.example.demo.ServiceException;
import com.example.demo.moneta.DemoExchangeRate;
import org.javamoney.moneta.convert.ExchangeRateBuilder;
import org.javamoney.moneta.spi.DefaultNumberValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.convert.ConversionContextBuilder;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.ProviderContext;
import javax.money.convert.ProviderContextBuilder;
import javax.money.convert.RateType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 코어 환율 프로바이더 팩토리
 * 
 * @author Kisong
 *
 */
public class DemoRateProviderFactory {

	private static final Logger logger = LoggerFactory.getLogger(DemoRateProviderFactory.class);

	/**
	 * 익스체인지 프로바이더 타입
	 * 
	 * @author Kisong
	 *
	 */
	enum ProviderType {
		
		HKRS_SPREAD ("DEMO-XE-SPREAD", "Demo xe.com spread"),
		
		HKRS_NON_SPREAD ("DEMO-XE-NON-SPREAD", "Demo xe.com non-spread");

		final String provider;
		final String contextDesc;

		ProviderType(final String provider, final String contextDesc) {
			this.provider = provider;
			this.contextDesc = contextDesc;
		}
	}

	/**
	 * static factory method of ExchangeRateProvider
	 *
	 * @param base base currency code
	 * @param demoRates list of DemoExchangeRate
	 * @param spread spread rate flag
	 * @return instance of DemoRateProvider
	 * @throws ServiceException error occurred
	 */
	public static ExchangeRateProvider of(String base, List<DemoExchangeRate> demoRates, boolean spread)
			throws ServiceException {
		if (base == null) {
			logger.error("[of] Base currency code is null.");
			throw new ServiceException(ServiceError.INTERNAL_SERVER_ERROR, new IllegalArgumentException());
		}
		if (demoRates.isEmpty()) {
			logger.error("[of] Provided customRates is empty.");
			throw new ServiceException(ServiceError.INTERNAL_SERVER_ERROR, new IllegalArgumentException());
		}

		ProviderType providerType = spread == true ? ProviderType.HKRS_SPREAD : ProviderType.HKRS_NON_SPREAD;
		if (providerType == null) {
			logger.error("[of] Unsupported base currency for exchange rate, base={}", base);
			throw new ServiceException(ServiceError.INVALID_PARAMETER, new IllegalStateException());
		}
		// 프로바이더 컨텍스트 빌더
		ProviderContext context = ProviderContextBuilder.of(providerType.provider, RateType.DEFERRED)
				.set("providerDescription", providerType.contextDesc)
				.set("days", 1)
				.build();
		Map<String, ExchangeRate> rates = coreRatesToMap(context, base, demoRates);
		return DemoRateProvider.of(providerType.provider, context, base, rates);
	}
	
	/**
	 * convert DemoExchangeRate to Map with term currency code as key and {@link javax.money.convert.ExchangeRate} as value
	 * 
	 * @param context Provider context
	 * @param baseCurrency base currency code
	 * @param demoRates list of DemoExchangeRate
	 * @return instance of Map with term currency code as key and ExchangeRate as value
	 */
	private static Map<String, ExchangeRate> coreRatesToMap(ProviderContext context,
                                                            String baseCurrency,
                                                            List<DemoExchangeRate> demoRates) {
		// 리턴할 맵
		Map<String, ExchangeRate> rates = new HashMap<>();
		// 기준통화 유닛
		CurrencyUnit baseCurrencyUnit = Monetary.getCurrency(baseCurrency);
		// 익스체인지 레이트 빌더
		ExchangeRateBuilder builder = new ExchangeRateBuilder(ConversionContextBuilder
				.create(context, RateType.DEFERRED)
				.build());
		// 데이터베이스에서 조회한 xe.com 의  환율정보 리스트를 순회하면서 모네타 환율정보를 설정한다.
		for (DemoExchangeRate demoRate : demoRates) {
			// 환율정보를 위한 대상 통화코드, 통화코드는 ISO 에서 지정한 3자리 표준 통화코드이다. KRW, USD
			String termCurrency = demoRate.getTerm();

			// 대상통화 유닛을 조회하여 여기에 기준,타겟 통화 유닛을 설정한다.
			builder.setBase(baseCurrencyUnit);
			builder.setTerm(Monetary.getCurrency(termCurrency));
			builder.setFactor(DefaultNumberValue.of(demoRate.getRate()));
			ExchangeRate exchangeRate = builder.build();

			rates.put(termCurrency, exchangeRate);
		}
		return rates;
	}

}
