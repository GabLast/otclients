package com.example.otclients.dto.response;

import com.example.otclients.dto.JsonResponse;
import lombok.Builder;

@Builder
public record CountResponseData(Integer total) implements JsonResponse {
}
