package com.example.otclients.dto.request.process;

import com.example.otclients.dto.JsonRequest;
import com.example.otclients.dto.request.RequestPagination;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddressFilterRequest extends RequestPagination implements JsonRequest {
    private boolean enabled = true;
    private Long id = null;
    private Long clientId = null;
    private String fullName = null;
    private String phoneNumber = null;
    private String address = null;
}
