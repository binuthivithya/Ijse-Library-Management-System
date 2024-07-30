package edu.ijse.UIController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashboardController {
    public AnchorPane context;
    public Label lblDate;
    public Label lblTime;

    public void initialize() {
        lblDate.setText(LocalDate.now().toString());
        lblTime.setText(java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        setUI("LoginForm");
    }

    public void categoryOnAction(ActionEvent actionEvent) throws IOException {
        setUI("CategoryForm");
    }

    public void bookOnAction(ActionEvent actionEvent) throws IOException {
        setUI("BookForm");
    }

    public void memberOnAction(ActionEvent actionEvent) throws IOException {
        setUI("MemberForm");
    }

    public void borrowingsOnAction(ActionEvent actionEvent) throws IOException {
        setUI("BorrowingForm");
    }

    public void returnOnAction(ActionEvent actionEvent) throws IOException {
        setUI("ReturnForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.show();
        stage.centerOnScreen();
    }
}
