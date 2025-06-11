package com.classicmodels.domain.repository;

import com.classicmodels.api.model.lists.OfficesRepModelIdCityList;
import com.classicmodels.api.model.lists.interfaces.OfficesIdCityProjection;
import com.classicmodels.domain.model.Offices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficesRepository extends JpaRepository<Offices, Long> {

    @Query(value = "SELECT id FROM classicmodels_api.offices", nativeQuery = true)
    List<Long> findIds();

    @Query(value = "SELECT id, city FROM classicmodels_api.offices", nativeQuery = true)
    List<OfficesIdCityProjection> findIdCity();
}
