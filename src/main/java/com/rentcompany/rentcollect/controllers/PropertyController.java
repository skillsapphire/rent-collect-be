package com.rentcompany.rentcollect.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentcompany.rentcollect.exception.ResourceNotFoundException;
import com.rentcompany.rentcollect.models.Property;
import com.rentcompany.rentcollect.repository.PropertyRepository;
import com.rentcompany.rentcollect.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
//https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-many-mapping-example/
public class PropertyController {

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/property/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public Page<Property> getAllProprties(@PathVariable(value = "userId") Long userId, Pageable pageable) {
		return propertyRepository.findByUserId(userId, pageable);
	}
	
	@PostMapping("/property/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
    public Property createProperty(@PathVariable (value = "userId") Long userId,
                                 @Valid @RequestBody Property property) {
        return userRepository.findById(userId).map(user -> {
        	property.setUser(user);
            return propertyRepository.save(property);
        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
    }
	
	@DeleteMapping("/user/{userId}/property/{propertyId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteProperty(@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "propertyId") Long propertyId ) {
		 return propertyRepository.findByIdAndUserId(propertyId, userId).map(property -> {
			 propertyRepository.delete(property);
	            return ResponseEntity.ok(property);
	}).orElseThrow(() -> new ResourceNotFoundException("Property not found with id " + propertyId + " and userId " + userId));
	}
	
	@PutMapping("/user/{userId}/property/{propertyId}")
    public Property updateProperty(@PathVariable (value = "userId") Long userId,
                                 @PathVariable (value = "propertyId") Long propertyId,
                                 @Valid @RequestBody Property propertyReq) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("UserId " + userId + " not found");
        }

        return propertyRepository.findById(propertyId).map(property -> {
        	property.setName(propertyReq.getName());
            return propertyRepository.save(property);
        }).orElseThrow(() -> new ResourceNotFoundException("PropertyId " + propertyId + " not found"));
    }
}
