package com.example.otclients.dto.response.configuration;

import com.example.otclients.dto.JsonResponse;
import lombok.Builder;

@Builder
public record CountryFilterDataDetails(
        Long id,
        String name,
        String code,
        String placeId
) implements JsonResponse {
}
