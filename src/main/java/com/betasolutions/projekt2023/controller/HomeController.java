package com.betasolutions.projekt2023.controller;

import com.betasolutions.projekt2023.model.Task;
import com.betasolutions.projekt2023.repository.Repository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private Repository repository;

    public HomeController(Repository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("task", repository.getAll());
        return "login";
    }

    @GetMapping("/create")
    public String showCreate() {
        //vis opretnytprojekt-siden
        return "create";
    }

    @PostMapping("/create")
    public String opretOpgave(@RequestParam("task-name") String newName,
                               @RequestParam("task-startDate") int newStartDate){
        //opret nyt projekt
        Task newTask = new Task();
        newTask.setName(newName);
        newTask.setStartDate(newStartDate);

        //gem nyt projekt
        repository.addTask(newTask);

        //tilbage til projektoversigten
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") int updateId, Model model) {
        // find project med name=updateName i databasen
        Task updateTask = repository.findTaskById(updateId);
        //tilføj project til viewmodel, så det kan bruges i thymeleaf
        model.addAttribute("task", updateTask);
        //fortæl spring hvilken HTML-side, der skal vises
        return "update";
    }

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

    @GetMapping("/delete/{name}")
        public String deleteTask(@PathVariable("id") int id){
        //slet fra repository
        repository.deleteById(id);
        //returner til startsiden
        return "redirect:/";
    }
}
