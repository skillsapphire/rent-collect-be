package com.rentcompany.rentcollect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentcompany.rentcollect.models.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
