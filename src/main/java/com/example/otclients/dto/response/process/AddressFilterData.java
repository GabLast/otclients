package com.example.otclients.dto.response.process;

import com.example.otclients.dto.JsonResponse;
import lombok.Builder;

@Builder
public record AddressFilterData(Long id, Long clientId,
                                String fullName,
                                Long countryId, String country,
                                String city, String stateProvince, String street, String zipCode,
                                String building,
                                String phoneNumber) implements JsonResponse {
}
