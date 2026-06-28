package com.example.otclients.controller.configuration;

import com.example.otclients.dto.request.configuration.CountryFilterRequest;
import com.example.otclients.dto.request.configuration.CountryRequest;
import com.example.otclients.services.command.configuration.CountryCommandService;
import com.example.otclients.services.query.configuration.CountryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryQueryService countryQueryService;
    private final CountryCommandService countryCommandService;

    @GetMapping("fetch")
    public ResponseEntity<?> fetch() {
        return new ResponseEntity<>(countryQueryService.findAllResponse(), HttpStatus.OK);
    }

    @GetMapping("findall")
    public ResponseEntity<?> findall(CountryFilterRequest request) {
        return new ResponseEntity<>(countryQueryService.findAllFilter(request), HttpStatus.OK);
    }

    @GetMapping("countall")
    public ResponseEntity<?> countall(CountryFilterRequest request) {
        return new ResponseEntity<>(countryQueryService.countAllFilter(request), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody CountryRequest request) {

        return new ResponseEntity<>(countryCommandService.save(request),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam Long id) {
        return new ResponseEntity<>(countryQueryService.getCountryById(id), HttpStatus.OK);
    }

}


