/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.dht.hibernatedemov1;

import com.dht.pojo.Category;
import com.dht.repository.ProductRepository;
import com.dht.repository.StatsRepository;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class HibernateDemoV1 {

    public static void main(String[] args) throws ParseException {
        StatsRepository s = new StatsRepository();
        
        Map<String, String> params = new HashMap<>();
        params.put("quarter", "1");
        params.put("year", "2020");
        
        s.statsRevenue(params).forEach(o -> System.out.printf("%d - %s - %d\n", o[0], o[1], o[2]));
//        try (Session s = HibernateUtils.getFactory().openSession()) {
//            Query q = s.createNamedQuery("Category.findById", Category.class);
//            q.setParameter("id", 1);
//            List<Category> cates = q.getResultList();
//            cates.forEach(c -> System.out.println(c.getName()));
//        }
//        Map<String, String> params = new HashMap<>();
////        params.put("toPrice", "50000000");
//        params.put("cateId", "1");
//        
//        ProductRepository pre = new ProductRepository();
//        pre.getProducts(params).forEach(p -> System.out.printf("%d - %s - %.1f\n", p.getId(), p.getName(), p.getPrice()));
    }
}
