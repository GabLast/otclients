package com.example.otclients.services.query.process;

import com.example.otclients.adapter.ClientProcessAdapter;
import com.example.otclients.converter.PaginationConverter;
import com.example.otclients.dto.PaginationObject;
import com.example.otclients.dto.request.process.ClientFilterRequest;
import com.example.otclients.dto.response.CountResponse;
import com.example.otclients.dto.response.CountResponseData;
import com.example.otclients.dto.response.process.ClientFilterData;
import com.example.otclients.dto.response.process.ClientFilterResponse;
import com.example.otclients.dto.response.process.ClientResponse;
import com.example.otclients.exceptions.ResourceNotFoundException;
import com.example.otclients.models.process.Client;
import com.example.otclients.repositories.process.ClientRepository;
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
public class ClientQueryService extends BaseService<Client, Long> {

    private final ClientRepository repository;
    private final AddressQueryService addressQueryService;

    @Override
    protected JpaRepository<Client, Long> getRepository() {
        return repository;
    }

    public ClientFilterResponse findAllFilter(ClientFilterRequest request) {

        PaginationObject paginationObject =
                PaginationConverter.fromSimpleValues(request.getSortProperty(),
                        request.getSortOrder(), request.getOffset(), request.getLimit());

        List<ClientFilterData> list = findAllFilter(
                request.isEnabled(),
                request.getId(),
                request.getName(), request.getLastName(), request.getEmail(), request.getPhoneNumber(),
                paginationObject.limit(), paginationObject.offset(),
                paginationObject.sort()
        ).stream().map(client -> ClientFilterData.builder()
                .id(client.getId())
                .name(client.getName())
                .lastName(client.getLastName())
                .fullName(client.getFullName())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .build()).toList();

        return ClientFilterResponse.builder().data(list).build();
    }

    public CountResponse countAllFilter(ClientFilterRequest request) {

        return CountResponse.builder()
                .data(CountResponseData.builder()
                        .total(countAllFilter(
                                request.isEnabled(),
                                request.getId(),
                                request.getName(), request.getLastName(), request.getEmail(), request.getPhoneNumber()
                        ))
                        .build())
                .build();
    }

    private List<Client> findAllFilter(
            boolean enabled,
            Long id, String name, String lastname, String email, String phonenumber,
            Integer limit, Integer offset, Sort sort) {

        return repository.findAllFilter(
                enabled,
                id, name, lastname, email, phonenumber,
                sort == null ? new OffsetBasedPageRequest(limit, offset) : new OffsetBasedPageRequest(limit, offset, sort)
        );
    }

    private Integer countAllFilter(
            boolean enabled,
            Long id, String name, String lastname, String email, String phonenumber
    ) {

        return repository.countAllFilter(
                enabled,
                id, name, lastname, email, phonenumber
        );
    }

    public Client findByEmailAndEnabled(String email, boolean enabled) {
        return repository.findByEmailAndEnabled(email, enabled);
    }

    public ClientResponse getClient(Long id) {
        Optional<Client> object = get(id);
        if (object.isEmpty()) {
            throw new ResourceNotFoundException("Client does not exist");
        }

        return ClientProcessAdapter.buildClientResponse(object.get(), addressQueryService.findAllByEnabledAndClient_Id(true, id));
    }
}
