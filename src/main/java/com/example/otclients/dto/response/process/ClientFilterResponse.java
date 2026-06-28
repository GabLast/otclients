package com.example.otclients.dto.response.process;

import com.example.otclients.dto.JsonResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ClientFilterResponse(List<ClientFilterData> data) implements JsonResponse {
}
