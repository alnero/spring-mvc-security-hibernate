package com.example.project;

import com.example.project.model.User;
import com.example.project.model.UserAuthority;
import com.example.project.model.UserRole;
import com.example.project.service.UserAuthorityService;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DbInit {
    private UserService userService;
    private UserAuthorityService userAuthorityService;

    @Autowired
    public DbInit(UserService userService, UserAuthorityService userAuthorityService) {
        this.userService = userService;
        this.userAuthorityService = userAuthorityService;
    }

    @PostConstruct
    private void postConstruct() {
        // add authorities to DB
        for (UserRole userRole : UserRole.values()) {
            userAuthorityService.add(new UserAuthority(userRole.name()));
        }

        // add admin to DB
        User admin = new User(
                "admin",
                "admin",
                (byte) 1);
        admin.setPassword("admin");
        admin.setUserAuthority(userAuthorityService.getUserAuthorityByName(UserRole.ADMIN.name()));
        userService.add(admin);
    }
}