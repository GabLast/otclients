package com.example.otclients.dto.response.configuration;

import com.example.otclients.dto.JsonResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record CountryFilterData(List<CountryFilterDataDetails> dataList) implements
        JsonResponse {
}
