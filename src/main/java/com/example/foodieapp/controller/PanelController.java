package com.example.foodieapp.controller;

import com.example.foodieapp.model.Item;
import com.example.foodieapp.model.Order;
import com.example.foodieapp.model.OrderStatus;
import com.example.foodieapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class PanelController {

    private OrderRepository repository;

    @Autowired
    public PanelController(OrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("panel/zamowienia")
    public String getAllOrders(@RequestParam(required = false) OrderStatus status, Model model){
        List<Order> orders;
        if (status == null){
            orders = repository.findAll();
        }else {
            orders = repository.findAllByStatus(status);
        }
        model.addAttribute("orders", orders);
        return "panel";
    }

    @GetMapping("/panel/zamowienie/{id}")
    public String singleOrder(@PathVariable Long id, Model model) {
        Optional<Order> order = repository.findById(id);
        return order.map(o -> singleOrderPanel(o, model))
                .orElse("redirect:/");
    }

    @PostMapping("/panel/zamowienie/{id}")
    public String changeOrderStatus(@PathVariable Long id, Model model) {
        Optional<Order> order = repository.findById(id);
        order.ifPresent(o -> {
            o.setStatus(OrderStatus.nextStatus(o.getStatus()));
            repository.save(o);
        });
        return order.map(o -> singleOrderPanel(o, model))
                .orElse("redirect:/");
    }

    private String singleOrderPanel(Order order, Model model) {
        model.addAttribute("order", order);
        model.addAttribute("sum", order
                .getItems()
                .stream()
                .mapToDouble(Item::getPrice)
                .sum());
        return "status";
    }
}
