package com.essensbestellung.service.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.essensbestellung.dto.DtoUserIU;
import com.essensbestellung.entities.Group;
import com.essensbestellung.entities.GroupMemberId;
import com.essensbestellung.entities.GroupMembers;
import com.essensbestellung.entities.User;
import com.essensbestellung.entities.Location;
import com.essensbestellung.enums.Role;
import com.essensbestellung.jwt.AuthRequest;
import com.essensbestellung.jwt.AuthResponse;
import com.essensbestellung.jwt.JwtService;
import com.essensbestellung.repository.IGroupMembersRepository;
import com.essensbestellung.repository.IGroupRepository;
import com.essensbestellung.repository.IUserRepository;
import com.essensbestellung.repository.ILocationRepository;
import com.essensbestellung.service.IAuthService;
import com.google.zxing.WriterException;

import io.jsonwebtoken.io.IOException;



@Service
public class IAuthServiceImpl implements IAuthService 
{
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private IGroupMembersRepository groupMembersRepository;
	
	@Autowired
	private IGroupRepository groupRepository;

	@Autowired
	private ILocationRepository locationRepository;
	
	
	public AuthResponse authenticate(AuthRequest request) {
		try {
	        UsernamePasswordAuthenticationToken auth =
	                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
	        authenticationProvider.authenticate(auth);

	        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
	        if (optionalUser.isPresent()) {
	            User user = optionalUser.get();


	            String token = jwtService.generateToken(user);

	            return new AuthResponse(token, user.getId());
	        }
	    } catch (Exception e) {
			e.printStackTrace();
			System.out.println("Username or password is wrong");
		}
		return null;
	}
	
	@Override
	public DtoUserIU register(AuthRequest request) {
	    DtoUserIU dto = new DtoUserIU();

	    User user = new User();
	    user.setUsername(request.getUsername());
	    user.setPassword(passwordEncoder.encode(request.getPassword()));
	    user.setFullname(request.getFullname());

	    Role role;
	    try {
	        role = Role.valueOf(request.getRole().toUpperCase());
	        user.setRole(role);
	    } catch (IllegalArgumentException e) {
	        throw new RuntimeException("Invalid role: " + request.getRole());
	    }

	    User savedUser = userRepository.save(user);

	    try {
	        try {
				savedUser.generateQRCode();
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
	        userRepository.save(savedUser);
	        dto.setQrCode(savedUser.getQrCode());
	    } catch (WriterException | IOException e) {
	        throw new RuntimeException("Error generating QR code: " + e.getMessage(), e);
	    }

		if (role == Role.GRUPPENLEITER || role == Role.KUECHENPERSONAL || role == Role.KUNDE) {
			if (request.getGroup() == null) {
	            throw new IllegalArgumentException("Group is required for this role");
	        }
	        GroupMembers groupMember = new GroupMembers();

	        GroupMemberId memberId = new GroupMemberId(request.getGroup().getId(), savedUser.getId());
	        groupMember.setId(memberId);
	        groupMember.setGroup(request.getGroup());
	        groupMember.setUser(savedUser);

	        groupMembersRepository.save(groupMember);

			if(role == Role.GRUPPENLEITER){
				Group group = groupRepository.findById(request.getGroup().getId())
	                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

				group.setGroupLeader(savedUser);

				groupRepository.save(group);

			}
	    } else if (role == Role.STANDORTLEITER) {
	        if (request.getLocation() == null) {
	            throw new IllegalArgumentException("Location is required for this role");
	        }
			Location location = locationRepository.findById(request.getLocation().getId()).orElseThrow(() -> new IllegalArgumentException		("Location not found"));
			location.setSiteManager(savedUser);
			locationRepository.save(location);

	    }

	    BeanUtils.copyProperties(savedUser, dto);

	    return dto;
	}
}
