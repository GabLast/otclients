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
              and (:fullName is null or lower(trim(u.fullName)) like concat('%', :fullName, '%'))
              and (:phoneNumber is null or lower(trim(u.phoneNumber)) like concat('%', :phoneNumber, '%'))
              and (:street is null or lower(trim(u.street)) like concat('%', :street, '%'))
              and (:building is null or lower(trim(u.building)) like concat('%', :building, '%'))
              and (:city is null or lower(trim(u.city)) like concat('%', :city, '%'))
              and (:stateProvince is null or lower(trim(u.stateProvince)) like concat('%', :stateProvince, '%'))
              and (:zipCode is null or lower(trim(u.zipCode)) like concat('%', :zipCode, '%'))
              and (:country is null or lower(trim(u.country.name)) like concat('%', :country, '%'))
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
                  and (:fullName is null or lower(trim(u.fullName)) like concat('%', :fullName, '%'))
                  and (:phoneNumber is null or lower(trim(u.phoneNumber)) like concat('%', :phoneNumber, '%'))
                  and (:street is null or lower(trim(u.street)) like concat('%', :street, '%'))
                  and (:building is null or lower(trim(u.building)) like concat('%', :building, '%'))
                  and (:city is null or lower(trim(u.city)) like concat('%', :city, '%'))
                  and (:stateProvince is null or lower(trim(u.stateProvince)) like concat('%', :stateProvince, '%'))
                  and (:zipCode is null or lower(trim(u.zipCode)) like concat('%', :zipCode, '%'))
                  and (:country is null or lower(trim(u.country.name)) like concat('%', :country, '%'))
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
