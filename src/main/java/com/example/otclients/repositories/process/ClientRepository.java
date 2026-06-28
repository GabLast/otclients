package com.example.otclients.repositories.process;

import com.example.otclients.models.process.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("""
            select u
            from Client u
            where u.enabled = :enabled
              and (:id is null or u.id = :id)
              and (:name is null or trim(lower(u.name)) like concat('%', :name, '%'))
              and (:lastName is null or trim(lower(u.lastName)) like concat('%', :lastName, '%'))
              and (:email is null or trim(lower(u.email)) like concat('%', :email, '%'))
              and (:phoneNumber is null or trim(lower(u.phoneNumber)) like concat('%', :phoneNumber, '%'))
            """)
    List<Client> findAllFilter(
            @Param("enabled") boolean enabled,
            @Param("id") Long id,
            @Param("name") String name,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("phoneNumber") String phoneNumber,
            Pageable pageable
    );


    @Query("""
                select count(u)
                from Client u
                where u.enabled = :enabled
                  and (:id is null or u.id = :id)
                  and (:name is null or trim(lower(u.name)) like concat('%', :name, '%'))
                  and (:lastName is null or trim(lower(u.lastName)) like concat('%', :lastName, '%'))
                  and (:email is null or trim(lower(u.email)) like concat('%', :email, '%'))
                  and (:phoneNumber is null or trim(lower(u.phoneNumber)) like concat('%', :phoneNumber, '%'))
            """)
    Integer countAllFilter(
            @Param("enabled") boolean enabled,
            @Param("id") Long id,
            @Param("name") String name,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("phoneNumber") String phoneNumber
    );

    Client findByEmailAndEnabled(String a, boolean enabled);
}
