package com.rentcompany.rentcollect.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rentcompany.rentcollect.models.Property;
import com.rentcompany.rentcollect.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.phoneNumber =:phoneNumber")
	Optional<User>  existsByPhoneNumber(@Param("phoneNumber")String phoneNumber);
}
