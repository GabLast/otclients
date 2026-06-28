package com.example.otclients.adapter;

import com.example.otclients.dto.response.process.AddressData;
import com.example.otclients.dto.response.process.ClientData;
import com.example.otclients.dto.response.process.ClientResponse;
import com.example.otclients.models.process.Address;
import com.example.otclients.models.process.Client;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ClientProcessAdapter {

    public static ClientResponse buildClientResponse(Client client, List<Address> addresses) {
        return ClientResponse.builder()
                .data(ClientData.builder()
                        .id(client.getId())
                        .name(client.getName())
                        .lastName(client.getLastName())
                        .email(client.getEmail())
                        .phoneNumber(client.getPhoneNumber())
                        .fullName(client.getFullName())
                        .addresses(addressesToResponse(addresses))
                        .build())
                .build();
    }

    private static List<AddressData> addressesToResponse(List<Address> list) {
        if (CollectionUtils.isEmpty(list))
            return null;

        return list.stream().map(ClientProcessAdapter::buildAddressData).toList();
    }

    public static AddressData buildAddressData(Address address) {
        return AddressData.builder()
                .id(address.getId())
                .clientId(address.getClient().getId())
                .countryId(address.getCountry().getId())
                .country(address.getCountry().getName())
                .fullName(address.getFullName())
                .city(address.getCity())
                .stateProvince(address.getStateProvince())
                .street(address.getStreet())
                .building(address.getBuilding())
                .zipCode(address.getZipCode())
                .phoneNumber(address.getPhoneNumber())
                .build();
    }
}
