package com.example.otclients.dto.response.process;

import com.example.otclients.dto.JsonResponse;
import lombok.Builder;

@Builder
public record ClientResponse(ClientData data) implements JsonResponse {
}
