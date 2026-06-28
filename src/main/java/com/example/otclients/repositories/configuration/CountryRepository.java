package com.example.otclients.repositories.configuration;

import com.example.otclients.models.configurations.Country;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("""
            select u
            from Country u
            where u.enabled = :enabled
              and (
                lower(trim(u.name)) like trim(lower(:value))
                or lower(trim(u.countryCode)) = trim(lower(:value))
                or lower(trim(u.placeId)) = trim(lower(:value))
              )
            """)
    Optional<Country> findByAnyIdentifier(@Param("enabled") boolean enabled, @Param("value") String value);

    List<Country> findAllByEnabled(boolean e);

    @Query("""
            select u
            from Country u
            where (:enabled is null or u.enabled = :enabled)
              and (:name is null or lower(u.name) like concat('%', lower(trim(:name)), '%'))
              and (:countryCode is null or lower(u.countryCode) like concat('%', lower(trim(:countryCode)), '%'))
            """)
    List<Country> findAllFilter(@Param("enabled") Boolean enabled,
                                @Param("name") String name,
                                @Param("countryCode") String countryCode,
                                Pageable pageable
    );

    @Query("""
            select count(u)
            from Country u
            where (:enabled is null or u.enabled = :enabled)
              and (:name is null or lower(u.name) like concat('%', lower(trim(:name)), '%'))
              and (:countryCode is null or lower(u.countryCode) like concat('%', lower(trim(:countryCode)), '%'))
            """)
    Integer countAllFilter(@Param("enabled") Boolean enabled,
                           @Param("name") String name,
                           @Param("countryCode") String countryCode
    );
}
