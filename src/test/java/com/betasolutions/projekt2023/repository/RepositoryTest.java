package com.betasolutions.projekt2023.repository;

import com.betasolutions.projekt2023.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RepositoryTest {
    @Autowired
    private Repository repository;
    @Test
    void addUser() {
        User user = new User("test","test",true);
        repository.addUser(user.getName(),user.getPassword(),user.getAdmin());

        User foundUser = repository.getUser(user.getName());
        assertEquals(user.getName(),foundUser.getName());
        assertEquals(user.getId(),foundUser.getId());


    }
}