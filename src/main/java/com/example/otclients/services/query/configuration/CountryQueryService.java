package com.example.otclients.services.query.configuration;

import com.example.otclients.converter.PaginationConverter;
import com.example.otclients.dto.PaginationObject;
import com.example.otclients.dto.request.configuration.CountryFilterRequest;
import com.example.otclients.dto.response.CountResponse;
import com.example.otclients.dto.response.CountResponseData;
import com.example.otclients.dto.response.configuration.CountryData;
import com.example.otclients.dto.response.configuration.CountryFilterData;
import com.example.otclients.dto.response.configuration.CountryFilterDataDetails;
import com.example.otclients.dto.response.configuration.CountryFilterResponse;
import com.example.otclients.dto.response.configuration.CountryFindAllData;
import com.example.otclients.dto.response.configuration.CountryFindAllDataDetails;
import com.example.otclients.dto.response.configuration.CountryFindAllResponse;
import com.example.otclients.dto.response.configuration.CountryResponse;
import com.example.otclients.exceptions.ResourceNotFoundException;
import com.example.otclients.models.configurations.Country;
import com.example.otclients.repositories.configuration.CountryRepository;
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
public class CountryQueryService extends BaseService<Country, Long> {

    private final CountryRepository repository;

    @Override
    protected JpaRepository<Country, Long> getRepository() {
        return repository;
    }

    @Transactional(readOnly = true)
    public Optional<Country> findByAnyIdentifier(boolean enabled, String identifier) {
        return repository.findByAnyIdentifier(enabled, identifier);
    }

    @Transactional(readOnly = true)
    public List<Country> findAllByEnabled(boolean enabled) {
        return repository.findAllByEnabled(enabled);
    }

    public CountryFindAllResponse findAllResponse() {
        return CountryFindAllResponse.builder()
                .data(CountryFindAllData.builder()
                        .dataList(findAllByEnabled(true).stream()
                                .map(it -> CountryFindAllDataDetails.builder()
                                        .id(it.getId())
                                        .code(it.getCountryCode())
                                        .name(it.getName())
                                        .build()).toList())
                        .build())
                .build();
    }

    @Transactional(readOnly = true)
    public List<Country> findAllFilter(Boolean enabled, String name, String description,
            Integer limit, Integer offset, Sort sort) {
        return repository.findAllFilter(enabled, name, description,
                sort == null ? new OffsetBasedPageRequest(limit, offset)
                             : new OffsetBasedPageRequest(limit, offset, sort));
    }

    @Transactional(readOnly = true)
    public Integer countAllFilter(Boolean enabled, String name, String description) {
        return repository.countAllFilter(enabled, name, description);
    }

    public CountryFilterResponse findAllFilter(CountryFilterRequest request) {

        PaginationObject paginationObject =
                PaginationConverter.fromSimpleValues(request.getSortProperty(),
                        request.getSortOrder(), request.getOffset(), request.getLimit());

        List<CountryFilterDataDetails> dataList =
                findAllFilter(request.isEnabled(), request.getName(),
                        request.getCountryCode(), paginationObject.limit(),
                        paginationObject.offset(), paginationObject.sort()).stream()
                        .map(it -> CountryFilterDataDetails.builder()
                                .id(it.getId()).name(it.getName())
                                .placeId(it.getPlaceId())
                                .code(it.getCountryCode()).build()).toList();

        return CountryFilterResponse.builder()
                .data(CountryFilterData.builder().dataList(dataList).build()).build();

    }

    public CountResponse countAllFilter(CountryFilterRequest request) {

        return CountResponse.builder().data(CountResponseData.builder()
                .total(countAllFilter(request.isEnabled(), request.getName(),
                        request.getCountryCode())).build()).build();
    }

    public CountryResponse getCountryById(Long id) {
        Optional<Country> header = get(id);
        if (header.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Object by ID [" + id + "] does not exist");
        }

        return generateResponse(header.get());
    }

    private CountryResponse generateResponse(Country country) {
        return CountryResponse.builder()
                .data(CountryData.builder()
                        .id(country.getId()).name(country.getName())
                        .placeId(country.getPlaceId())
                        .code(country.getCountryCode()).build())
                .build();
    }
}
