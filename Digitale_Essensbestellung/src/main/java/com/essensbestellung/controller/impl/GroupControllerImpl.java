package com.essensbestellung.controller.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.essensbestellung.controller.IGroupController;
import com.essensbestellung.entities.Group;
import com.essensbestellung.entities.SubstituteLeaders;
import com.essensbestellung.entities.User;
import com.essensbestellung.service.IGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.CrossOrigin;   

@CrossOrigin(origins = {"http://172.26.92.152", "http://172.26.92.152:80", "http://172.26.92.152:8080"}) 

@RestController
@RequestMapping("/rest/api/groups")
public class GroupControllerImpl implements IGroupController
{
	 @Autowired
	 private IGroupService groupService;

	 @GetMapping(path = "/list/{id}")
	 public Group getGroupbyId(@PathVariable(name = "id") Long id)
	 {
		 return groupService.getGroupbyId(id);
	 }
	 
	 @GetMapping("/{groupId}/members")
	 public ResponseEntity<List<User>> getMembersByGroup(@PathVariable Long groupId) 
	 {
	        List<User> members = groupService.getMembersByGroup(groupId);
	        if (members.isEmpty()) 
	        {
	            return ResponseEntity.noContent().build(); 
	        }
	        return ResponseEntity.ok(members); 
	 }
	 
	 @PostMapping(path = "/save")
	 public Group saveGroup(@RequestBody Group group)
	 {
		 return groupService.saveGroup(group);
	 }
	 
	 @GetMapping(path = "/list")
	 public List<Group> getAllGroups()
	 {
		 return groupService.getAllGroups();
	 }
	 
	 @DeleteMapping(path = "/delete/{id}")
	 public void deleteGroup(@PathVariable(name = "id") Long id)
	 {
		 groupService.deleteGroup(id);
	 }

	 @GetMapping(path = "/group-leader/{leaderId}")
    public ResponseEntity<List<Group>> getGroupsByGroupLeader(@PathVariable(name = "leaderId") Long leaderId) {
        List<Group> result = new ArrayList<>();

        // Fetch the leader's group
        Group leaderGroup = groupService.getGroupByGroupLeader(leaderId);
        if (leaderGroup != null) {
            result.add(leaderGroup);
        }

        // Fetch substitution groups and add them to the result
        List<Group> substitutionGroups = groupService.getSubstitutionGroupsForToday(leaderId);
        result.addAll(substitutionGroups);

        // Return the response
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

	@PostMapping("/{groupId}/substitutions")
	public ResponseEntity<SubstituteLeaders> addSubstitution(
        @PathVariable Long groupId,
        @RequestParam Long substituteLeaderId,
        @RequestParam("substitutionDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate substitutionDate) {
    	SubstituteLeaders substitution = groupService.addSubstitution(groupId, substituteLeaderId, substitutionDate);
    return ResponseEntity.ok(substitution);
}
}
