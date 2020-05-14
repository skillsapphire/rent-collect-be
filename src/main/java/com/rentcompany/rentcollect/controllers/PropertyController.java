package com.rentcompany.rentcollect.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentcompany.rentcollect.models.Property;
import com.rentcompany.rentcollect.repository.PropertyRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/property")
public class PropertyController {

	@Autowired
	private PropertyRepository propertyRepository;
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<Property> getAllProprties() {
		return propertyRepository.findAll();
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createProperty(@Valid @RequestBody Property property) {
		return ResponseEntity.ok(propertyRepository.save(property));
	}
}
