package com.betasolutions.projekt2023.repository;

import com.betasolutions.projekt2023.model.Task;
import com.betasolutions.projekt2023.model.User;
import com.betasolutions.projekt2023.model.Project;
import com.betasolutions.projekt2023.utility.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

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
            int id = user.getId();

            statement.setString(1, name);
            statement.setString(2, password);
            statement.setBoolean(3, isAdmin);
            statement.setInt(4, id);

            statement.executeUpdate();

        } catch (SQLException e){
            System.out.println("Opdatering fejlede");
            e.printStackTrace();
        }

    }

    public void deleteUserById(int id){
        String DELETEUSER_QUERY = "DELETE FROM beta_solutions_db.users WHERE id = ?";
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

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();

        String GETALLUSERS_QUERY = "SELECT * FROM beta_solutions_db.users";
        ConnectionManager connectionManager = new ConnectionManager();

        try {
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement statement = connection.prepareStatement(GETALLUSERS_QUERY);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setAdmin(resultSet.getBoolean("is_admin"));

                users.add(user);
            }

        } catch (SQLException e){
            System.out.println("Kunne ikke hente listen af alle /User ");
            e.printStackTrace();
        }
        return users;
    }

    public User getUserBasedOnId(int id){
        User user = new User();

        String GETUSERBASEDONID_QUERY = "SELECT * FROM beta_solutions_db.users WHERE id = ?";
        ConnectionManager connectionManager = new ConnectionManager();

        try {
            //db connection
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            //prepared statement
            PreparedStatement statement = connection.prepareStatement(GETUSERBASEDONID_QUERY);
            //set parameter
            statement.setInt(1, id);
            //execute statement
            ResultSet resultSet = statement.executeQuery();

            //få user ud af resultSet
            resultSet.next();
            String name = resultSet.getString(2);
            String password = resultSet.getString(3);
            boolean isAdmin = resultSet.getBoolean(4);

            user.setName(name);
            user.setPassword(password);
            user.setAdmin(isAdmin);
            user.setId(id);

        } catch (SQLException e){
            System.out.println("Could not find user based on id");
            e.printStackTrace();
        }
        return user;
    }

    //Start på John's kode
    public User getUser(String name){
        User user = new User();

        String GETUSER_QUERY = "SELECT * FROM beta_solutions_db.users where name = ?";
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            //Execute QUERY
            PreparedStatement statement = connection.prepareStatement(GETUSER_QUERY);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                int id = resultSet.getInt("id");
                String password = resultSet.getString("password");
                boolean isAdmin = resultSet.getBoolean("is_admin");
                user = new User(id, name, password, isAdmin);
            } else {
                System.out.println("User with username " + name + " does not exist");
            }

        } catch (SQLException e){
            System.out.println("[Repository::getUser] Fejl i getUser funktion " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }
    //Slut på John's kode

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

    public List<Project> getAllProjects() {
        String SELECTPROJECTS_QUERY = "SELECT * FROM beta_solutions_db.projects";
        ConnectionManager connectionManager = new ConnectionManager();
        List<Project> result = new ArrayList<Project>();
        try {
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement statement = connection.prepareStatement(SELECTPROJECTS_QUERY);

            ResultSet results = statement.executeQuery();
            while(results.next()) {
                result.add(new Project(
                        results.getInt(1),
                        results.getString(2),
                        LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(results.getDate(3))),
                        LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(results.getDate(4)))
                ));
            }
        } catch (SQLException e) {
            System.out.println("Kunne ikke hente projekter");
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

    public List<Task> getAllTasks(){
        String getAllTask_query = "SELECT * FROM beta_solutions_db.tasks";
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement statement = connection.prepareStatement(getAllTask_query);
            statement.execute();
            System.out.println("Du ved det");
        } catch(SQLException e) {
            System.out.println("fejl");
            e.printStackTrace();
        }
        return null;
    }

    public void addTask(Task newTask){
        String ADDTASK_QUERY = "INSERT INTO beta_solutions_db.tasks(name, start_date, end_date) VALUES(?,?,?)";
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            //Execute QUERY
            PreparedStatement statement = connection.prepareStatement(ADDTASK_QUERY);
            statement.setString(1, newTask.getName());
            Date sqlStartDate = Date.valueOf(newTask.getStartDate());

            Date sqlEndDate = Date.valueOf(newTask.getEndDate());

            statement.setDate(2, sqlStartDate);
            statement.setDate(3, sqlEndDate);
            System.out.println("går igennem");
            //statement.execute();

        } catch(SQLException e){
            System.out.println("Fejl i oprettelse");
            e.printStackTrace();
        }
    }

    public void updateTask(Task task) {
        // Gør QUERY klar
        final String UPDATETASK_QUERY = "UPDATE beta_solutions_db.tasks SET name = ?, start_date = ?, end_date = ? WHERE id = ?";
        /*int id = task.getId();
        String name = task.getName();
        LocalDate startDate = task.getStartDate();
        LocalDate endDate = task.getEndDate();*/
        ConnectionManager connectionManager = new ConnectionManager();
        try {
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            connection.setAutoCommit(false); //slå autocommit fra
            PreparedStatement statement = connection.prepareStatement(UPDATETASK_QUERY);
            Date sqlStartDate = Date.valueOf(task.getStartDate());
            Date sqlEndDate = Date.valueOf(task.getEndDate());
            statement.setString(1, task.getName());
            statement.setDate(2, sqlStartDate);
            statement.setDate(3, sqlEndDate);
            statement.setInt(4, task.getId());
            statement.executeUpdate();
            connection.commit(); //udfør commit her
            System.out.println("hej");
        } catch (SQLException e) {
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
            System.out.println("opgaven med id " + id + " er blevet slettet.");
        } catch (SQLException e){
            System.out.println("Kunne ikke slette task/opgave");
            e.printStackTrace();
        }
    }
    public Task getTaskById(int id) {
        Task result = new Task();
        String SELECTTASK_QUERY = "SELECT name, start_date, end_date FROM beta_solutions_db.tasks WHERE id = ?";
        ConnectionManager connectionManager = new ConnectionManager();
        try {
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement statement = connection.prepareStatement(SELECTTASK_QUERY);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            result.setId(id);
            result.setName(resultSet.getString(1));
            result.setStartDate(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(resultSet.getDate(2))));
            result.setEndDate(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(resultSet.getDate(3))));
        } catch (SQLException e) {
            System.out.println("Kunne ikke hente projekt-oplysninger");
            e.printStackTrace();
        }

        return result;
    }
}
