package com.example.otclients.dto.response.configuration;

import com.example.otclients.dto.JsonResponse;
import lombok.Builder;

@Builder
public record CountryFindAllResponse(CountryFindAllData data) implements JsonResponse {
}
