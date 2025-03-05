package com.essensbestellung.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.essensbestellung.entities.Group;



@Repository
public interface IGroupRepository extends JpaRepository<Group, Long>
{

    @Query("SELECT g FROM Group g WHERE g.groupLeader.id = :groupLeaderid")
    Group getGroupByGroupManager(@Param("groupLeaderid") Long groupLeaderid);

    @Query("""
       SELECT g 
       FROM SubstituteLeaders s
       JOIN s.group g
       WHERE g.groupLeader.id = :groupLeaderid
         AND s.substitutionDate = CURRENT_DATE
       """)
    List<Group> getGroupsWithSubstitutionForToday(@Param("groupLeaderid") Long groupLeaderid);




}
