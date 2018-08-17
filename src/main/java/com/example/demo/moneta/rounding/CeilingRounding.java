package com.example.demo.moneta.rounding;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryRounding;
import javax.money.RoundingContext;
import javax.money.RoundingContextBuilder;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.OptionalInt;

/**
 * Ceiling rounding monetary operator with scale 0
 * 
 * @author Kisong
 */
public final class CeilingRounding implements MonetaryRounding {
	
	private final RoundingContext roundingContext;
	
	private final OptionalInt optionalScale;
	
	CeilingRounding() {
		this(0);
	}
	
	CeilingRounding(int scale) {
		this(RoundingContextBuilder.of("demo", "ceiling")
				.set("providerClass", CeilingRounding.class.getName())
				.set("scale", scale)
				.set(RoundingMode.CEILING)
				.build());
	}
	
	CeilingRounding(RoundingContext roundingContext) {
		this.roundingContext = roundingContext;
		this.optionalScale = OptionalInt.of(roundingContext.getInt("scale"));
	}

	@Override
	public MonetaryAmount apply(MonetaryAmount amount) {
		Objects.requireNonNull(amount, "[apply] amount required.");
		CurrencyUnit currency = amount.getCurrency();
		int scale = optionalScale.orElse(currency.getDefaultFractionDigits());
		BigDecimal value = amount.getNumber()
				.numberValue(BigDecimal.class)
				.setScale(scale, roundingContext.get(RoundingMode.class));
		return amount.getFactory()
				.setNumber(value)
				.create();
	}

	@Override
	public RoundingContext getRoundingContext() {
		return roundingContext;
	}

}
