package com.betvictor.exchangerate.challenge.app.model.factory;

import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueListRequest;
import com.betvictor.exchangerate.challenge.app.web.dto.request.ConvertValueRequest;
import com.betvictor.exchangerate.challenge.domain.Monetary;

/**
 * Factory that creates {@link Monetary} from requests.
 */
public class MonetaryFactory {

    public static Monetary create(ConvertValueRequest request) {
        var currency = request.currency();
        var value = request.value();

        return Monetary.of(value, currency);
    }

    public static Monetary create(ConvertValueListRequest request) {
        var currency = request.currency();
        var value = request.value();

        return Monetary.of(value, currency);
    }

}
