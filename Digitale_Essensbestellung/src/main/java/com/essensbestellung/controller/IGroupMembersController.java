package com.essensbestellung.controller;

import java.util.List;

import com.essensbestellung.entities.GroupMembers;

public interface IGroupMembersController
{
//	 public GroupMembers getGroupMemberbyId(Long id);
	 
	 public GroupMembers saveGroupMembers(GroupMembers groupMembers);
	 
	 public List<GroupMembers> getAllGroupMembers();
	 
//	 public void deleteGroupMember(Long id);
}
