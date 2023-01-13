package com.betvictor.exchangerate.challenge.app.model.converter;

import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionResponse;
import com.betvictor.exchangerate.challenge.client.response.ConversionResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConversionResponseToValueConversionResponseConverter implements Converter<ConversionResponse, ValueConversionResponse> {

    @Override
    public ValueConversionResponse convert(ConversionResponse source) {
        return new ValueConversionResponse(
                source.monetary(),
                source.conversions().get(0)
        );
    }
}
