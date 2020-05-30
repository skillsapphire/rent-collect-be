package com.rentcompany.rentcollect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentcompany.rentcollect.models.Rent;

public interface TenantRentDetail extends JpaRepository<Rent, Long>{

}
