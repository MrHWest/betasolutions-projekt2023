package com.betasolutions.projekt2023.controller;

import com.betasolutions.projekt2023.model.Project;
import com.betasolutions.projekt2023.model.User;
import com.betasolutions.projekt2023.model.Task;
import com.betasolutions.projekt2023.repository.Repository;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.element.Name;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@org.springframework.stereotype.Controller
public class Controller {

    private Repository repository;

    public Controller(Repository repo) {
        this.repository = repo;
    }

    @GetMapping("/")
    public String index() {
        return "Forside";
    }

    @GetMapping("/test")
    public String showProjektoversigt(HttpSession session, Model model){

        if (session.getAttribute("username") == "admin" &&
                session.getAttribute("passWord") == "admin"){

            return "opgaveoversigt";
        }
        if (session.getAttribute("username") == "udvikler" &&
                session.getAttribute("passWord") == "udvikler"){
            return "opgaveoversigt";
        }
        return "Login";
    }

    /*@GetMapping("/login")
    public String login(HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password){

        User user = repository.getUser(username);

        if (user != null){
            session.setAttribute("id", user.getId());
            session.setAttribute("username", user.getName());
            session.setAttribute("password", user.getPassword());
            session.setAttribute("isAdmin", user.getAdmin());
            return "opgaveoversigt";
        }

        return "Login";

            return "projektoversigt";
        }
        if (session.getAttribute("username") == "udvikler" &&
                session.getAttribute("passWord") == "udvikler"){
            return "projektoversigt";
        }
        return "Login";

    }*/

    @GetMapping("/login")
    public String login(HttpSession session){
        return "Login";
    }


    @PostMapping("/login")
    public String loginCheck(@RequestBody String requestBody){

        //Gem data local
        String username = "";
        String password = "";

        String[] input = requestBody.split("&");
        for (String is : input){
            if (is.startsWith("username=")){
                System.out.println(is);
                username = is;
                username = username.replace("username=", "");
            } if (is.startsWith("password=")){
                System.out.println(is);
                password = is;
                password = password.replace("password=", "");

            }
        }

        //check om data passer med DB

        User user = repository.getUser(username);
        repository.getUser(username);

        //Hvis data ikke passer, send tilbage til login
        if (!password.equals(user.getPassword())){
            return "login";
        } else { //Hvis data passer, send videre til korrekte side.
            if (user.getAdmin()==true){return "opgaveoversigt";
            } else{
                return "udviklerprojektoversigt";
            }

        }

    }


    /*@GetMapping("/login")
    public String login(HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password){

        User user = repository.getUser(username);

        if (user != null){
            session.setAttribute("id", user.getId());
            session.setAttribute("username", user.getName());
            session.setAttribute("password", user.getPassword());
            session.setAttribute("isAdmin", user.getAdmin());
            return "projektoversigt";
        }

        return "Login";

    }*/
    @GetMapping("/nyt_projekt")
    public String newProject() {
        // TODO: Replace this with session data when log-in system has been implemented
        User loggedIn = new User(0, "test", "test", true);

        // Redirect to login-page when not admin
        if(!loggedIn.getAdmin()) {
            return "redirect:/login";
        }
        else {
            return "Opretnytprojekt";
        }
    }

    @PostMapping("/create-project")
    public String createProject(
            @RequestParam("project-name") String name,
            @RequestParam(value = "project-start-date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "project-end-date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {

        if(name.length() > 99 || name.length() == 0 || startDate == null || endDate == null) {
            // Name input is invalid.
            return "redirect:/nyt_projekt?invalidName=true";
        }
        else {
            // All input is OK.
            Project newProject = new Project(name, startDate, endDate);
            repository.addProject(newProject);
            return "redirect:/nyt_projekt?success=true";
        }
    }


    @GetMapping("/opdater_projekt")
    public String updateProject(@RequestParam(name="id", required = true) int id, Model model) {
        // TODO: Replace this with session data when log-in system has been implemented
        User loggedIn = new User(0, "test", "test", true);

        // Redirect to login-page when not admin
        if(!loggedIn.getAdmin()) {
            return "redirect:/login";
        }
        else {
            Project project = repository.getProjectById(id);
            model.addAttribute("project", project);
            return "opdaterprojekt";
        }
    }
    @PostMapping("/update-project")
    public String executeProjectUpdate(
            @RequestParam("project-id") int id,
            @RequestParam("project-name") String name,
            @RequestParam(value = "project-start-date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "project-end-date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {

        if (name.length() > 99 || name.length() == 0 || startDate == null || endDate == null) {
            // Name input is invalid.
            return "redirect:/opdater_projekt?id=" + id + "&invalidName=true";
            //return "redirect:/opdater_projekt?id=" + id;
        } else {
            // All input is OK.
            Project newProject = new Project(id, name, startDate, endDate);
            repository.updateProject(newProject);
            return "redirect:/opdater_projekt?id=" + id + "&success=true";
            //return "redirect:/opdater_projekt?id=" + id;
        }
    }
    @GetMapping("/brugeroversigt")
    public String getAllUsers(Model model){
        List<User> users = repository.getAllUsers();
        model.addAttribute("users", users);
        return "brugeroversigt";

    }


    @GetMapping("/opretnybruger")
    public String newUser(){
        return "opretnybruger";
    }

    @PostMapping("/opretnybruger")
    public String newUser(@RequestBody String requestBody){

        System.out.println("RequestBody: " + requestBody);
        //Gem data local
        String username = "";
        String password = "";
        boolean isAdmin = false;

        String[] input = requestBody.split("&");
        for (String userInput : input){
            if (userInput.startsWith("username=")){
                System.out.println("username " + userInput);
                username = userInput;
                username = username.replace("username=", "");
            } if (userInput.startsWith("password=")){
                System.out.println("password " + userInput);
                password = userInput;
                password = password.replace("password=", "");
            } if (userInput.equals("isAdmin=on")){
                isAdmin = true;
                System.out.println("isAdmin " + isAdmin);
            }

        }

        //Tjek om username allerede existere

        User user = repository.getUser(username);
        repository.getUser(username);

        //Hvis ja, bed om ny username
        if(username.length() > 0 && password.length() > 0) {
            if (!username.equals(user.getName())) {
                user.setName(username);
                user.setPassword(password);
                user.setAdmin(isAdmin);

                repository.addUser(username, password, isAdmin);
                return "redirect:/brugeroversigt";

            } else { //Hvis nej, opret ny bruger

                return "opretnybruger";
            }
        } return "opretnybruger";
    }

    @PostMapping("/deleteuser")
    public String deleteUser(@RequestParam("userId") int userId) {
        repository.deleteUserById(userId);

        return "redirect:/brugeroversigt";
    }

    @GetMapping("/updateuser")
    public String updateUser(@RequestParam("id") int updateId, Model model){
        User updateUser = repository.getUserBasedOnId(updateId);

        model.addAttribute("user", updateUser);

        return "updateuser";
    }

    @PostMapping("/updateuser")
    public String updateUser(@RequestParam("name") String name,
                             @RequestParam("password") String password,
                             @RequestParam(value = "admin", defaultValue = "false") boolean admin,
                             @RequestParam("id") int id){

        User updateUser = new User(id, name, password, admin);
        repository.updateUser(updateUser);

        return "redirect:/brugeroversigt";
    }



    //Ahmad's HomeController

    @GetMapping("/opgaveoversigt")
    public String getAllTasks(Model model, HttpSession session){
        //Hent opgaver fra session eller opret en ny liste
        List<Task> tasks = getTasksFromSession(session);

        //tilføj opgaver til modellen, så de kan vises i listen
        model.addAttribute("tasks", tasks);

        //returner navnet på listen
        return "/opgaveoversigt";
    }

    @GetMapping("/projektoversigt")
    public String projectOverview(Model model) {
        // TODO: Replace this with session data when log-in system has been implemented
        User loggedIn = new User(0, "test", "test", true);

        // Redirect to login page if user not logged in
        if(loggedIn == null) {
            return "redirect:/login";
        }

        // Show project overview
        List<Project> projects = repository.getAllProjects();
        model.addAttribute("projects", projects);
        return "projektoversigt";
    }

    @PostMapping("/opretnyopgave")
    public String createTask(@RequestParam String name, @RequestParam int startDate, @RequestParam int endDate, HttpSession session){
        // opret en ny opgave baseret på vores parametre
        Task task = new Task(name, startDate, endDate);

        //hent opgaver fra session eller opret en ny liste
        List<Task> tasks = getTasksFromSession(session);

        //tilføj den nye opgave til tasklisten
        tasks.add(task);

        //gem opdateret opgaver i session
        session.setAttribute("tasks", tasks);

        //omdirigerer til hovedsiden for opgaver
        return "redirect:/opretnyopgave";
    }

    @PostMapping("/update{taskId}")
    public String updateTask(@PathVariable int taskId, @RequestParam String name, @RequestParam int startDate, @RequestParam int endDate, HttpSession session){
        //hent opgaver fra session eller opret en ny liste
        List<Task> tasks = getTasksFromSession(session);

        //find den opgave, der skal opdateres, baseret på taskId
        Task task = findTaskById(taskId, tasks);

        //opdater opgavens navn startdato og slutdato
        if (task != null){
            task.setName(name);
            task.setStartDate(startDate);
            task.setEndDate(endDate);
        }
        //gem opdateret opgaver i session
        session.setAttribute("tasks", tasks);

        //omdirigerer til hovedsiden for opgaver
        return "redirect:/opgaveoversigt";
    }
    @PostMapping("/delete{taskId}")
    public String deleteTask(@PathVariable int taskId, HttpSession session){
        //hent opgaver fra session eller opret en ny liste
        List<Task> tasks = getTasksFromSession(session);

        //find opgaven der skal slettes baseret på taskId
        Task task = findTaskById(taskId, tasks);

        //fjern opgaven fra tasklisten, hvis den bliver fundet
        if (task != null){
            tasks.remove(task);
        }

        //gem opdaterede opgaver i sessionen
        session.setAttribute("tasks", tasks);

        //omdirigerer til hovedsiden for opgaver
        return "redirect:/opgaveoversigt";
    }

    //hjælpefunktion til at hente opgaver fra sessionen eller oprette en ny liste
    private List<Task> getTasksFromSession(HttpSession session){
        List<Task> tasks = (List<Task>) session.getAttribute("tasks");
        if (tasks == null){
            tasks = new ArrayList<>();
            session.setAttribute("tasks", tasks);
        }
        return tasks;
    }

    //hjælpefunktion til at finde en opgave baseret på taskId
    private Task findTaskById(int taskId, List<Task> tasks){
        for(Task task : tasks){
            if (task.getId() == taskId){
                return task;
            }
        }
        return null;
    }

}
