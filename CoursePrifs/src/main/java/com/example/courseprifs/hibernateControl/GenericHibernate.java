/**
 *  Helper klase, skirta darbui su duomenu baze t.y perduosiu objektus, atidarysiu sesija, sesijos metu kazka atliksiu, baigsiu darba
 *  Cio dar irgi bus aprasytos CRUD : issaugot, updateint, trinti ir perziureti duomenis is duomenu bazes
 */

package com.example.courseprifs.hibernateControl;


import com.example.courseprifs.model.User;
import com.example.courseprifs.utils.FxUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

public class GenericHibernate {
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    public GenericHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;

    }

    public <T> void create(T entity){
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entity); //INSERT
            entityManager.getTransaction().commit();
        }catch (Exception e){
            //Klaidos atveju as panaudosiu Alertus
        }finally {

            if(entityManager != null) entityManager.close();
        }
    }

    public <T> void update(T entity){
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(entity); //UPDATE
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Update","User update error", "Empty fields to update");
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    //Gali buti blogai su detached entity
    public <T> void delete(Class<T> entityClass, int id) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            T entity = entityManager.find(entityClass, id);
            entityManager.remove(entity); //DELETE
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            FxUtils.generateAlert(Alert.AlertType.ERROR, "During DELETE", "Delete failed", e.getMessage());
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }



    public <T> T getEntityById(Class<T> entityClass, int id) {
        T entity = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entity = entityManager.find(entityClass, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            //Noriu alerto
        } finally {
            if (entityManager != null) entityManager.close();
        }
        return entity;
    }

    public <T> List<T> getAllRecords(Class<T> entityClass) {
        List<T> list = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(entityClass));
            Query q = entityManager.createQuery(query);
            list = q.getResultList();
        } catch (Exception e) {
            //alerto reiks
        }
        return list;
    }
}
