package com.betasolutions.projekt2023.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User dummyUser = new User();

    @BeforeEach
    void dummyUser(){
        dummyUser = new User(1, "admin", "1234", true);
    }

    @Test
    void setNameTest() {

        //String name = "admin";

        assertEquals("admin", dummyUser.getName());


        dummyUser.setName("klaus");
        assertEquals("klaus", dummyUser.getName());
    }


    @Test
    void setName() {
    }
}