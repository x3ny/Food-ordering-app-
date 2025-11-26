package com.example.courseprifs.fxControllers;

import com.example.courseprifs.hibernateControl.GenericHibernate;
import com.example.courseprifs.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class UserForm {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public TextField phoneNumberField;
    @FXML
    public TextField addressField;
    @FXML
    public RadioButton userRadio;
    @FXML
    public RadioButton restaurantRadio;
    @FXML
    public RadioButton driverRadio;
    @FXML
    public RadioButton clientRadio;
    @FXML
    public ToggleGroup userType;
    @FXML
    public TextField vehicleInfoField;
    @FXML
    public TextField licenseNumberField;
    @FXML
    public DatePicker dateOfBirthField;
    @FXML
    public Pane driverPane;
    @FXML
    public TextField emailFieldRestaurant;
    @FXML
    public TextField descriptionField;
    @FXML
    public TextField restaurantInfoField;
    @FXML
    public TextField addressFieldBasicUser;
    @FXML
    public TextField phoneNumberFieldBasicUser;
    @FXML
    public Pane userPane;
    @FXML
    public Pane restaurantPane;
    @FXML
    public Button updateButton;
    @FXML
    public ComboBox<VehicleType> vehicleTypeBox;

    private EntityManagerFactory entityManagerFactory;
    private GenericHibernate genericHibernate;
    private User userForUpdate;
    private boolean isForUpdate;

    public void setData(EntityManagerFactory entityManagerFactory, User user, boolean isForUpdate) {
        this.entityManagerFactory = entityManagerFactory;
        this.genericHibernate = new GenericHibernate(entityManagerFactory);
        this.userForUpdate = user;
        this.isForUpdate = isForUpdate;
        fillUserDataForUpdate();
    }

    private void fillUserDataForUpdate() {
        if(userForUpdate != null && isForUpdate) {
            // Fill common fields for all user types
            usernameField.setText(userForUpdate.getLogin());
            passwordField.setText(userForUpdate.getPassword());
            nameField.setText(userForUpdate.getName());
            surnameField.setText(userForUpdate.getSurname());
            phoneNumberFieldBasicUser.setText(userForUpdate.getPhoneNumber());

            if(userForUpdate instanceof Driver) {
                Driver driver = (Driver) userForUpdate;
                userRadio.setSelected(false);
                driverRadio.setSelected(true);

                licenseNumberField.setText(driver.getLicence());
                addressFieldBasicUser.setText(driver.getAddress());
                vehicleInfoField.setText(driver.getVehicleInfo());

                if(driver.getVehicleType() != null) {
                    vehicleTypeBox.getItems().setAll(VehicleType.values());
                    vehicleTypeBox.setValue(driver.getVehicleType());
                }
            }
            else if(userForUpdate instanceof BasicUser) {
                BasicUser basicUser = (BasicUser) userForUpdate;
                clientRadio.setSelected(true);
                userRadio.setSelected(false);

                addressFieldBasicUser.setText(basicUser.getAddress());
            }
            else if(userForUpdate instanceof Restaurant) {
                Restaurant restaurant = (Restaurant) userForUpdate;
                restaurantRadio.setSelected(true);
                userRadio.setSelected(false);

                restaurantInfoField.setText(restaurant.getRestaurantInfo());
                descriptionField.setText(restaurant.getRestaurantInfo());
                emailFieldRestaurant.setText(restaurant.getEmail());
                phoneNumberFieldBasicUser.setText(restaurant.getPhoneNumber());
                addressFieldBasicUser.setText(restaurant.getAddress());
            }
            else if(userForUpdate instanceof User) {
                userRadio.setSelected(true);
            }

            // Refresh UI to show/hide appropriate fields based on selected user type
            disableFields();
        } else {
            updateButton.setVisible(false);
        }
    }

    public void createUser(ActionEvent actionEvent) {
        if(userRadio.isSelected()) {
            User user = new User(usernameField.getText(), passwordField.getText(), nameField.getText(),
                    surnameField.getText(), phoneNumberFieldBasicUser.getText(), true);
            genericHibernate.create(user);
        }
        else if(clientRadio.isSelected()) {
            BasicUser basicUser = new BasicUser(usernameField.getText(), passwordField.getText(),
                    surnameField.getText(), nameField.getText(),
                    phoneNumberFieldBasicUser.getText(), addressFieldBasicUser.getText());
            genericHibernate.create(basicUser);
        }
        else if(restaurantRadio.isSelected()) {
            Restaurant restaurant = new Restaurant(usernameField.getText(), passwordField.getText(),
                    surnameField.getText(), nameField.getText(),
                    restaurantInfoField.getText(), descriptionField.getText(),
                    emailFieldRestaurant.getText(), phoneNumberFieldBasicUser.getText(),
                    addressFieldBasicUser.getText());
            genericHibernate.create(restaurant);
        }
        else if(driverRadio.isSelected()) {
            Driver driver = new Driver(usernameField.getText(), passwordField.getText(), nameField.getText(),
                    surnameField.getText(), phoneNumberFieldBasicUser.getText(),
                    addressFieldBasicUser.getText(), licenseNumberField.getText(),
                    dateOfBirthField.getValue(), vehicleTypeBox.getValue(), vehicleInfoField.getText());
            genericHibernate.create(driver);
        }
    }

    @FXML
    public void initialize() {
        userRadio.setSelected(true);
        vehicleTypeBox = new ComboBox<>();
        disableFields();
    }

    public void disableFields() {
        if(userRadio.isSelected()) {
            userPane.setVisible(true);
            addressFieldBasicUser.setVisible(false);
            restaurantPane.setVisible(false);
            driverPane.setVisible(false);
        }
        else if(restaurantRadio.isSelected()) {
            restaurantPane.setVisible(true);
            userPane.setVisible(true);
            addressFieldBasicUser.setVisible(true);
            driverPane.setVisible(false);
        }
        else if(clientRadio.isSelected()) {
            userPane.setVisible(true);
            addressFieldBasicUser.setVisible(true);
            driverPane.setVisible(false);
            restaurantPane.setVisible(false);
        }
        else if(driverRadio.isSelected()) {
            driverPane.setVisible(true);
            userPane.setVisible(true);
            restaurantPane.setVisible(false);
            vehicleTypeBox.getItems().setAll(VehicleType.values());
        }
    }

    public void updateUser(ActionEvent actionEvent) {

        if(userForUpdate instanceof BasicUser) {

            BasicUser basicUser = (BasicUser) userForUpdate;
            basicUser.setLogin(usernameField.getText());
            basicUser.setPassword(passwordField.getText());
            basicUser.setName(nameField.getText());
            basicUser.setSurname(surnameField.getText());
            basicUser.setPhoneNumber(phoneNumberFieldBasicUser.getText());
            basicUser.setAddress(addressFieldBasicUser.getText());
            genericHibernate.update(basicUser);
        }

        else if(userForUpdate instanceof Driver){
            Driver driver = (Driver) userForUpdate;
            driver.setLogin(usernameField.getText());
            driver.setPassword(passwordField.getText());
            driver.setName(nameField.getText());
            driver.setSurname(surnameField.getText());
            driver.setPhoneNumber(phoneNumberFieldBasicUser.getText());
            driver.setLicence(licenseNumberField.getText());
            driver.setVehicleInfo(vehicleInfoField.getText());
            driver.setVehicleType(vehicleTypeBox.getValue());
            driver.setBDate(dateOfBirthField.getValue());
            genericHibernate.update(driver);

        }
        else if(userForUpdate instanceof Restaurant){
            Restaurant restaurant = (Restaurant) userForUpdate;
            restaurant.setLogin(usernameField.getText());
            restaurant.setPassword(passwordField.getText());
            restaurant.setName(nameField.getText());
            restaurant.setSurname(surnameField.getText());
            restaurant.setPhoneNumber(phoneNumberFieldBasicUser.getText());
            restaurant.setAddress(addressFieldBasicUser.getText());
            restaurant.setRestaurantName(restaurantInfoField.getText());
            restaurant.setRestaurantInfo(descriptionField.getText());
            restaurant.setEmail(emailFieldRestaurant.getText());
            genericHibernate.update(restaurant);

        }
        else{
            userForUpdate.setLogin(usernameField.getText());
            userForUpdate.setPassword(passwordField.getText());
            userForUpdate.setName(nameField.getText());
            userForUpdate.setSurname(surnameField.getText());
            userForUpdate.setPhoneNumber(phoneNumberFieldBasicUser.getText());
            genericHibernate.update(userForUpdate);
        }

    }
}
