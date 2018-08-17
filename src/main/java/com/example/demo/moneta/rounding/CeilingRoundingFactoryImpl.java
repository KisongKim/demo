package com.example.demo.moneta.rounding;

import com.example.demo.moneta.MonetaryRoundingFactory;

import javax.money.MonetaryAmount;
import javax.money.MonetaryRounding;
import java.util.Objects;

/**
 * @author Kisong
 *
 */
public final class CeilingRoundingFactoryImpl implements MonetaryRoundingFactory {

	@Override
	public MonetaryRounding monetaryRounding() {
		return new CeilingRounding();
	}

	@Override
	public MonetaryRounding monetaryRounding(int scale) {
		return new CeilingRounding(scale);
	}
	
	@Override
	public MonetaryRounding monetaryRounding(MonetaryAmount amount) {
		Objects.requireNonNull(amount, "[monetaryRounding] amount required.");
		return new CeilingRounding(amount.getCurrency().getDefaultFractionDigits());
	}
	
}
