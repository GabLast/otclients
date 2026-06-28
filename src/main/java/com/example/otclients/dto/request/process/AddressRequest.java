package com.example.otclients.dto.request.process;

import com.example.otclients.dto.JsonRequest;
import lombok.Builder;

@Builder
public record AddressRequest(Long id, Long clientId, Long countryId,
                             String fullName, String city, String stateProvince, String street, String zipCode,
                             String building,
                             String phoneNumber) implements JsonRequest {
}
