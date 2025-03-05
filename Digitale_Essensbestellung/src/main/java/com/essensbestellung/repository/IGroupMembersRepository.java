package com.essensbestellung.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.essensbestellung.entities.Group;
import com.essensbestellung.entities.GroupMemberId;
import com.essensbestellung.entities.GroupMembers;


@Repository
public interface IGroupMembersRepository extends JpaRepository<GroupMembers, GroupMemberId>{
	
    List<GroupMembers> findByGroupId(Long groupId);

    @Query("SELECT gm.group FROM GroupMembers gm WHERE gm.user.id = :userId")
    Group findGroupByUserId(@Param("userId") Long userId);
    
   
    @Modifying
    @Query("DELETE FROM GroupMembers gm WHERE gm.id.userid = :userId")
    void deleteById_UserId(@Param("userId") Long userId);
    
    List<GroupMembers> findById_Userid(Long userid);

}