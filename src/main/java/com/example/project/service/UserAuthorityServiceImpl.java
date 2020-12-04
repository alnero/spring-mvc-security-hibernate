package com.example.project.service;

import com.example.project.dao.UserAuthorityDao;
import com.example.project.model.UserAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserAuthorityServiceImpl implements UserAuthorityService {
    private UserAuthorityDao userAuthorityDao;

    @Autowired
    public UserAuthorityServiceImpl(UserAuthorityDao userAuthorityDao) {
        this.userAuthorityDao = userAuthorityDao;
    }

    @Override
    public List<UserAuthority> listUserAuthorities() {
        return userAuthorityDao.listUserAuthorities();
    }

    @Override
    public UserAuthority getById(long id) {
        return userAuthorityDao.getById(id);
    }

    @Override
    public UserAuthority getUserAuthorityByName(String name) {
        return userAuthorityDao.getUserAuthorityByName(name);
    }

    @Override
    public void add(UserAuthority userAuthority) {
        userAuthorityDao.add(userAuthority);
    }

    @Override
    public void edit(UserAuthority userAuthority) {
        userAuthorityDao.edit(userAuthority);
    }

    @Override
    public void delete(UserAuthority userAuthority) {
        userAuthorityDao.delete(userAuthority);
    }
}
