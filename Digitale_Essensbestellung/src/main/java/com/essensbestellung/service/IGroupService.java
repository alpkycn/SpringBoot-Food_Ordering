package com.essensbestellung.service;

import java.time.LocalDate;
import java.util.List;

import com.essensbestellung.entities.Group;
import com.essensbestellung.entities.SubstituteLeaders;
import com.essensbestellung.entities.User;

public interface IGroupService 
{
	public Group getGroupbyId(Long id);
	
	public Group saveGroup(Group group);
	
	public List<Group> getAllGroups();
	
	public void deleteGroup(Long id);
	
	public List<User> getMembersByGroup(Long groupId);
	
	public Group getGroupByGroupLeader(Long leaderID);

	public List<Group> getSubstitutionGroupsForToday(Long leaderID);

	public SubstituteLeaders addSubstitution(Long groupId, Long substituteLeaderId, LocalDate substitutionDate);

}
