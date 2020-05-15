package com.rentcompany.rentcollect.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentcompany.rentcollect.models.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
	//https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#repositories.query-methods.query-creation
	//https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-three-custom-queries-with-query-methods/
	Page<Property> findByUserId(Long userId, Pageable pageable);
	Optional<Property> findByIdAndUserId(Long id, Long userId);
}
