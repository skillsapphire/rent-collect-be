package com.rentcompany.rentcollect.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentcompany.rentcollect.models.ERole;
import com.rentcompany.rentcollect.models.Property;
import com.rentcompany.rentcollect.models.Rent;
import com.rentcompany.rentcollect.models.Role;
import com.rentcompany.rentcollect.models.User;
import com.rentcompany.rentcollect.repository.PropertyRepository;
import com.rentcompany.rentcollect.repository.RentRepository;
import com.rentcompany.rentcollect.repository.RoleRepository;
import com.rentcompany.rentcollect.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class RentController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private RentRepository rentRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/*@PostMapping("/rent/{userId}/property/{propertyId}")
	public Rent rentProperty(@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "propertyId") Long propertyId, @Valid @RequestBody Rent rent) {
		
		User tenant = new User();
		tenant.setEmail(rent.);
		Optional<User> user = userRepository.findById(userId);
		Optional<Property> property = propertyRepository.findById(propertyId);
		rent.setProperty(property.get());
		rent.setUser(user.get());
		rentRepository.save(rent);
		return rent;
		
	}*/
	
	
	@PostMapping("/rent/{propertyId}")
	public Rent rentProperty(
			@PathVariable(value = "propertyId") Long propertyId, @Valid @RequestBody Rent rent) {
		
		User tenant = new User();
		tenant.setUsername(rent.getUser().getUsername());
		tenant.setEmail(rent.getUser().getEmail());
		tenant.setPassword(passwordEncoder.encode(rent.getUser().getPassword()));
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
		tenant.setRoles(roles);
		User user = userRepository.save(tenant);
		Optional<Property> property = propertyRepository.findById(propertyId);
		rent.setProperty(property.get());
		rent.setUser(user);
		rentRepository.save(rent);
		return rent;
		
	}
}
