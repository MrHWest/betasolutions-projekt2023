package com.betasolutions.projekt2023.controller;

import com.betasolutions.projekt2023.model.Project;
import com.betasolutions.projekt2023.model.User;
import com.betasolutions.projekt2023.model.Task;
import com.betasolutions.projekt2023.repository.Repository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
<<<<<<< HEAD
=======
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
>>>>>>> a34e5df8e2e658328a06c4efdc4df75e91fb7aea

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
<<<<<<< HEAD
            return "opgaveoversigt";
        }
        if (session.getAttribute("username") == "udvikler" &&
                session.getAttribute("passWord") == "udvikler"){
            return "opgaveoversigt";
        }
        return "Login";
    }

    @GetMapping("/login")
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

=======
            return "projektoversigt";
        }
        if (session.getAttribute("username") == "udvikler" &&
                session.getAttribute("passWord") == "udvikler"){
            return "projektoversigt";
        }
        return "Login";
>>>>>>> a34e5df8e2e658328a06c4efdc4df75e91fb7aea
    }

    @GetMapping("/login")
    public String login(HttpSession session){
        return "Login";
    }

    /*@GetMapping("/brugeroversigt")
    public String getbrugerOversigt(){
        return "brugeroversigt";
    }*/

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
            if (user.getAdmin()==true){return "projektoversigt";
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

<<<<<<< HEAD
    @GetMapping("/ny_bruger")
=======

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

    /*@GetMapping("/ny_bruger")
>>>>>>> a34e5df8e2e658328a06c4efdc4df75e91fb7aea
    public String newUser(){
        // TODO: Replace this with session data when log-in system has been implemented
        User loggedIn = new User(0, "test", "test", true);

        // Redirect to login-page when not admin
        if(!loggedIn.getAdmin()) {
            return "redirect:/login";
        }
        else {
            return "Opretnybruger";
        }
<<<<<<< HEAD
    }

    @PostMapping("/create-user")
    public String createNewUser(
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            @RequestParam("isAdmin") boolean isAdmin ){

        if(name.length() > 99 || name.length() == 0 || password.length() > 45 || password.length() == 0){
            //Name and Password is invalid
            return "redirect:/ny_bruger?invalidNameAndPassword=true";
        } else {
            repository.addUser(name, password, isAdmin);
            return "redirect:/ny_bruger?succes=true";
        }
    }

=======
    }*/

    @GetMapping("/opretnybruger")
    public String newUser(){
        return "Opretnybruger";
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

    /*@PostMapping("/deleteuser")
    public String deleteUser(@RequestBody String requestBody){

        //Få data

        //Execute delete_query fra repository
        repository.deleteUserById(getId);
        return "brugeroversigt";

    }*/

    //Ahmad's HomeController

     /*@GetMapping("/")
    public String index(Model model) {
        model.addAttribute("task", repository.getAll());
        return "login";
    }*/

    @GetMapping("/create")
    public String showCreate() {
        //vis opretnytprojekt-siden
        return "create";
    }

    @PostMapping("/create")
    public String opretOpgave(@RequestParam("task-name") String newName,
                              @RequestParam("task-startDate") int newStartDate,
                              @RequestParam("task-endDate") int newEndDato){
        //opret nyt projekt
        Task newTask = new Task();
        newTask.setName(newName);
        newTask.setStartDate(newStartDate);
        newTask.setEndDato(newEndDato);

        //gem nyt projekt
        repository.addTask(newTask);

        //tilbage til projektoversigten
        return "redirect:/";
    }

    /*@GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") int updateId, Model model) {
        // find project med name=updateName i databasen
        Task updateTask = repository.findTaskById(updateId);
        //tilføj project til viewmodel, så det kan bruges i thymeleaf
        model.addAttribute("task", updateTask);
        //fortæl spring hvilken HTML-side, der skal vises
        return "update";
    }*/

    @PostMapping("/update")
    public String updateTask(@RequestParam("task-name") String updateName,
                             @RequestParam("task-startDate") int updateStartDate,
                             @RequestParam("task-endDate") int updateEndDate,
                             @RequestParam("task-id") int updateId){
        //lav project ud fra parametre
        Task updateTask = new Task(updateId, updateName, updateStartDate, updateEndDate);

        //kald opdater i repository
        repository.updateTask(updateTask);
        //rediriger til oversigten
        return "redirect:/";
    }

    /*@GetMapping("/delete/{name}")
    public String deleteTask(@PathVariable("id") int id){
        //slet fra repository
        repository.deleteById(id);
        //returner til startsiden
        return "redirect:/";
    }*/

>>>>>>> a34e5df8e2e658328a06c4efdc4df75e91fb7aea
}
