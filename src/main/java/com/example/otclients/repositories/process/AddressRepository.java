package com.example.otclients.repositories.process;

import com.example.otclients.models.process.Address;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {


    @EntityGraph(attributePaths = {"country"})
    @Query("""
                select u
                from Address u
                where u.enabled = :enabled
                  and (:clientId is null or u.client.id = :clientId)
                  and (:id is null or u.id = :id)
                  and (:fullName is null or trim(lower(u.fullName)) like concat('%', trim(lower(:fullName)), '%'))
                  and (:phoneNumber is null or trim(lower(u.phoneNumber)) like concat('%', trim(lower(:phoneNumber)), '%'))
                  and ((:street is null or trim(lower(u.street)) like concat('%', trim(lower(:street)), '%'))
                  or (:building is null or trim(lower(u.building)) like concat('%', trim(lower(:building)), '%'))
                  or (:city is null or trim(lower(u.city)) like concat('%', trim(lower(:city)), '%'))
                  or (:stateProvince is null or trim(lower(u.stateProvince)) like concat('%', trim(lower(:stateProvince)), '%'))
                  or (:zipCode is null or trim(lower(u.zipCode)) like concat('%', trim(lower(:zipCode)), '%'))
                  or (:country is null or trim(lower(u.country.name)) like concat('%', trim(lower(:country)), '%')))
            """)
    List<Address> findAllFilter(
            @Param("enabled") boolean enabled,
            @Param("id") Long id,
            @Param("clientId") Long clientId,
            @Param("fullName") String fullName,
            @Param("phoneNumber") String phoneNumber,
            @Param("street") String street,
            @Param("building") String building,
            @Param("city") String city,
            @Param("stateProvince") String stateProvince,
            @Param("zipCode") String zipCode,
            @Param("country") String country,
            Pageable pageable
    );

    @Query("""
                select count(u)
                from Address u
                where u.enabled = :enabled
                  and (:clientId is null or u.client.id = :clientId)
                  and (:id is null or u.id = :id)
                  and (:fullName is null or trim(lower(u.fullName)) like concat('%', trim(lower(:fullName)), '%'))
                  and (:phoneNumber is null or trim(lower(u.phoneNumber)) like concat('%', trim(lower(:phoneNumber)), '%'))
                  and ((:street is null or trim(lower(u.street)) like concat('%', trim(lower(:street)), '%'))
                  or (:building is null or trim(lower(u.building)) like concat('%', trim(lower(:building)), '%'))
                  or (:city is null or trim(lower(u.city)) like concat('%', trim(lower(:city)), '%'))
                  or (:stateProvince is null or trim(lower(u.stateProvince)) like concat('%', trim(lower(:stateProvince)), '%'))
                  or (:zipCode is null or trim(lower(u.zipCode)) like concat('%', trim(lower(:zipCode)), '%'))
                  or (:country is null or trim(lower(u.country.name)) like concat('%', trim(lower(:country)), '%')))
            """)
    Integer countAllFilter(
            @Param("enabled") boolean enabled,
            @Param("id") Long id,
            @Param("clientId") Long clientId,
            @Param("fullName") String fullName,
            @Param("phoneNumber") String phoneNumber,
            @Param("street") String street,
            @Param("building") String building,
            @Param("city") String city,
            @Param("stateProvince") String stateProvince,
            @Param("zipCode") String zipCode,
            @Param("country") String country
    );

    @EntityGraph(attributePaths = {"country", "client"})
    Optional<Address> findById(Long id);

    List<Address> findAllByEnabledAndClient_Id(boolean enabled, Long clientId);
}
