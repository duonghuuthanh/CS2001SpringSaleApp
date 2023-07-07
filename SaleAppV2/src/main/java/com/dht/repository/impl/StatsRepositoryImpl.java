/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.repository.impl;

import com.dht.pojo.Category;
import com.dht.pojo.OrderDetail;
import com.dht.pojo.Product;
import com.dht.pojo.SaleOrder;
import com.dht.repository.StatsRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private SimpleDateFormat f;

    public List<Object[]> countProductsByCate() {
        Session s = this.factory.getObject().getCurrentSession();
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

    public List<Object[]> statsRevenue(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root rProduct = q.from(Product.class);
        Root rOrderDetails = q.from(OrderDetail.class);
        Root rOrder = q.from(SaleOrder.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rOrderDetails.get("productId"), rProduct.get("id")));
        predicates.add(b.equal(rOrderDetails.get("orderId"), rOrder.get("id")));

        String fd = params.get("fromDate");
        if (fd != null && !fd.isEmpty()) {
            try {
                predicates.add(b.greaterThanOrEqualTo(rOrder.get("createdDate"), f.parse(fd)));
            } catch (ParseException ex) {
                Logger.getLogger(StatsRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String td = params.get("toDate");
        if (td != null && !td.isEmpty()) {
            try {
                predicates.add(b.lessThanOrEqualTo(rOrder.get("createdDate"), f.parse(td)));
            } catch (ParseException ex) {
                Logger.getLogger(StatsRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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
