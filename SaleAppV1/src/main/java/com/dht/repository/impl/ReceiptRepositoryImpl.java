/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.repository.impl;

import com.dht.pojo.Cart;
import com.dht.pojo.OrderDetail;
import com.dht.pojo.SaleOrder;
import com.dht.pojo.User;
import com.dht.repository.ProductRepository;
import com.dht.repository.ReceiptRepository;
import com.dht.repository.UserReppository;
import java.util.Date;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository
public class ReceiptRepositoryImpl implements ReceiptRepository {

    @Autowired
    private UserReppository userRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean addReceipt(Map<String, Cart> carts) {

//        [{
//    "1": {
//        "id": 1,
//        "unitPrice": 100,
//        "quantity": 10
//    }
//}, {
//    "2": {
//        "id": 2,
//        "unitPrice": 100,
//        "quantity": 10
//    }
//}]
        Session s = this.factory.getObject().getCurrentSession();
        SaleOrder order = new SaleOrder();

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User u = this.userRepo.getUserByUsername(authentication.getName());
            order.setUserId(u);
            order.setCreatedDate(new Date());
            s.save(u);

            for (Cart c : carts.values()) {
                OrderDetail d = new OrderDetail();
                d.setProductId(this.productRepo.getProductById(Integer.parseInt(c.getId().toString())));
                d.setOrderId(order);
                d.setNum(c.getQuantity());
                d.setUnitPrice(c.getUnitPrice());

                s.save(d);
            }

            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }

    }

}
