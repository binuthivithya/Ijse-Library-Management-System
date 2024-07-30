package edu.ijse.UIController;

import edu.ijse.dto.UserDto;
import edu.ijse.service.custom.impl.UserServiceImpl;
import edu.ijse.util.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public PasswordField txtPassword;
    public Hyperlink txtforgotpassword;

    private final UserServiceImpl userService = new UserServiceImpl();

    public void loginOnAction(ActionEvent actionEvent) throws IOException {

        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        try{
            UserDto user = userService.getUser(email);
            if (user != null) {
                if (new PasswordManager().checkPassword(password, user.getPassword())) {
                    setUI("Dashboard");
                } else {
                    new Alert(Alert.AlertType.ERROR, "Email or Password Incorrect...!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "User Not Found...!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUI("SignUpForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.show();
        stage.centerOnScreen();
    }
}
