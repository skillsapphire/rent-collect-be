package com.rentcompany.rentcollect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rentcompany.rentcollect.models.Rent;

@Repository
public interface RentRepository  extends JpaRepository<Rent, Long> {
	
	 @Query("SELECT r FROM Rent r WHERE r.user.id = :tenantId")
	    public Rent findTenantRentDetail(@Param("tenantId") Long tenantId);
}
