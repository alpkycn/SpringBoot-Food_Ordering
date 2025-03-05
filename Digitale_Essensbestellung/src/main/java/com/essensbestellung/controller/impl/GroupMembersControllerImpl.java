package com.essensbestellung.controller.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essensbestellung.controller.IGroupMembersController;
import com.essensbestellung.entities.GroupMembers;
import com.essensbestellung.service.IGroupMembersService;

    @RestController
	@RequestMapping("/rest/api/groupmember")
	public class GroupMembersControllerImpl implements IGroupMembersController
	{
		 @Autowired
		 private IGroupMembersService groupMembersService;

/*		 @GetMapping(path = "/list/{id}")
		 public GroupMembers getGroupMemberbyId(@PathVariable(name = "id") Long id)
		 {
			 return groupMembersService.getGroupMemberbyId(id);
		 }
*/		 
		 @PostMapping(path = "/save")
		 public GroupMembers saveGroupMembers(@RequestBody GroupMembers groupMember)
		 {
			 return groupMembersService.saveGroupMembers(groupMember);
		 }
		 
		 @GetMapping(path = "/list/{id}")
		 public List<GroupMembers> getAllGroupMembers()
		 {
			 return groupMembersService.getAllGroupMembers();
		 }
		 
/*		 @DeleteMapping(path = "/delete/{id}")
		 public void deleteGroupMember(@PathVariable(name = "id") Long id)
		 {
			 groupMembersService.deleteGroupMember(id);
		 }
*/		 
	}

