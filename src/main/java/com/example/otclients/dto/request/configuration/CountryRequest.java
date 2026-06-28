package com.example.otclients.dto.request.configuration;

import com.example.otclients.dto.JsonRequest;
import lombok.Builder;

@Builder
public record CountryRequest(
        Long id,
        String name,
        String code,
        String placeId
) implements JsonRequest {
}
