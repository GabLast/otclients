package com.example.otclients.controller.process;

import com.example.otclients.dto.request.process.ClientFilterRequest;
import com.example.otclients.dto.request.process.ClientRequest;
import com.example.otclients.services.command.process.ClientProcessCommandService;
import com.example.otclients.services.query.process.ClientQueryService;
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
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientQueryService clientQueryService;

    private final ClientProcessCommandService clientProcessCommandService;

    @GetMapping("findall")
    public ResponseEntity<?> findall(ClientFilterRequest request) {
        return new ResponseEntity<>(clientQueryService.findAllFilter(request), HttpStatus.OK);
    }

    @GetMapping("countall")
    public ResponseEntity<?> countall(ClientFilterRequest request) {
        return new ResponseEntity<>(clientQueryService.countAllFilter(request),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return new ResponseEntity<>(clientQueryService.getClient(id),
                HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody ClientRequest request) {
        return new ResponseEntity<>(clientProcessCommandService.saveClient(request, false),
                HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> put(@RequestBody ClientRequest request) {
        return new ResponseEntity<>(clientProcessCommandService.saveClient(request, true),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return new ResponseEntity<>(clientProcessCommandService.deleteClient(id),
                HttpStatus.OK);
    }
}


