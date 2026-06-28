package com.example.otclients.services.command.process;

import com.example.otclients.adapter.ClientProcessAdapter;
import com.example.otclients.dto.request.process.AddressRequest;
import com.example.otclients.dto.request.process.ClientRequest;
import com.example.otclients.dto.response.process.ClientResponse;
import com.example.otclients.exceptions.InvalidActionException;
import com.example.otclients.exceptions.InvalidDataFormat;
import com.example.otclients.exceptions.ResourceExistsException;
import com.example.otclients.exceptions.ResourceNotFoundException;
import com.example.otclients.models.configurations.Country;
import com.example.otclients.models.process.Address;
import com.example.otclients.models.process.Client;
import com.example.otclients.services.query.configuration.CountryQueryService;
import com.example.otclients.services.query.process.AddressQueryService;
import com.example.otclients.services.query.process.ClientQueryService;
import com.example.otclients.utils.Utilities;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientProcessCommandService {

    private final ClientCommandService clientCommandService;
    private final AddressCommandService addressCommandService;

    private final ClientQueryService clientQueryService;
    private final AddressQueryService addressQueryService;

    private final CountryQueryService countryQueryService;

    public ClientResponse saveClient(ClientRequest request, boolean update) {

        String name = Utilities.cleanString(request.name());
        String lastName = Utilities.cleanString(request.lastName());
        String email = Utilities.cleanEmail(request.email());
        String phoneNumber = Utilities.cleanPhoneNumber(request.phoneNumber());

        if (!StringUtils.hasText(name)) {
            throw new NullPointerException("Client name can not be empty");
        }

        if (!StringUtils.hasText(lastName)) {
            throw new NullPointerException("Client last name can not be empty");
        }

        if (!StringUtils.hasText(email)) {
            throw new NullPointerException("Client email can not be empty");
        }

        if (!Utilities.isValidEmail(email)) {
            throw new InvalidDataFormat("Invalid e-mail");
        }

        if (StringUtils.hasText(phoneNumber) && !Utilities.isValidPhoneNumber(phoneNumber)) {
            throw new InvalidDataFormat("Invalid phone number. Please follow the standard international numbers like +14155552671");
        }

        Client objectSave = null;
        if (request.id() != null && request.id() > 0L) {
            objectSave = clientQueryService.get(request.id()).orElseThrow(() ->
                    new ResourceNotFoundException(String.format("Client with ID %d does not exist", request.id())));
        }

        if(update && objectSave == null) {
            throw new ResourceNotFoundException("Client to update was not sent");
        }

        Client tmp = clientQueryService.findByEmailAndEnabled(request.email(), true);
        if (tmp != null && !tmp.getId().equals(request.id())) {
            throw new ResourceExistsException(
                    "This mail has already been taken. Please select a new one");
        }

        if (objectSave == null) {
            objectSave = Client.builder()
                    .name(name)
                    .lastName(lastName)
                    .email(email)
                    .phoneNumber(phoneNumber)
                    .build();
        } else {
            objectSave.setName(name);
            objectSave.setLastName(lastName);
            objectSave.setEmail(email);
            objectSave.setPhoneNumber(phoneNumber);
        }

        objectSave = clientCommandService.save(objectSave);

        List<Address> addresses = new ArrayList<>();

        if (!CollectionUtils.isEmpty(request.addresses())) {
            for (AddressRequest address : request.addresses()) {

                if (request.id() != null && request.id() > 0L &&
                        address.clientId() != null && address.clientId() > 0L &&
                        !request.id().equals(address.clientId())) {
                    throw new InvalidActionException(String.format("Address %s does not belong to the selected client %s",
                            address.fullName(), objectSave.getFullName()));
                }

                if (address.countryId() == null || address.countryId() <= 0L) {
                    throw new InvalidActionException(String.format("Address %s has no country", address.fullName()));
                }

                Country country = countryQueryService.get(address.countryId()).orElse(null);
                if (country == null) {
                    throw new InvalidActionException(String.format("Address %s has selected an invalid country", address.fullName()));
                }

                String addressFullName = Utilities.cleanString(address.fullName());
                String street = Utilities.normalizeAddressFields(address.street());
                String building = Utilities.normalizeAddressFields(address.building());
                String city = Utilities.normalizeAddressFields(address.city());
                String stateProvince = Utilities.normalizeAddressFields(address.stateProvince());
                String addressPhoneNumber = Utilities.cleanPhoneNumber(address.phoneNumber());
                String zipCode = Utilities.cleanZipCode(address.zipCode());

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
                if (address.id() != null && address.id() > 0L) {
                    addressToSave = addressQueryService.findById(address.id()).orElseThrow(() ->
                            new ResourceNotFoundException(String.format("Address with ID %d does not exist", address.id())));
                }

                if (addressToSave == null) {
                    addressToSave = Address.builder()
                            .client(objectSave)
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
                    addressToSave.setClient(objectSave);
                    addressToSave.setFullName(addressFullName);
                    addressToSave.setCountry(country);
                    addressToSave.setPhoneNumber(addressPhoneNumber);
                    addressToSave.setStreet(street);
                    addressToSave.setBuilding(building);
                    addressToSave.setCity(city);
                    addressToSave.setStateProvince(stateProvince);
                    addressToSave.setZipCode(zipCode);
                }

                addresses.add(addressToSave);
            }

            addresses = addressCommandService.saveAllAndFlush(addresses);
        }

        return ClientProcessAdapter.buildClientResponse(objectSave, addresses);
    }

    public ClientResponse deleteClient(Long id) {
        Optional<Client> object = clientCommandService.get(id);
        if (object.isEmpty() || !object.get().isEnabled()) {
            throw new ResourceNotFoundException("Client does not exist");
        }

        List<Address> addresses = addressQueryService.findAllByEnabledAndClient_Id(true, id);
        addresses.forEach(it -> it.setEnabled(false));
        addressCommandService.saveAllAndFlush(addresses);

        clientCommandService.disable(object.get());
        return ClientProcessAdapter.buildClientResponse(object.get(), null);
    }
}
