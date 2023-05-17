package com.betasolutions.projekt2023.controller;

import com.betasolutions.projekt2023.model.Project;
import com.betasolutions.projekt2023.model.User;
import com.betasolutions.projekt2023.repository.Repository;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.ldap.Control;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
            return "projektoversigt";
        }
        if (session.getAttribute("username") == "udvikler" &&
                session.getAttribute("passWord") == "udvikler"){
            return "projektoversigt";
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
            return "projektoversigt";
        }

        return "Login";

    }
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

    @GetMapping("/ny_bruger")
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

}
