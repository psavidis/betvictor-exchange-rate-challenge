package com.betvictor.exchangerate.challenge.app.model.converter;

import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionListResponse;
import com.betvictor.exchangerate.challenge.client.response.ConversionResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConversionResponseToValueConversionListResponseConverter implements Converter<ConversionResponse, ValueConversionListResponse> {

    @Override
    public ValueConversionListResponse convert(ConversionResponse source) {
        return new ValueConversionListResponse(
                source.monetary(),
                source.conversions()
        );
    }
}
