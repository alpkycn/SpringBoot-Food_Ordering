package com.essensbestellung.jwt;


import com.essensbestellung.entities.Group;
import com.essensbestellung.entities.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest 
{
	private String username;
    private String password;
    private String role;
    private String fullname;
    private Group group;
    private Location location;

}
