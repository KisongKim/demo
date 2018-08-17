package com.example.demo.moneta;

import javax.money.MonetaryAmount;
import javax.money.MonetaryRounding;

/**
 * Monetary rounding factory interface
 * 
 * @author Kisong
 *
 */
public interface MonetaryRoundingFactory {
	
	/**
	 * Instantiate the Demo application default MonetaryRounding operator
	 * 
	 * <p>
	 * 	{@code CeilingRounding} use scale 0, {@code FloorRounding} use scale 2
	 * </p>
	 * 
	 * @return instance of MonetaryRounding
	 */
	MonetaryRounding monetaryRounding();
	
	/**
	 * Instantiate the MonetaryRounding operator with given scale
	 * 
	 * @param scale scale
	 * @return instance of MonetaryRounding
	 */
	MonetaryRounding monetaryRounding(int scale);
	
	/**
	 * Instantiate the MonetaryRounding with general manner
	 * 
	 * @param amount
	 * @return instance of MonetaryRounding
	 */
	MonetaryRounding monetaryRounding(MonetaryAmount amount);
	
}
