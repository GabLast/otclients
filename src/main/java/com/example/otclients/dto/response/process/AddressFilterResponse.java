package com.example.otclients.dto.response.process;

import com.example.otclients.dto.JsonResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record AddressFilterResponse(List<AddressFilterData> data) implements JsonResponse {
}
