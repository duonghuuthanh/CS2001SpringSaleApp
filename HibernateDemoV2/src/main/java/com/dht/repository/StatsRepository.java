/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.repository;

import com.dht.hibernatedemov2.HibernateUtils;
import com.dht.pojo.Category;
import com.dht.pojo.OrderDetail;
import com.dht.pojo.Product;
import com.dht.pojo.SaleOrder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class StatsRepository {
    public static final SimpleDateFormat F = new SimpleDateFormat("yyyy-MM-dd");
    
    public List<Object[]> countProductsByCate() {
        try (Session s = HibernateUtils.getFactory().openSession()) {
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
            
            Root rProduct = q.from(Product.class);
            Root rCate = q.from(Category.class);
            
            q.where(b.equal(rProduct.get("categoryId"), rCate.get("id")));
            
            q.multiselect(rCate.get("id"), rCate.get("name"), b.count(rProduct.get("id")));
            q.groupBy(rCate.get("id"));
            
            Query query = s.createQuery(q);
            return query.getResultList();
        }
    }
    
    public List<Object[]> statsRevenue(Map<String, String> params) throws ParseException {
         try (Session s = HibernateUtils.getFactory().openSession()) {
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
            
            Root rProduct = q.from(Product.class);
            Root rOrderDetails = q.from(OrderDetail.class);
            Root rOrder = q.from(SaleOrder.class);
            
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(b.equal(rOrderDetails.get("productId"), rProduct.get("id")));
            predicates.add(b.equal(rOrderDetails.get("orderId"), rOrder.get("id")));
            
            String fd = params.get("fromDate");
            if (fd != null && !fd.isEmpty())
                predicates.add(b.greaterThanOrEqualTo(rOrder.get("createdDate"), F.parse(fd)));
            
            String td = params.get("toDate");
            if (td != null && !td.isEmpty())
                predicates.add(b.lessThanOrEqualTo(rOrder.get("createdDate"), F.parse(td)));
            
            String quarter = params.get("quarter");
            if (quarter != null && !quarter.isEmpty()) {
                String year = params.get("year");
                if (year != null && !year.isEmpty()) {
                    predicates.add(b.equal(b.function("YEAR", Integer.class, rOrder.get("createdDate")), Integer.parseInt(year)));
                    predicates.add(b.equal(b.function("QUARTER", Integer.class, rOrder.get("createdDate")), Integer.parseInt(quarter)));
                }
            }
            
            q.where(predicates.toArray(Predicate[]::new));
            
            q.multiselect(rProduct.get("id"), rProduct.get("name"), b.prod(rOrderDetails.get("unitPrice"), rOrderDetails.get("num")));
            q.groupBy(rProduct.get("id"));
            
            Query query = s.createQuery(q);
            return query.getResultList();
         }
    }
}
