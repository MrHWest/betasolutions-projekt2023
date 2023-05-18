package com.betasolutions.projekt2023.controller;

import com.betasolutions.projekt2023.model.Task;
import com.betasolutions.projekt2023.repository.Repository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @GetMapping("/")
    public String getAllTasks(Model model, HttpSession session){
        //Hent opgaver fra session eller opret en ny liste
        List<Task> tasks = getTasksFromSession(session);

        //tilføj opgaver til modellen, så de kan vises i listen
        model.addAttribute("tasks", tasks);

        //returner navnet på listen
        return "tasks";
    }
    @PostMapping("create")
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
        return "redirect:/tasks/";
    }
    @PostMapping("update/{taskId}")
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
        return "redirect./tasks/";
    }
    @PostMapping("/delete/{taskId}")
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
        return "redirect:/tasks/";
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