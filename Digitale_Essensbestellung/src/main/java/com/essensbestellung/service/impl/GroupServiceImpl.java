package com.essensbestellung.service.impl;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essensbestellung.entities.Group;
import com.essensbestellung.entities.GroupMembers;
import com.essensbestellung.entities.SubstituteLeaders;
import com.essensbestellung.entities.User;
import com.essensbestellung.exception.BaseException;
import com.essensbestellung.exception.ErrorMessage;
import com.essensbestellung.exception.MessageType;
import com.essensbestellung.repository.IGroupMembersRepository;
import com.essensbestellung.repository.IGroupRepository;
import com.essensbestellung.repository.ISubstituteLeadersRepository;
import com.essensbestellung.repository.IUserRepository;
import com.essensbestellung.service.IGroupService;

@Service
public class GroupServiceImpl implements IGroupService
{
	@Autowired
	private IGroupRepository groupRepository;
	
	@Autowired
	private IGroupMembersRepository groupMembersRepository;

	@Autowired
    private ISubstituteLeadersRepository substituteLeadersRepository;

	@Autowired
    private IUserRepository userRepository;


	public Group getGroupbyId(Long id)
	{
		Optional<Group> optional = groupRepository.findById(id);
		
		if(optional.isEmpty())
		{
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString()));

		}
		
		return optional.get();	
	}
	
	public List<User> getMembersByGroup(Long groupId) {
        List<GroupMembers> groupMembers = groupMembersRepository.findByGroupId(groupId);
        return groupMembers.stream()
                .map(gm -> gm.getUser())    
                .collect(Collectors.toList());
    }
	
	public Group saveGroup(Group group)
	{
		return groupRepository.save(group);
	}

	public List<Group> getAllGroups()
	{
		List<Group> groups = groupRepository.findAll();
		
		return groups;
	}


	public void deleteGroup(Long id)
	{
		Group group = getGroupbyId(id);
		
		if(group != null)
		{
			groupRepository.delete(group);
		}
		
		if(group == null)
		{
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString()));
		}
	}

	@Override
    public Group getGroupByGroupLeader(Long leaderID) {
        return groupRepository.getGroupByGroupManager(leaderID);
    }

    @Override
    public List<Group> getSubstitutionGroupsForToday(Long leaderID) {
        return groupRepository.getGroupsWithSubstitutionForToday(leaderID);
    }

	@Override
    public SubstituteLeaders addSubstitution(Long groupId, Long substituteLeaderId, LocalDate substitutionDate) {
        
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found for ID: " + groupId));

        
        User substituteLeader = userRepository.findById(substituteLeaderId)
                .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + substituteLeaderId));

        
        SubstituteLeaders substitution = new SubstituteLeaders();
        substitution.setGroup(group);
        substitution.setStellvertreter(substituteLeader);
        substitution.setSubstitutionDate(substitutionDate);

        return substituteLeadersRepository.save(substitution);
    }


}
