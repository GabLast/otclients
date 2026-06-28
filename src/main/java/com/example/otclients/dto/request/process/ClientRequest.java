package com.example.otclients.dto.request.process;

import com.example.otclients.dto.JsonRequest;
import lombok.Builder;

import java.util.List;

@Builder
public record ClientRequest(Long id, String name, String lastName, String email, String phoneNumber,
                            List<AddressRequest> addresses) implements JsonRequest {
}
