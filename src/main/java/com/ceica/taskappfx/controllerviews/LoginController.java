package com.ceica.taskappfx.controllerviews;

import com.ceica.taskappfx.TaskApplication;
import com.ceica.taskappfx.controller.TaskController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
            String view="";
            String titleWindow;
            if(taskController.isAdmin()){
                //Mostrar vista administrador
                view="admin-view.fxml";
                titleWindow="Admin";
            }else{
                //Mostrar vista usuario
                view="user-view.fxml";
                titleWindow="User";
            }
            FXMLLoader fxmlLoader=new FXMLLoader(TaskApplication.class.getResource(view));
            try {
                Parent root=fxmlLoader.load();
                IControllerView controller =fxmlLoader.getController();
                controller.setTaskController(taskController);
                Scene scene=new Scene(root);
                Stage stage=new Stage();
                stage.setTitle(titleWindow);
                stage.setScene(scene);
                stage.show();
                Node source=(Node) actionEvent.getSource();
                Stage stage1=(Stage) source.getScene().getWindow();
                stage1.close();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            lblMessage.setText("Incorrect User or Password");
        }
    }
}
