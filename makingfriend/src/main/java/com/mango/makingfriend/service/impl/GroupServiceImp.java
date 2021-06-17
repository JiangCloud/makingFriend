package com.mango.makingfriend.service.impl;

import com.linln.common.data.PageSort;
import com.mango.makingfriend.dao.GroupDao;
import com.mango.makingfriend.model.Group;
import com.mango.makingfriend.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GroupServiceImp implements GroupService {

    @Autowired
    private GroupDao groupDao;
    @Override
    public boolean updateGroupName(String groupName, String groupId) {
        boolean flag;
        try {
            groupDao.updateGroupName( groupName,  groupId);
            flag=true;
        } catch (Exception e) {
            flag=false;
            e.printStackTrace();
        }

        return flag;
    }

    @Override
    public List<Group> saveGroup(Iterable<Group> iterable) {

        return  groupDao.saveAll(iterable);
    }

    @Override
    public Group save(Group group) {


        return groupDao.save(group);
    }

    @Override
    public Page<Group> getGroupInfo(Example<Group> example) {


       PageRequest pageRequest= PageSort.pageRequest("created", Sort.Direction.DESC);


       return  groupDao.findAll(example,pageRequest);
    }

    @Override
    public boolean deleteGroup(List<Group> groups) {

        try {
            groupDao.deleteInBatch(groups);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return  false;

        }

        return true;
    }

    @Override
    public boolean updateGroup(Group group) {
        try {
            groupDao.updateGroup(group);
        } catch (Exception e) {

           log.debug(e.getMessage());
            return false;
        }


        return true;

    }
}
