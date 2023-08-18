/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.repository;

import com.dht.pojo.User;
import java.util.Map;

/**
 *
 * @author admin
 */
public interface UserReppository {
    User getUserByUsername(String username);
    boolean authUser(String username, String password);
    User addUser(User user);
}
