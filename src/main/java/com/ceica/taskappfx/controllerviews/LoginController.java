package com.ceica.taskappfx.controllerviews;

import com.ceica.taskappfx.controller.TaskController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    protected TextField txtUsername;
    @FXML
    protected PasswordField txtPassword;
    @FXML
    protected Label lblMessage;

    public TaskController taskController=new TaskController();

    public void btnLogin(ActionEvent actionEvent) {
        if(taskController.login(txtUsername.getText(),txtPassword.getText())){
            System.out.println("TODO OK");
        }else {
            lblMessage.setText("Incorrect User or Password");
        }
    }
}
