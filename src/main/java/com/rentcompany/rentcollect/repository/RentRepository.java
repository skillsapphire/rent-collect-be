package com.rentcompany.rentcollect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentcompany.rentcollect.models.Rent;

@Repository
public interface RentRepository  extends JpaRepository<Rent, Long> {

}
