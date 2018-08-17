/**
 * 
 */
package com.example.demo.moneta.rounding;

import com.example.demo.moneta.MonetaryRoundingFactory;

import javax.money.MonetaryAmount;
import javax.money.MonetaryRounding;
import java.util.Objects;

/**
 * @author Kisong
 *
 */
public class FloorRoundingFactoryImpl implements MonetaryRoundingFactory {

	@Override
	public MonetaryRounding monetaryRounding() {
		return new FloorRounding();
	}

	@Override
	public MonetaryRounding monetaryRounding(int scale) {
		return new FloorRounding(scale);
	}

	@Override
	public MonetaryRounding monetaryRounding(MonetaryAmount amount) {
		Objects.requireNonNull(amount, "[monetaryRounding] amount required.");
		return new FloorRounding(amount.getCurrency().getDefaultFractionDigits());
	}

}
