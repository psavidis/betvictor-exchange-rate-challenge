package com.betvictor.exchangerate.challenge.app.model.service;

import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateListResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ExchangeRateResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionListResponse;
import com.betvictor.exchangerate.challenge.app.web.dto.response.ValueConversionResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class NotificationService {

    private final RestTemplate template;

    public NotificationService(RestTemplate template) {
        this.template = template;
    }

    public void notify(UUID contextId, String url, ExchangeRateListResponse data) {
        template.postForEntity(getFormatedUrl(url, contextId), data, Void.class);
    }

    public void notify(UUID contextId, String url, ExchangeRateResponse data) {
        template.postForEntity(getFormatedUrl(url, contextId), data, Void.class);
    }

    public void notify(UUID contextId, String url, ValueConversionResponse data) {
        template.postForEntity(getFormatedUrl(url, contextId), data, Void.class);
    }

    public void notify(UUID contextId, String url, ValueConversionListResponse data) {
        template.postForEntity(getFormatedUrl(url, contextId), data, Void.class);
    }

    private String getFormatedUrl(String url, UUID contexId) {
        return url + "?contextId=" + contexId;
    }
}