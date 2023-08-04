/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.pojo;

import lombok.Data;

/**
 *
 * @author admin
 */
@Data
public class Cart {
    private int id;
    private String name;
    private Long unitPrice;
    private int quantity;
}
