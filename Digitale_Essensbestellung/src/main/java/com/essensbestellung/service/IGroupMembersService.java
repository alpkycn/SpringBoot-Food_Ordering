package com.essensbestellung.service;

import java.util.List;

import com.essensbestellung.entities.Group;
import com.essensbestellung.entities.GroupMembers;

public interface IGroupMembersService 
{
//	public GroupMembers getGroupMemberbyId(Long id);
	
	public GroupMembers saveGroupMembers(GroupMembers groupMembers);
	
	public List<GroupMembers> getAllGroupMembers();

	public Group getGroupByUserId(Long userid);
	
//	public void deleteGroupMember(Long id);
}
