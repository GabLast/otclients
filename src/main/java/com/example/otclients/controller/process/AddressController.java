package com.example.otclients.controller.process;

import com.example.otclients.dto.request.process.AddressFilterRequest;
import com.example.otclients.dto.request.process.AddressRequest;
import com.example.otclients.services.command.process.AddressCommandService;
import com.example.otclients.services.query.process.AddressQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressQueryService queryService;
    private final AddressCommandService commandService;

    @GetMapping("findall")
    public ResponseEntity<?> findall(AddressFilterRequest request) {
        return new ResponseEntity<>(queryService.findAllFilter(request), HttpStatus.OK);
    }

    @GetMapping("countall")
    public ResponseEntity<?> countall(AddressFilterRequest request) {
        return new ResponseEntity<>(queryService.countAllFilter(request),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return new ResponseEntity<>(queryService.getAddress(id),
                HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody AddressRequest request) {
        return new ResponseEntity<>(commandService.saveAddress(request, false),
                HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> put(@RequestBody AddressRequest request) {
        return new ResponseEntity<>(commandService.saveAddress(request, true),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable Long id) {
        return new ResponseEntity<>(commandService.deleteAddress(id),
                HttpStatus.OK);
    }
}


