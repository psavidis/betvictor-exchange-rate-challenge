package com.betvictor.exchangerate.challenge.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Factory to create Monetary from Rate maps that contain entries currency-name / rate.
 */
public class MonetaryFactory {

    public static List<Monetary> createListFromRates(Map<String, Double> rates) {
        Objects.requireNonNull(rates);

        var result = new ArrayList<Monetary>();
        rates.forEach((key, value) -> {

            var val = BigDecimal.valueOf(value);
            var cur = SupportedCurrency.valueOf(key);

            result.add(Monetary.of(val, cur));
        });
        return result;
    }
}
