package com.essensbestellung.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essensbestellung.dto.DtoUser;
import com.essensbestellung.dto.DtoUserIU;
import com.essensbestellung.entities.Group;
import com.essensbestellung.entities.GroupMemberId;
import com.essensbestellung.entities.GroupMembers;
import com.essensbestellung.entities.Location;
import com.essensbestellung.entities.User;
import com.essensbestellung.exception.BaseException;
import com.essensbestellung.exception.ErrorMessage;
import com.essensbestellung.exception.MessageType;
import com.essensbestellung.repository.IGroupMembersRepository;
import com.essensbestellung.repository.IGroupRepository;
import com.essensbestellung.repository.ILocationRepository;
import com.essensbestellung.repository.IUserRepository;
import com.essensbestellung.service.IUserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
    private IUserRepository userRepository;

	@Autowired
	private IGroupMembersRepository groupMembersRepository;
	
	@Autowired
	private IGroupRepository groupRepository;
	
    @Autowired
	private ILocationRepository locationRepository;
	
	
	public DtoUser getUserbyId(Long id) {
	    Optional<User> optional = userRepository.findById(id);
	    
	    if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString()));
	    }
	    
	    User user = optional.get(); 
	    DtoUser dtoUser = new DtoUser();
	    
	    BeanUtils.copyProperties(user, dtoUser);
	    return dtoUser;    
	}
	
	 public List<DtoUser> getAllUsersWithDetails() {
	        return userRepository.findAllUsersWithDetails();
	    }
	
	public DtoUser saveUser(DtoUserIU dtoUserIU)
	{
		
		DtoUser dtoUser = new DtoUser();
		User user = new User();
		
		BeanUtils.copyProperties(dtoUserIU, user);
		User dpUser = userRepository.save(user);
		
		BeanUtils.copyProperties(dpUser, dtoUser);
		
		return dtoUser;
	}


	public List<DtoUser> getAllUsers()
	{
		List<DtoUser> dtolist = new ArrayList<DtoUser>();
		
		List<User> UserList = userRepository.findAll();
		
		for(User user : UserList)
		{
			DtoUser dto = new DtoUser();
			BeanUtils.copyProperties(user, dto);
			
			dtolist.add(dto);
		}
		
		return dtolist;
		
	}
	
	public void deleteOrder(Long id)
	{
		getUserbyId(id);
		
		userRepository.deleteById(id);

	}

	public Group getGroup(Long id){

		return groupMembersRepository.findGroupByUserId(id);

	}
	
	@Override
	@Transactional
	public DtoUserIU updateUser(Long userId, DtoUser updateRequest) {

		User user = userRepository.findById(userId)
	        .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));


		if (updateRequest.getUsername() != null && !updateRequest.getUsername().isEmpty()) {
	        user.setUsername(updateRequest.getUsername());
	    }

		if (updateRequest.getRole() != null) {
		    user.setRole(updateRequest.getRole());
		}

	    if (updateRequest.getUsername() != null && !updateRequest.getUsername().isEmpty()) {
	        user.setFullname(updateRequest.getUsername());
	    }

	    if (updateRequest.getGruppenId() != null) {
	        Long newGroupId = updateRequest.getGruppenId();

	        groupMembersRepository.deleteById_UserId(userId);

	        Group newGroup = groupRepository.findById(newGroupId)
	            .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + newGroupId));

	        GroupMembers newGroupMember = new GroupMembers();
	        GroupMemberId newGroupMemberId = new GroupMemberId(newGroup.getId(), userId);

	        newGroupMember.setId(newGroupMemberId);
	        newGroupMember.setGroup(newGroup);
	        newGroupMember.setUser(user);

	        groupMembersRepository.save(newGroupMember);
	    }

	    if (updateRequest.getLocationId() != null) {
	        Long newLocationId = updateRequest.getLocationId();

	        Location newLocation = locationRepository.findById(newLocationId)
	            .orElseThrow(() -> new IllegalArgumentException("Location not found with ID: " + newLocationId));

	        newLocation.setSiteManager(user);
	        locationRepository.save(newLocation);
	    }

	    userRepository.save(user);

	    DtoUserIU dto = new DtoUserIU();
	    BeanUtils.copyProperties(user, dto);

	    return dto;
	}

	


   
}

