package com.example.courseprifs.fxControllers;

import com.example.courseprifs.HelloApplication;
import com.example.courseprifs.hibernateControl.CustomHibernate;
import com.example.courseprifs.hibernateControl.GenericHibernate;
import com.example.courseprifs.model.*;
import com.example.courseprifs.utils.FxUtils;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class mainForm {
    @FXML
    public Tab managementTab;
    @FXML
    public Tab FoodTab;
    @FXML
    public Tab userTab;
    @FXML
    public Tab alterativeTab;
    @FXML
    public ListView<User> userListField;
    @FXML
    public TabPane tabsPane;
    @FXML
    public Button UpdateButton;
    @FXML
    public ListView<FoodOrder> ordersList;
    @FXML
    public ComboBox<BasicUser> clientList;
    @FXML
    public TextField titleField;
    @FXML
    public TextField priceField;
    @FXML
    public ComboBox<Restaurant> restaurantList;
    @FXML
    public ComboBox<OrderStatus> orderStatusList;
    @FXML
    public ComboBox<OrderStatus> filterStatus;
    @FXML
    public ComboBox<BasicUser> filterClients;
    @FXML
    public DatePicker filterFrom;
    @FXML
    public DatePicker FilterTo;
    @FXML
    public ListView<Cuisine> cuisineList;
    @FXML
    public TextField cuisineNameField;
    @FXML
    public ListView<Restaurant> restaurantListFood;
    @FXML
    public ListView<Cuisine> foodList;
    @FXML
    public TextField cuisinePriceField;
    @FXML
    public CheckBox isSpicy;
    @FXML
    public CheckBox isVegan;
    @FXML
    public TextArea ingredientsCuisineField;

    private EntityManagerFactory entityManagerFactory;
    private CustomHibernate customHibernate;
    private GenericHibernate genericHibernate;
    private User currentUser;

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        this.customHibernate = new CustomHibernate(entityManagerFactory);
        setUserFormVisibility();
        reloadTableData();
    }

    private void setUserFormVisibility() {
        if(currentUser instanceof User) {
            //turbut nieko nedarom, gal kazka custom

        }
        else if(currentUser instanceof Restaurant){
            tabsPane.getTabs().remove(alterativeTab); //Man net nesugeneruos sito tabo
        }
    }

    public void reloadTableData() {
        if(alterativeTab.isSelected()){
            List<User> userList = customHibernate.getAllRecords(User.class);
            userListField.getItems().setAll(userList);

        }else if(managementTab.isSelected()){ //cia yra orders management
            clearAllOrderFields();
            List<FoodOrder> foodOrders = getFoodOrders();
            ordersList.getItems().addAll(foodOrders);
            clientList.getItems().addAll(customHibernate.getAllRecords(BasicUser.class));
            restaurantList.getItems().addAll(customHibernate.getAllRecords(Restaurant.class));
            orderStatusList.getItems().setAll(OrderStatus.values());
            cuisineList.getItems().addAll(customHibernate.getAllRecords(Cuisine.class));

        }
        else if(FoodTab.isSelected()){
            clearAllCuisineFields();
            restaurantListFood.getItems().addAll(customHibernate.getAllRecords(Restaurant.class));
        }
    }

    private void clearAllOrderFields(){
        ordersList.getItems().clear();
        clientList.getItems().clear();
        restaurantList.getItems().clear();
        titleField.clear();
        priceField.clear();
    }

    private void clearAllCuisineFields(){
        restaurantListFood.getItems().clear();
        cuisineList.getItems().clear();
        cuisineNameField.clear();
        cuisinePriceField.clear();
        ingredientsCuisineField.clear();
        isSpicy.setSelected(false);
        isVegan.setSelected(false);
    }


    //<editor-fold desc="User Management functions">

    public void addUser(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("userForm.fxml"));
        Parent parent = fxmlLoader.load();

        User selectedUser = userListField.getSelectionModel().getSelectedItem();

        UserForm userForm = fxmlLoader.getController();
        userForm.setData(entityManagerFactory, null, false);

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void loadUser(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("userForm.fxml"));
        Parent parent = fxmlLoader.load();

        User selectedUser = userListField.getSelectionModel().getSelectedItem();

        UserForm userForm = fxmlLoader.getController();
        userForm.setData(entityManagerFactory, selectedUser, true);

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void deleteUser(ActionEvent actionEvent) {
        User selectedUser = userListField.getSelectionModel().getSelectedItem();
        customHibernate.delete(User.class,selectedUser.getId());
        userListField.getItems().clear();
        reloadTableData();

    }

    //</editor-fold>


    //<editor-fold desc="Orders management functions">

    private List<FoodOrder> getFoodOrders(){
        if(currentUser instanceof Restaurant){
            return customHibernate.getRestaurantOrders((Restaurant)currentUser);
        }else{
            return customHibernate.getAllRecords(FoodOrder.class);
        }
    }

    public static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public void createOrder() {
        if(isNumber(priceField.getText())) {
            FoodOrder foodOrder = new FoodOrder(clientList.getValue(),titleField.getText(), Double.parseDouble(priceField.getText()),foodList.getSelectionModel().getSelectedItems(), restaurantList.getValue());
            customHibernate.create(foodOrder);
            ordersList.getItems().clear();
            ordersList.getItems().addAll(customHibernate.getAllRecords(FoodOrder.class));
            fillOrderList();
        }
        else  FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Price field error","Price is wrong", "Price field must be a valid number");
    }

    public void updateOrder() {
        FoodOrder foodOrder = ordersList.getSelectionModel().getSelectedItem();
        foodOrder.setRestaurant(restaurantList.getSelectionModel().getSelectedItem());
        foodOrder.setTitle(titleField.getText());
        foodOrder.setPrice(Double.parseDouble(priceField.getText()));
        foodOrder.setOrderStatus(orderStatusList.getValue());
        foodOrder.setCustomer(clientList.getSelectionModel().getSelectedItem());
        customHibernate.update(foodOrder);
        fillOrderList();

    }

    public void DeleteOrder() {
        FoodOrder selectedOrder = ordersList.getSelectionModel().getSelectedItem();
        customHibernate.delete(FoodOrder.class,selectedOrder.getId());
        fillOrderList();

    }

    private void fillOrderList(){
        ordersList.getItems().clear();
        ordersList.getItems().addAll(customHibernate.getAllRecords(FoodOrder.class));
    }

    public void loadOrderInfo() {
        FoodOrder selectedOrder = ordersList.getSelectionModel().getSelectedItem();
        clientList.getItems().stream()
                .filter(c->c.getId() == selectedOrder.getCustomer().getId())
                .findFirst()
                .ifPresent(u-> clientList.getSelectionModel().select(u));
        titleField.setText(selectedOrder.getTitle());
        priceField.setText(String.valueOf(selectedOrder.getPrice()));
        restaurantList.getItems().stream()
                .filter(r->r.getId() == selectedOrder.getRestaurant().getId())
                .findFirst()
                .ifPresent(u -> restaurantList.getSelectionModel().select(u));
        orderStatusList.getSelectionModel().select(selectedOrder.getOrderStatus());
        disableFooDOrderFields();
    }

    private void disableFooDOrderFields(){
        if(orderStatusList.getSelectionModel().getSelectedItem() == OrderStatus.COMPLETED){
            clientList.setDisable(true);
            priceField.setDisable(true);
            restaurantList.setDisable(true);
            titleField.setDisable(true);
        }
    }

    public void filterOrders(ActionEvent actionEvent) {

    }

    //</editor-fold>

    //<editor-fold desc="Cuisine management tab functions">
    public void createNewMenu() {
        Cuisine cuisine = new Cuisine(cuisineNameField.getText(),Double.parseDouble(cuisinePriceField.getText()),isSpicy.isSelected()
                ,isVegan.isSelected(),ingredientsCuisineField.getText(),restaurantListFood.getSelectionModel().getSelectedItem());
        customHibernate.create(cuisine);

    }

    public void UpdateNewItem(ActionEvent actionEvent) {
    }

    public void loadRestaurantMenu() {
        cuisineList.getItems().addAll(customHibernate.getRestaurantCuisine(restaurantList.getSelectionModel().getSelectedItem()));
    }
    //</editor-fold>
}
