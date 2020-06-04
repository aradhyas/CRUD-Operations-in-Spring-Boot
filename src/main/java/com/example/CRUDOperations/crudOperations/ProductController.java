package com.example.CRUDOperations.crudOperations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository repo;
    @GetMapping("/register")
    public String newDevice(Product product)
    {
        return "add-user";
    }

    @PostMapping("/addUser")
    public String addProduct(@Validated Product product, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "add-user";
        }

        repo.save(product);
        model.addAttribute("product", repo.findAll());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showForm(@PathVariable("id") long id, Model model)
    {
        Product product = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID:" + id));

        model.addAttribute("product", product);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateForm(@PathVariable("id") long id, @Validated Product product, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            product.setId(id);
            return "update-user";
        }

        repo.save(product);
        model.addAttribute("products", repo.findAll());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id, Model model) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        repo.delete(product);
        model.addAttribute("users", repo.findAll());
        return "index";
    }

}
