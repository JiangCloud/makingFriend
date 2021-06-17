package com.mango.makingfriend.service;


import com.mango.makingfriend.model.Group;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GroupService {
    boolean updateGroupName(String groupName,String groupId);

    List<Group> saveGroup(Iterable<Group> iterable);
    Group save(Group group);

    Page<Group> getGroupInfo(Example<Group> example);

    boolean deleteGroup(List<Group> groups);

    boolean updateGroup(Group group);


}
