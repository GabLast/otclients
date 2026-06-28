package com.example.otclients.services.query.process;

import com.example.otclients.adapter.ClientProcessAdapter;
import com.example.otclients.converter.PaginationConverter;
import com.example.otclients.dto.PaginationObject;
import com.example.otclients.dto.request.process.AddressFilterRequest;
import com.example.otclients.dto.response.CountResponse;
import com.example.otclients.dto.response.CountResponseData;
import com.example.otclients.dto.response.process.AddressFilterData;
import com.example.otclients.dto.response.process.AddressFilterResponse;
import com.example.otclients.dto.response.process.AddressResponse;
import com.example.otclients.exceptions.ResourceNotFoundException;
import com.example.otclients.models.process.Address;
import com.example.otclients.repositories.process.AddressRepository;
import com.example.otclients.services.BaseService;
import com.example.otclients.utils.OffsetBasedPageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AddressQueryService extends BaseService<Address, Long> {

    private final AddressRepository repository;

    @Override
    protected JpaRepository<Address, Long> getRepository() {
        return repository;
    }

    public Optional<Address> findById(Long id) {
        return repository.findById(id);
    }

    public List<Address> findAllByEnabledAndClient_Id(boolean enabled, Long clientId) {
        return repository.findAllByEnabledAndClient_Id(enabled, clientId);
    }

    public AddressFilterResponse findAllFilter(AddressFilterRequest request) {

        PaginationObject paginationObject =
                PaginationConverter.fromSimpleValues(request.getSortProperty(),
                        request.getSortOrder(), request.getOffset(), request.getLimit());

        List<AddressFilterData> list = findAllFilter(
                request.isEnabled(),
                request.getId(), request.getClientId(),
                request.getFullName(), request.getPhoneNumber(), request.getAddress(),
                paginationObject.limit(), paginationObject.offset(),
                paginationObject.sort()
        ).stream().map(it -> AddressFilterData.builder()
                .id(it.getId())
                .clientId(it.getClient().getId())
                .fullName(it.getFullName())
                .country(it.getCountry().getName())
                .countryId(it.getCountry().getId())
                .phoneNumber(it.getPhoneNumber())
                .street(it.getStreet())
                .stateProvince(it.getStateProvince())
                .building(it.getBuilding())
                .zipCode(it.getZipCode())
                .build()).toList();

        return AddressFilterResponse.builder()
                .data(list)
                .build();
    }

    private List<Address> findAllFilter(boolean enabled,
                                        Long id, Long clientId,
                                        String fullName, String phoneNumber, String address,
                                        Integer limit, Integer offset, Sort sort) {

        return repository.findAllFilter(enabled, id, clientId,
                fullName, phoneNumber,
                address, address, address, address, address, address,
                sort == null ? new OffsetBasedPageRequest(limit, offset) : new OffsetBasedPageRequest(limit, offset, sort));
    }

    public CountResponse countAllFilter(AddressFilterRequest request) {

        return CountResponse.builder()
                .data(CountResponseData.builder()
                        .total(countAllFilter(
                                request.isEnabled(),
                                request.getId(), request.getClientId(),
                                request.getFullName(), request.getPhoneNumber(), request.getAddress()
                        ))
                        .build())
                .build();
    }

    private Integer countAllFilter(boolean enabled,
                                   Long id, Long clientId,
                                   String fullName, String phoneNumber, String address) {

        return repository.countAllFilter(enabled, id, clientId,
                fullName, phoneNumber,
                address, address, address, address, address, address
        );
    }

    public AddressResponse getAddress(Long id) {

        Optional<Address> object = repository.findById(id);
        if (object.isEmpty()) {
            throw new ResourceNotFoundException("Address does not exist");
        }

        return AddressResponse.builder()
                .data(ClientProcessAdapter.buildAddressData(object.get()))
                .build();
    }
}
