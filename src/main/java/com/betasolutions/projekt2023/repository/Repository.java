package com.betasolutions.projekt2023.repository;

import com.betasolutions.projekt2023.model.User;
import com.betasolutions.projekt2023.model.Project;
import com.betasolutions.projekt2023.utility.ConnectionManager;
import com.betasolutions.projekt2023.model.Task;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

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

    public User getUser(String name){
        User user = new User();

        String GETUSER_QUERY = "SELECT * FROM USER where name = ?";
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            //Execute QUERY
            PreparedStatement statement = connection.prepareStatement(GETUSER_QUERY);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            String username = name;
            int id = resultSet.getInt("id");
            String password = resultSet.getString("password");
            boolean isAdmin = resultSet.getBoolean("is_admin");
            user = new User(id, username, password, isAdmin);

        } catch (SQLException e){
            System.out.println("Fejl i oprettelse");
            e.printStackTrace();
        }
        return user;
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

    public Project getProjectById(int id) {
        Project result = new Project();
        String SELECTPROJECT_QUERY = "SELECT name, start_date, end_date FROM beta_solutions_db.projects WHERE id = ?";
        ConnectionManager connectionManager = new ConnectionManager();
        try {
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement statement = connection.prepareStatement(SELECTPROJECT_QUERY);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            result.setId(id);
            result.setName(resultSet.getString(1));
            result.setStartDate(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(resultSet.getDate(2))));
            result.setSlutDate(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(resultSet.getDate(3))));
        } catch (SQLException e) {
            System.out.println("Kunne ikke hente projekt-oplysninger");
            e.printStackTrace();
        }

        return result;
    }

    public void updateProject(Project project) {
        // Gør QUERY klar
        String UPDATEPROJECT_QUERY = "UPDATE beta_solutions_db.projects SET name = ?, start_date = ?, end_date = ? WHERE id= ?";
        ConnectionManager connectionManager = new ConnectionManager();

        System.out.println(project.getId() + project.getName() + project.getStartDate() + project.getSlutDate());

        try{
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            //Execute QUERY
            PreparedStatement statement = connection.prepareStatement(UPDATEPROJECT_QUERY);
            statement.setString(1, project.getName());
            statement.setDate(2, Date.valueOf(project.getStartDate()));
            statement.setDate(3, Date.valueOf(project.getSlutDate()));
            statement.setInt(4, project.getId());
            statement.execute();

        } catch (SQLException e){
            System.out.println("Fejl i opdatering");
            e.printStackTrace();
        }
    }
    public void addTask(Task newTask){
        String ADDTASK_QUERY = "INSERT INTO beta_solutions_db.tasks(name, startDate, endDate) VALUES(?,?,?)";
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            //Execute QUERY
            PreparedStatement statement = connection.prepareStatement(ADDTASK_QUERY);
            statement.setString(1, newTask.getName());
            statement.setInt(2, newTask.getStartDate());
            statement.setInt(3, newTask.getEndDate());
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
