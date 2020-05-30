package com.rentcompany.rentcollect.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentcompany.rentcollect.exception.ResourceNotFoundException;
import com.rentcompany.rentcollect.models.ERole;
import com.rentcompany.rentcollect.models.Property;
import com.rentcompany.rentcollect.models.Rent;
import com.rentcompany.rentcollect.models.Role;
import com.rentcompany.rentcollect.models.User;
import com.rentcompany.rentcollect.payload.request.LoginRequest;
import com.rentcompany.rentcollect.payload.response.RentDetail;
import com.rentcompany.rentcollect.repository.PropertyRepository;
import com.rentcompany.rentcollect.repository.RentRepository;
import com.rentcompany.rentcollect.repository.RoleRepository;
import com.rentcompany.rentcollect.repository.UserRepository;
import com.rentcompany.rentcollect.util.EmailService;

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
	
	@Autowired
	private EmailService emailService;
	
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
		property.get().setRented(true);
		Property prop = property.get();
		propertyRepository.save(prop);
		System.out.println(prop.isRented());
		rent.setProperty(property.get());
		rent.setUser(user);
		rentRepository.save(rent);
		String body = "Username: "+rent.getUser().getUsername()+"<br>"
					  +"Password: "+rent.getUser().getPassword()+"<br>"
					  +"Website address: http://localhost:8080/";
		emailService.sendEmail(user.getEmail(), "Your login details", body);
		return rent;
		
	}
	
	@GetMapping("/rent/{userId}")
	public RentDetail getTenantRentDetail(
			@PathVariable(value = "userId") Long userId) {
		
		RentDetail rentDetail = new RentDetail();
		Rent rent = rentRepository.findTenantRentDetail(userId);
		Optional<Property> property = propertyRepository.findById(rent.getProperty().getId());
		Property prop = property.get();
		 User user =prop.getUser();
		 rentDetail.setRenteeName(rent.getUser().getUsername());
		rentDetail.setRentAmount(rent.getRentAmount());
		rentDetail.setStartDate(rent.getStartDate());
		rentDetail.setPropertyDesc(property.get().getDescription());
		rentDetail.setPropertyName(property.get().getName());
		rentDetail.setOwnerEmail(user.getEmail());
		
		return rentDetail;
	}
	
	@PutMapping("/user/reset/{userId}")
//	@PreAuthorize("hasRole('ADMIN')")
    public User passwordReset(@PathVariable (value = "userId") Long userId,
                                     @Valid @RequestBody LoginRequest userParam) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("UserId " + userId + " not found");
        }

      Optional<User> user = userRepository.findById(userId);
      User userObject = user.get();
      userObject.setPassword(passwordEncoder.encode(userParam.getPassword()));
      return userRepository.save(userObject);
    }
	
	@PostMapping("/rent/notifyOwner")
	public String emailOwner(
			 @Valid @RequestBody RentDetail rentDetail) {
		String body = "Property Name: "+rentDetail.getPropertyName()+"<br>"
				  +"Tenant Name: "+rentDetail.getRenteeName()+"<br>"
				  +"Rent Amount: "+rentDetail.getRentAmount();
	emailService.sendEmail(rentDetail.getOwnerEmail(), "Rent Paid", body);
	System.out.println("owner is notified");
				return "Owner is notified for the payment made";
	}

}
