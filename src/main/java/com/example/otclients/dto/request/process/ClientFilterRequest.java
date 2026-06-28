package com.example.otclients.dto.request.process;

import com.example.otclients.dto.JsonRequest;
import com.example.otclients.dto.request.RequestPagination;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientFilterRequest extends RequestPagination implements JsonRequest {
    private boolean enabled = true;
    private Long id = null;
    private String name = null;
    private String lastName = null;
    private String email = null;
    private String phoneNumber = null;
}
