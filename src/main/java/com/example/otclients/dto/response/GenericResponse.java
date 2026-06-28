package com.example.otclients.dto.response;

import com.example.otclients.dto.JsonResponse;
import lombok.Builder;

@Builder
public record GenericResponse(
        ResponseInfo responseInfo
) implements JsonResponse {
}
