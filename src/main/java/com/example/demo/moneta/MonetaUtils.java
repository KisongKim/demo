package com.example.demo.moneta;

import com.example.demo.moneta.rounding.CeilingRoundingFactoryImpl;
import com.example.demo.moneta.rounding.FloorRoundingFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.money.MonetaryAmount;
import javax.money.MonetaryRounding;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MonetaUtils {

    private static final Logger logger = LoggerFactory.getLogger(MonetaUtils.class);

    /**
     * Instantiate the BigDecimal from MonetaryAmount
     *
     * @param amount instance of MonetaryAmount
     * @return instace of BigDecimal
     */
    public static BigDecimal from(MonetaryAmount amount) {
        if (amount == null) {
            logger.debug("[from] Zero value returns");
            return new BigDecimal("0");
        }
        return new BigDecimal(amount.getNumber().toString()).setScale(8, RoundingMode.HALF_EVEN);
    }

    public static MonetaryAmount applyCeilingRounding(MonetaryAmount source) {
        MonetaryRoundingFactory factory = new CeilingRoundingFactoryImpl();
        MonetaryRounding rounding = factory.monetaryRounding(0);
        logger.debug("[applyCeilingRounding] Rounding={}", rounding);
        return source.with(rounding);
    }

    public static MonetaryAmount applyFloorRounding(MonetaryAmount source) {
        MonetaryRoundingFactory factory = new FloorRoundingFactoryImpl();
//		MonetaryRounding rounding = factory.monetaryRounding(2);
        MonetaryRounding rounding = factory.monetaryRounding(6);
        logger.debug("[applyFloorRounding] Rounding={}", rounding);
        return source.with(rounding);
    }

}
