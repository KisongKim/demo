/**
 * 
 */
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
 * Ceiling rounding monetary operator with scale 2
 * 
 * @author Kisong
 */
public final class FloorRounding implements MonetaryRounding {

	private final RoundingContext roundingContext;
	
	private final OptionalInt optionalScale;
	
	FloorRounding() {
		this(2);
	}
	
	FloorRounding(int scale) {
		this(RoundingContextBuilder.of("demo", "floor")
				.set("ProviderClass", FloorRounding.class.getName())
				.set("scale", scale)
				.set(RoundingMode.FLOOR)
				.build());
	}
	
	FloorRounding(RoundingContext roundingContext) {
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
