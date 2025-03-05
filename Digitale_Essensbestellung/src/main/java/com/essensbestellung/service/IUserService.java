package com.essensbestellung.service;

import java.util.List;

import com.essensbestellung.entities.Group;
import com.essensbestellung.dto.DtoUser;
import com.essensbestellung.dto.DtoUserIU;

public interface IUserService {

	public DtoUser getUserbyId(Long id);
	
	public DtoUser saveUser(DtoUserIU dtoUserIU);
		
	public List<DtoUser> getAllUsers();
	
	public void deleteOrder(Long id);

	public Group getGroup(Long id);
	
	public List<DtoUser> getAllUsersWithDetails();
	
	public DtoUserIU updateUser(Long userId, DtoUser updateRequest);

//	public DtoUserIU updateUser(@PathVariable(name = "id") Long id, @RequestBody User updateRequest);

}
