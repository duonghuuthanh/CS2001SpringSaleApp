/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dht.pojo.Product;
import com.dht.repository.ProductRepository;
import com.dht.service.ProductService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Product> getProducts(Map<String, String> params) {
        return this.productRepo.getProducts(params);
    }

    @Override
    public Long countProduct() {
        return this.productRepo.countProduct();
    }

    @Override
    public boolean addOrUpdateProduct(Product p) {
       // p.setImage("https://res.cloudinary.com/dxxwcby8l/image/upload/v1647248652/dkeolz3ghc0eino87iec.jpg");
        
        if (!p.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(p.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                p.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return this.productRepo.addOrUpdateProduct(p);
    }

    @Override
    public Product getProductById(int id) {
        return this.productRepo.getProductById(id);
    }

    @Override
    public boolean deleteProduct(int id) {
        return this.productRepo.deleteProduct(id);
    }
    
}
