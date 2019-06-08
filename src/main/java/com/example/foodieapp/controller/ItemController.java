package com.example.foodieapp.controller;

import com.example.foodieapp.model.Item;
import com.example.foodieapp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ItemController {

    private ItemRepository repository;

    @Autowired
    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("danie/{name}")
    public String pizzaInfo(@PathVariable String name, Model model){
        Optional<Item> item = repository.findItemByName(name.replace('-',' '));
        item.ifPresent(it->model.addAttribute("item", it));
        return "item";
    }
}
