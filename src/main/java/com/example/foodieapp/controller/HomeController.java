package com.example.foodieapp.controller;

import com.example.foodieapp.model.Item;
import com.example.foodieapp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private ItemRepository repository;

    @Autowired
    public HomeController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home(Model model){
        List<Item> items = repository.findAll();
        model.addAttribute("items", items);
        return "home";
    }
}
