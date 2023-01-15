package com.betvictor.exchangerate.challenge.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Factory to create Monetary from Rate maps that contain entries currency-name / rate.
 */
public class MonetaryFactory {

    private static final Logger LOG = LoggerFactory.getLogger(MonetaryFactory.class);

    public static List<Monetary> createListFromRates(Map<String, Double> rates) {
        Objects.requireNonNull(rates);

        var result = new ArrayList<Monetary>();
        rates.forEach((key, value) -> {
            try {
                var val = BigDecimal.valueOf(value);
                var cur = SupportedCurrency.valueOf(key);

                result.add(Monetary.of(val, cur));
            } catch (Exception e) {
                LOG.error("Ignoring Failed Entry {}:{} to Rate Results due to : {}", key, value, e.getCause());
            }
        });
        return result;
    }
}
