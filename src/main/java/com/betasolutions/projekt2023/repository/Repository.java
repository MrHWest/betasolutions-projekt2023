package com.betasolutions.projekt2023.repository;

import com.betasolutions.projekt2023.model.Task;
import com.betasolutions.projekt2023.model.User;
import com.betasolutions.projekt2023.model.Project;
import com.betasolutions.projekt2023.utility.ConnectionManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;


@org.springframework.stereotype.Repository
public class Repository {

    @Value("${spring.datasource.url}")
    private String DB_URL;

    @Value("${spring.datasource.username}")
    private String UID;

    @Value("${spring.datasource.password}")
    private String PWD;

    //Funktion til at tilføje nye bruger
    public void addUser(String name, String password, boolean isAdmin){

        // Gør QUERY klar
        String ADDUSER_QUERY = "INSERT INTO beta_solutions_db.users(name, password, is_admin) VALUES (?, ?, ?)";
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            //Execute QUERY
            PreparedStatement statement = connection.prepareStatement(ADDUSER_QUERY);
            statement.setString(1, name);
            statement.setString(2, password);
            statement.setBoolean(3, isAdmin);
            statement.execute();

        } catch (SQLException e){
            System.out.println("Fejl i oprettelse");
            e.printStackTrace();
        }
    }

    //Funktion til at opdater bruger infomation
    public void updateUser(User user){

        // Gør QUERY klar
        String UPDATEUSER_QUERY = "UPDATE beta_solutions_db.users SET name = ?, password = ?, is_admin = ? WHERE id = ?";
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement statement = connection.prepareStatement(UPDATEUSER_QUERY);

            String name = user.getName();
            String password = user.getPassword();
            boolean isAdmin = user.getAdmin();
            //int id = user.getId();

            statement.setString(1, name);
            statement.setString(2, password);
            statement.setBoolean(3, isAdmin);
            //statement.setInt(4, id);

            statement.executeUpdate();

        } catch (SQLException e){
            System.out.println("Opdatering fejlede");
            e.printStackTrace();
        }

    }

    public void deleteUserById(int id){
        String DELETEUSER_QUERY = "DELETE FROM beta_solutions_db.user WHERE id = ?";
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement statement = connection.prepareStatement(DELETEUSER_QUERY);

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e){
            System.out.println("Kunne ikke slette bruger");
            e.printStackTrace();
        }

    }

    public void addProject(Project newProject) {
        // Gør QUERY klar
        String ADDPROJECT_QUERY = "INSERT INTO beta_solutions_db.projects(name, start_date, end_date) VALUES (?, ?, ?)";
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            //Execute QUERY
            PreparedStatement statement = connection.prepareStatement(ADDPROJECT_QUERY);
            statement.setString(1, newProject.getName());
            statement.setDate(2, Date.valueOf(newProject.getStartDate()));
            statement.setDate(3, Date.valueOf(newProject.getSlutDate()));
            statement.execute();

        } catch (SQLException e){
            System.out.println("Fejl i oprettelse");
            e.printStackTrace();
        }
    }
    public void addTask(String name, int startDate, int endDate){
        String ADDTASK_QUERY = "INSERT INTO beta_solutions_db.tasks(name, startDate, endDate) VALUES(?,?,?)";
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            //Execute QUERY
            PreparedStatement statement = connection.prepareStatement(ADDTASK_QUERY);
            statement.setString(1, name);
            statement.setInt(2, startDate);
            statement.setInt(3, endDate);
            statement.execute();

        } catch(SQLException e){
            System.out.println("Fejl i oprettelse");
            e.printStackTrace();
        }
    }
    public void updateTask(Task task){
        //Gør QUERY klar
        String UPDATETASK_QUERY = "UPDATE beta_solutions_db.tasks SET name = ?, startDate = ?, endDate = ? WHERE id= ?";
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement statement = connection.prepareStatement(UPDATETASK_QUERY);

            String name = task.getName();
            int startDate = task.getStartDate();
            int endDate = task.getEndDate();
            //int id = task.getId();

            statement.setString(1, name);
            statement.setInt(2, startDate);
            statement.setInt(3, endDate);
            //statement.setInt(4, id);

            statement.executeUpdate();
        } catch(SQLException e){
            System.out.println("Opdatering fejlede");
            e.printStackTrace();
        }
    }
    public void deleteTaskById(int id){
        String DELETETASK_QUERY = "DELETE FROM beta_solutions_db.tasks WHERE id = ?";
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement statement = connection.prepareStatement(DELETETASK_QUERY);

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Kunne ikke slette task/opgave");
            System.out.println("whatever");
            e.printStackTrace();
        }
    }

}
