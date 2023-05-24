package com.betasolutions.projekt2023;

import com.betasolutions.projekt2023.model.Task;

import com.betasolutions.projekt2023.model.User;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
/*import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.mockito.Mockito.*;*/

import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Projekt2023ApplicationTests {
    @Test
    void contextLoads() {
    }

   /* public class AhmadTestClass{
       @Test
       public void testGetAllTasks(){
           //Arrange, opret en liste af opgaver med start og slutdato
            List<Task> tasks = new ArrayList<>();
            tasks.add(new Task(1, "Task1", 1, 1));
            tasks.add(new Task(2, "Task2", 2, 2));

            //Simuler at hente opgaver fra sessionen
           session.setAttribute("tasks", tasks);

           //Act, kald getAllTasks metoden
           String viewName = Controller.getAllTasks(model, session);

           //Assert, vverificer at den returnerede visningsside er "tasks"
           assertEquals("tasks", viewName);
       }
    }*/


    /*public class UserManagementTest {
        private UserManagement userManagement;
        private ConnectionManager connectionManager;
        private Connection connection;
        private PreparedStatement statement;

        @Before
        public void setup() throws SQLException {
            userManagement = new UserManagement();
            connectionManager = mock(ConnectionManager.class);
            connection = mock(Connection.class);
            statement = mock(PreparedStatement.class);

            when(connectionManager.getConnection(anyString(), anyString(), anyString())).thenReturn(connection);
            when(connection.prepareStatement(anyString())).thenReturn(statement);
        }

        @Test
        public void testAddUser() throws SQLException {
            String name = "John";
            String password = "password123";
            boolean isAdmin = true;

            userManagement.addUser(name, password, isAdmin);

            verify(connectionManager).getConnection(anyString(), anyString(), anyString());
            verify(connection).prepareStatement("INSERT INTO beta_solutions_db.users(name, password, is_admin) VALUES (?, ?, ?)");
            verify(statement).setString(1, name);
            verify(statement).setString(2, password);
            verify(statement).setBoolean(3, isAdmin);
            verify(statement).execute();
        }
    }*/

}
