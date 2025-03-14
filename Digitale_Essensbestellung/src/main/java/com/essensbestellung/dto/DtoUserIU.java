package com.essensbestellung.dto;


import com.essensbestellung.entities.Location;
import com.essensbestellung.enums.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoUserIU 
{
	    @NotBlank(message = "Username is required")
	    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
	    private String username;

	    @NotBlank(message = "Password is required")
	    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
	    private String password;

	    @NotBlank(message = "Role is required")
	    private Role role; 
	    
	    @NotBlank(message = "Location is required")
	    private Location location;
	    
	    @NotBlank(message = "Full name is required")  
	    private String fullname; 
	    
	    private byte[] qrCode; 



}
