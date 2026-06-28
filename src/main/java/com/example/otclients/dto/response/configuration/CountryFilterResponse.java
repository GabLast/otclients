package com.example.otclients.dto.response.configuration;

import com.example.otclients.dto.JsonResponse;
import lombok.Builder;

@Builder
public record CountryFilterResponse(CountryFilterData data) implements
        JsonResponse {
}
