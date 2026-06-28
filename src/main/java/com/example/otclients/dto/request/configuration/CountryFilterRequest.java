package com.example.otclients.dto.request.configuration;

import com.example.otclients.dto.JsonRequest;
import com.example.otclients.dto.request.RequestPagination;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CountryFilterRequest extends RequestPagination implements JsonRequest {
    private boolean enabled     = true;
    private String  name        = null;
    private String  countryCode = null;
}
