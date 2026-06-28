package com.example.otclients.dto.response;

import com.example.otclients.dto.JsonResponse;
import lombok.Builder;

@Builder
public record ResponseInfo(String message, String path, int status)
        implements JsonResponse {

}
