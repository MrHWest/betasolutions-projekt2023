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
        String UPDATEPROJECT_QUERY = "UPDATE beta_solutions_db.projects SET name = ?, start_date = ?, end_date = ?  WHERE id= ?";
        ConnectionManager connectionManager = new ConnectionManager();

        System.out.println(project.getId() + project.getName() + project.getStartDate() + project.getSlutDate() + project.isDone());

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
        //sql query for at hente alle opgaver fra databasen
        String getAllTask_query = "SELECT * FROM beta_solutions_db.tasks";
        //opretter en ConnectionManager til at håndtere forbindelsen til databasen
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            //opretter en forbindelse til databasen
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            //opretter en PreparedStatement til at udføre sql-queryen
            PreparedStatement statement = connection.prepareStatement(getAllTask_query);
            //udfører sql-query
            statement.execute();
            System.out.println("går igennem igen");
        } catch(SQLException e) {
            //håndterer eventuelle sql-exceptions, der kan opstå
            System.out.println("fejl");
            e.printStackTrace();
        }
        return null;
    }

    public void addTask(Task newTask){
        //sql-query for at tilføje en ny opgave til databasen
        String ADDTASK_QUERY = "INSERT INTO beta_solutions_db.tasks(name, start_date, end_date, is_pending, fk_project_id, fk_tasks_id) VALUES(?,?,?,false,?,?)";
        //opretter en ConnectionManger til at håndtere forbindelsen til databasen
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            //opretter en forbindelse til databasen
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            //opretter en PreparedStatement til at udføre sql-queryen
            PreparedStatement statement = connection.prepareStatement(ADDTASK_QUERY);
            //konverterer LocalDate-objekter til java.sql.date-objekter
            Date sqlStartDate = Date.valueOf(newTask.getStartDate());
            Date sqlEndDate = Date.valueOf(newTask.getEndDate());
            //sætter værdierne i PreparedStatement
            statement.setString(1, newTask.getName());
            statement.setDate(2, sqlStartDate);
            statement.setDate(3, sqlEndDate);
            statement.setInt(4, newTask.getFk_project_id());
            if(newTask.getFk_tasks_id() != null) {
                statement.setInt(5, newTask.getFk_tasks_id());
            }
            else {
                statement.setNull(5, java.sql.Types.NULL);
            }
            //udfører sql-query
            System.out.println("går igennem");
            statement.execute();

        } catch(SQLException e){
            //håndterer eventuelle sql-exceptions, der kan opstå
            System.out.println("Fejl i oprettelse");
            e.printStackTrace();
        }
    }

    public void updateTask(Task task) {
        // sql-query for at opdatere en eksisterende opgave i databasen
        final String UPDATETASK_QUERY = "UPDATE beta_solutions_db.tasks SET name = ?, start_date = ?, end_date = ? WHERE id = ?";
        //opretter en ConnectionManager til at håndtere forbindelsen til databasen
        ConnectionManager connectionManager = new ConnectionManager();
        try {
            //opretter forbindelse til databasen
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);

            connection.setAutoCommit(false); //slå autocommit fra
            //opretter en PreparedStatement til at udføre sql-queryen
            PreparedStatement statement = connection.prepareStatement(UPDATETASK_QUERY);
            //konverter LocalDate-objekter toil java.sql.Date-objekter
            Date sqlStartDate = Date.valueOf(task.getStartDate());
            Date sqlEndDate = Date.valueOf(task.getEndDate());
            //sætter værdierne i PreparedStatement
            statement.setString(1, task.getName());
            statement.setDate(2, sqlStartDate);
            statement.setDate(3, sqlEndDate);
            statement.setInt(4, task.getId());
            //udfør sql-query
            statement.executeUpdate();
            //udfør en commit for at gemme ændringer i databasen
            connection.commit(); //udfør commit her
            System.out.println("hej");
        } catch (SQLException e) {
            //håndterer eventuelle sql-exceptions, der kan opstå
            System.out.println("Opdatering fejlede");
            e.printStackTrace();
        }
    }


    public void deleteTaskById(int id){
        // SQL-query for at slette en opgave fra databasen baseret på id
        String DELETETASK_QUERY = "DELETE FROM beta_solutions_db.tasks WHERE id = ?";
        // Opretter en ConnectionManager til at håndtere forbindelsen til database
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            // Opretter en forbindelse til databasen
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            // Opretter en PreparedStatement til at udføre SQL-queryen
            PreparedStatement statement = connection.prepareStatement(DELETETASK_QUERY);
            // Sætter værdien af id i PreparedStatement
            statement.setInt(1, id);
            //udfører sql-quryen
            statement.executeUpdate();
            System.out.println("opgaven med id " + id + " er blevet slettet.");
        } catch (SQLException e){
            // Håndterer eventuelle SQLExceptions, der kan opstå
            System.out.println("Kunne ikke slette task/opgave");
            e.printStackTrace();
        }
    }
    public Task getTaskById(int id) {
        //opretter et tomt task-objekt
        Task result = new Task();
        // SQL-query for at hente en opgave fra databasen baseret på id
        String SELECTTASK_QUERY = "SELECT name, start_date, end_date, fk_project_id FROM beta_solutions_db.tasks WHERE id = ?";
        //opretter en ConnectionManager til at håndtere forbindelen til databasen
        ConnectionManager connectionManager = new ConnectionManager();
        try {
            //opretter en forbindelse til databasen
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            //opretter en PreparedStatement til at udføre sql-queryen
            PreparedStatement statement = connection.prepareStatement(SELECTTASK_QUERY);
            //sætter værdien af id i PreparedStatement
            statement.setInt(1, id);

            //udfører sql-queryen og henter resultatet som en ResultSet
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            //sætter værdierne fra ResultSet i Task-objektet
            result.setId(id);
            result.setName(resultSet.getString(1));
            result.setStartDate(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(resultSet.getDate(2))));
            result.setEndDate(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(resultSet.getDate(3))));
            result.setFk_project_id(resultSet.getInt(4));
        } catch (SQLException e) {
            //håndterer eventuelle sql-exceptions, der kan opstå
            System.out.println("Kunne ikke hente projekt-oplysninger");
            e.printStackTrace();
        }

        return result;
    }


    public List<Task> getTasksByProjectId(int projectId) {
        List<Task> result = new ArrayList<Task>();
        String getAllTask_query = "SELECT * FROM beta_solutions_db.tasks WHERE beta_solutions_db.tasks.fk_project_id = ? AND beta_solutions_db.tasks.fk_tasks_id IS NULL";
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement statement = connection.prepareStatement(getAllTask_query);
            statement.setInt(1, projectId);
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                result.add(new Task(
                        results.getInt(1),
                        results.getString(2),
                        LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(results.getDate(3))),
                        LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(results.getDate(4))),
                        results.getBoolean(5),
                        projectId
                ));
            }
        } catch(SQLException e) {
            System.out.println("Error while fetching task data");
            e.printStackTrace();
        }
        return result;
    }

    public List<Task> getTasksByParentId(int parentId) {
        List<Task> result = new ArrayList<Task>();
        String getAllTask_query = "SELECT * FROM beta_solutions_db.tasks WHERE beta_solutions_db.tasks.fk_tasks_id = ?";
        ConnectionManager connectionManager = new ConnectionManager();
        try{
            Connection connection = connectionManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement statement = connection.prepareStatement(getAllTask_query);
            statement.setInt(1, parentId);
            ResultSet results = statement.executeQuery();
            while(results.next()) {
                result.add(new Task(
                        results.getInt(1),
                        results.getString(2),
                        LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(results.getDate(3))),
                        LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(results.getDate(4))),
                        results.getBoolean(5),
                        parentId
                ));
            }
        } catch(SQLException e) {
            System.out.println("Error while fetching task data");
            e.printStackTrace();
        }
        return result;
    }
}
