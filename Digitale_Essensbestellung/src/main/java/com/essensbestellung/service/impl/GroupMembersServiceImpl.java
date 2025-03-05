package com.essensbestellung.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essensbestellung.entities.Group;
import com.essensbestellung.entities.GroupMembers;
import com.essensbestellung.repository.IGroupMembersRepository;
import com.essensbestellung.service.IGroupMembersService;


@Service
public class GroupMembersServiceImpl implements IGroupMembersService
{
	@Autowired
	private IGroupMembersRepository groupMembersRepository;

/*	public GroupMembers getGroupMemberbyId(Long id)
	{
		Optional<GroupMembers> optional = groupMembersRepository.findById(id);
		
		if(optional.isEmpty())
		{
			return null;
		}
		
		return optional.get();	
	}
*/	
	public GroupMembers saveGroupMembers(GroupMembers groupMembers)
	{
		return groupMembersRepository.save(groupMembers);
	}

	public List<GroupMembers> getAllGroupMembers()
	{
		List<GroupMembers> groupMembers = groupMembersRepository.findAll();
		
		return groupMembers;
	}
	public Group getGroupByUserId(Long userId) {
        return groupMembersRepository.findGroupByUserId(userId);
    }
	
/*	public void deleteGroupMember(Long id)
	{
		GroupMembers groupMember = getGroupMemberbyId(id);
		
		if(groupMember != null)
		{
			groupMembersRepository.delete(groupMember);
		}
	}
*/	
}
