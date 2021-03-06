package com.rentcompany.rentcollect.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rentcompany.rentcollect.models.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
	//https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#repositories.query-methods.query-creation
	//https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-three-custom-queries-with-query-methods/
	//https://www.javaworld.com/article/3387643/java-persistence-with-jpa-and-hibernate-part-2-many-to-many-relationships.html

	Page<Property> findByUserId(Long userId, Pageable pageable);
	
	@Query(nativeQuery = true, value = "SELECT * FROM property p, users u  WHERE (p.name like %:searchedProperty% or p.description like %:searchedProperty%) and u.id =:userId and p.user_id = u.id")
	Page<Property> findBySearchedProperty(@Param("userId") Long userId, 
			@Param("searchedProperty") String searchedProperty, Pageable pageable);
	
	Optional<Property> findByIdAndUserId(Long id, Long userId);
}
