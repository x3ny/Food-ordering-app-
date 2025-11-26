package com.example.courseprifs.utils;

import javafx.scene.control.Alert;

public class FxUtils {
    public static void generateAlert(Alert.AlertType alertType, String title, String header, String content){
        //cia noriu naudoti alertus
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
