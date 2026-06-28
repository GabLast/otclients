package com.example.otclients.services.command.process;

import com.example.otclients.dto.request.process.AddressRequest;
import com.example.otclients.dto.response.process.AddressData;
import com.example.otclients.dto.response.process.AddressResponse;
import com.example.otclients.exceptions.InvalidActionException;
import com.example.otclients.exceptions.ResourceNotFoundException;
import com.example.otclients.models.configurations.Country;
import com.example.otclients.models.process.Address;
import com.example.otclients.models.process.Client;
import com.example.otclients.repositories.process.AddressRepository;
import com.example.otclients.services.BaseService;
import com.example.otclients.services.query.configuration.CountryQueryService;
import com.example.otclients.services.query.process.AddressQueryService;
import com.example.otclients.services.query.process.ClientQueryService;
import com.example.otclients.utils.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AddressCommandService extends BaseService<Address, Long> {

    private final AddressRepository repository;
    private final CountryQueryService countryQueryService;
    private final AddressQueryService addressQueryService;
    private final ClientQueryService clientQueryService;

    @Override
    protected JpaRepository<Address, Long> getRepository() {
        return repository;
    }

    public AddressResponse saveAddress(AddressRequest request, boolean update) {

        Client client = clientQueryService.get(request.clientId()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Client with ID %d does not exist", request.clientId())));

        if(!client.isEnabled()) {
            throw new ResourceNotFoundException(String.format("Client with ID %d does not exist", request.clientId()));
        }

        if (request.countryId() == null || request.countryId() <= 0L) {
            throw new InvalidActionException(String.format("Address %s has no country", request.fullName()));
        }

        Country country = countryQueryService.get(request.countryId()).orElse(null);
        if (country == null) {
            throw new InvalidActionException(String.format("Address %s has selected an invalid country", request.fullName()));
        }

        String addressFullName = Utilities.cleanString(request.fullName());
        String street = Utilities.normalizeAddressFields(request.street());
        String building = Utilities.normalizeAddressFields(request.building());
        String city = Utilities.normalizeAddressFields(request.city());
        String stateProvince = Utilities.normalizeAddressFields(request.stateProvince());
        String addressPhoneNumber = Utilities.cleanPhoneNumber(request.phoneNumber());
        String zipCode = Utilities.cleanZipCode(request.zipCode());

        if (!StringUtils.hasText(addressFullName)) {
            throw new NullPointerException("Address name can not be empty");
        }
        if (!StringUtils.hasText(street)) {
            throw new NullPointerException("Address street can not be empty");
        }
        if (!StringUtils.hasText(building)) {
            throw new NullPointerException("Address building can not be empty");
        }
        if (!StringUtils.hasText(city)) {
            throw new NullPointerException("Address city can not be empty");
        }
        if (!StringUtils.hasText(stateProvince)) {
            throw new NullPointerException("Address state / province can not be empty");
        }
        if (!StringUtils.hasText(addressPhoneNumber)) {
            throw new NullPointerException("Address phone number can not be empty");
        }
        if (!StringUtils.hasText(zipCode)) {
            throw new NullPointerException("Address zip code can not be empty");
        }

        Address addressToSave = null;
        if (request.id() != null && request.id() > 0L) {
            addressToSave = addressQueryService.findById(request.id()).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Address with ID %d does not exist", request.id())));
        }

        if(update && addressToSave == null) {
            throw new ResourceNotFoundException("Address to update was not sent");
        }

        if (addressToSave == null) {
            addressToSave = Address.builder()
                    .client(client)
                    .fullName(addressFullName)
                    .country(country)
                    .phoneNumber(addressPhoneNumber)
                    .street(street)
                    .building(building)
                    .city(city)
                    .stateProvince(stateProvince)
                    .zipCode(zipCode)
                    .build();
        } else {
            addressToSave.setClient(client);
            addressToSave.setCountry(country);
            addressToSave.setFullName(addressFullName);
            addressToSave.setPhoneNumber(addressPhoneNumber);
            addressToSave.setStreet(street);
            addressToSave.setBuilding(building);
            addressToSave.setCity(city);
            addressToSave.setZipCode(zipCode);
            addressToSave.setStateProvince(stateProvince);
        }

        addressToSave = saveAndFlush(addressToSave);

        return buildAddressResponse(addressToSave);
    }

    public AddressResponse deleteAddress(Long id) {
        Optional<Address> object = get(id);
        if (object.isEmpty() || !object.get().isEnabled()) {
            throw new ResourceNotFoundException("Address does not exist");
        }
        disable(object.get());
        return buildAddressResponse(object.get());
    }

    private AddressResponse buildAddressResponse(Address addressToSave) {
        return AddressResponse.builder()
                .data(AddressData.builder()
                        .id(addressToSave.getId())
                        .clientId(addressToSave.getClient().getId())
                        .countryId(addressToSave.getCountry().getId())
                        .country(addressToSave.getCountry().getName())
                        .fullName(addressToSave.getFullName())
                        .city(addressToSave.getCity())
                        .stateProvince(addressToSave.getStateProvince())
                        .street(addressToSave.getStreet())
                        .building(addressToSave.getBuilding())
                        .zipCode(addressToSave.getZipCode())
                        .build())
                .build();
    }
}
