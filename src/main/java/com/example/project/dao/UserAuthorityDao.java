package com.example.project.dao;

import com.example.project.model.UserAuthority;

import java.util.List;

public interface UserAuthorityDao {
    List<UserAuthority> listUserAuthorities();
    UserAuthority getById(long id);
    UserAuthority getUserAuthorityByName(String name);
    void add(UserAuthority userAuthority);
    void edit(UserAuthority userAuthority);
    void delete(UserAuthority userAuthority);
}
