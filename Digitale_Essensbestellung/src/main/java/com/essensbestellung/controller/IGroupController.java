package com.essensbestellung.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.essensbestellung.entities.Group;


public interface IGroupController 
{
//	 public Group getGroupById(Long id);
	 
	 public Group saveGroup(Group group);
	 
	 public List<Group> getAllGroups();
	 
	 public void deleteGroup(Long id);

	 public ResponseEntity<List<Group>> getGroupsByGroupLeader(Long leaderId);
}
