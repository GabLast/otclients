package com.example.otclients.dto.response.process;

import com.example.otclients.dto.JsonResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ClientData(Long id, String name, String lastName, String fullName, String email,
                         String phoneNumber, List<AddressData> addresses) implements JsonResponse {
}
