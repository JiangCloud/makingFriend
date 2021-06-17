package com.mango.makingfriend.dao;

import com.mango.makingfriend.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GroupDao extends JpaRepository<Group,String> {

    @Query("select g.groupId,g.groupName,g.groupAvatar  from Group g where g.groupId=?1")
    List<Object> findGroupInfo(String groupId);

    @Query("update Group g set g.groupName=?1 where g.groupId=?2")
    @Modifying
    @Transactional
    void updateGroupName(String groupName,String groupId);


    @Query("update Group g set g.groupName=:#{#group.groupName},g.last_modified=:#{#group.last_modified},g.owner=:#{#group.owner}" +
            " where g.groupId=:#{#group.groupId}")
    @Modifying
    @Transactional
    void updateGroup(Group group);

}
