package com.betvictor.exchangerate.challenge.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ConverterConfiguration implements WebMvcConfigurer {

    @Autowired
    private List<Converter> converters;

    public void addFormatters(FormatterRegistry registry) {
        converters.forEach(registry::addConverter);
    }
}