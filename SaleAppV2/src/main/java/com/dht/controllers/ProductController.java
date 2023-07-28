/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.controllers;

import com.dht.pojo.Product;
import com.dht.service.ProductService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author admin
 */
@Controller
public class ProductController {
    @Autowired
    private ProductService proService;
    
    @GetMapping("/products")
    public String list(Model model) {
        model.addAttribute("product", new Product());
        return "products";
    }
    
    @GetMapping("/products/{id}")
    public String update(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("product", this.proService.getProductById(id));
        return "products";
    }
    
    @PostMapping("/products")
    public String add(@ModelAttribute(value = "product") @Valid Product p,
            BindingResult rs) {
        
        if (!rs.hasErrors())
            if (this.proService.addOrUpdateProduct(p) == true)
                return "redirect:/";
        
        return "products";
    }
}
