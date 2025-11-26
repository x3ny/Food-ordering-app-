package com.example.courseprifs.hibernateControl;

import com.example.courseprifs.model.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomHibernate extends GenericHibernate{
    public CustomHibernate(EntityManagerFactory entityManagerFactory){
        super(entityManagerFactory);
    }

    public User getUserByCredentials(String username, String password){
        User user = null;
         try{
             entityManager = entityManagerFactory.createEntityManager();
             CriteriaBuilder cb = entityManager.getCriteriaBuilder();
             CriteriaQuery<User> query = cb.createQuery(User.class);
             Root<User> root = query.from(User.class);

             query.select(root).where(cb.and(
                     cb.equal(root.get("login"),username),
                     cb.equal(root.get("password"), password)
             ));
             Query q = entityManager.createQuery(query);
             user = (User) q.getSingleResult();
         }catch(Exception e){
                //Veliau cia dar
         }
        return user;
    }

    public List<FoodOrder> getRestaurantOrders(Restaurant restaurant){
        List<FoodOrder> foodOrders = new ArrayList<>();
        try{
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<FoodOrder> query = cb.createQuery(FoodOrder.class);
            Root<FoodOrder> root = query.from(FoodOrder.class);

            query.select(root).where(cb.equal(root.get("restaurant"),restaurant));

            Query q = entityManager.createQuery(query);
            foodOrders = q.getResultList();
        }catch(Exception e){
            //Veliau cia dar
        }
        return foodOrders;
    }

    public List<FoodOrder> getFilteredOrders(OrderStatus orderStatus, BasicUser client, LocalDate start, LocalDate end, Restaurant restaurant){
        List<FoodOrder> foodOrders = new ArrayList<>();
        try{
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<FoodOrder> query = cb.createQuery(FoodOrder.class);
            Root<FoodOrder> root = query.from(FoodOrder.class);

            if(restaurant != null) {
                query.select(root).where(cb.equal(root.get("restaurant"), restaurant));
                Query q = entityManager.createQuery(query);
                foodOrders = q.getResultList();
            }
            else{
                query.select(root).where(cb.equal(root.get("restaurant"), restaurant));
            }
            }catch(Exception e){
                //Veliau cia dar
            }
            return foodOrders;

    }

    public List<Cuisine> getRestaurantCuisine(Restaurant restaurant){
        List<Cuisine> menu = new ArrayList<>();
        try{
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Cuisine> query = cb.createQuery(Cuisine.class);
            Root<Cuisine> root = query.from(Cuisine.class);

            query.select(root).where(cb.equal(root.get("restaurant"),restaurant));

            Query q = entityManager.createQuery(query);
            menu = q.getResultList();
        }catch(Exception e){
            //Veliau cia dar
        }
        return menu;
    }
}
