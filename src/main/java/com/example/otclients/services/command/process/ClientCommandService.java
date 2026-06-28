package com.example.otclients.services.command.process;

import com.example.otclients.models.process.Client;
import com.example.otclients.repositories.process.ClientRepository;
import com.example.otclients.services.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ClientCommandService extends BaseService<Client, Long> {

    private final ClientRepository repository;

    @Override
    protected JpaRepository<Client, Long> getRepository() {
        return repository;
    }
}
