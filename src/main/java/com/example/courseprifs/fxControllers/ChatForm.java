package com.example.courseprifs.fxControllers;

import com.example.courseprifs.hibernateControl.CustomHibernate;
import com.example.courseprifs.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class ChatForm {
    public ListView messageList;
    public TextArea messageTextArea;
    private EntityManagerFactory entityManagerFactory;
    private CustomHibernate customHibernate;
    private User currentUser;
    private FoodOrder currentFoodOrder;

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser, FoodOrder currentFoodOrder) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = currentUser;
        this.currentFoodOrder = currentFoodOrder;
        this.customHibernate = new CustomHibernate(entityManagerFactory);
    }


    public void sendMessage(ActionEvent actionEvent) {
        if(currentFoodOrder.getChat() == null){
          Chat chat = new Chat("Chat no " + currentFoodOrder.getTitle(), currentFoodOrder);
          customHibernate.create(chat);
        }
        FoodOrder foodOrder = customHibernate.getEntityById(FoodOrder.class ,currentFoodOrder.getId());
        Review message = new Review(messageTextArea.getText(), (BasicUser) currentUser,foodOrder.getChat());
        customHibernate.create(message);

    }
}
