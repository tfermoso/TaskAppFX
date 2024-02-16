package com.ceica.taskappfx.controllerviews;

import com.ceica.taskappfx.controller.TaskController;
import com.ceica.taskappfx.models.Rol;
import com.ceica.taskappfx.models.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.List;

public class AdminController extends ControllerView  {

    @FXML
    protected TableView<User> tblUser;
    @FXML
    protected TableColumn<User,Integer> idColumn;
    @FXML
    protected TableColumn<User,String> userNameColumn;
    @FXML
    protected TableColumn<User,String> rolColumn;
    @FXML
    protected TextField txtUserName;
    @FXML
    protected PasswordField txtPassword;
    @FXML
    protected PasswordField txtrePassword;
    @FXML
    protected ComboBox<Rol> comboRol;
    @FXML
    protected Label lblMsg;
    @FXML
    protected Button btnAdd;
    @FXML
    protected Button btnUpdate;

    private ObservableList<User> observableList= FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        idColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getIduser()));
        userNameColumn.setCellValueFactory(cell->new SimpleStringProperty(cell.getValue().getUsername()));
        rolColumn.setCellValueFactory(cell->new SimpleStringProperty(cell.getValue().getRol().getDescription()));
        comboRol.setConverter(new StringConverter<Rol>() {
            @Override
            public String toString(Rol rol) {
                return rol.getDescription();
            }

            @Override
            public Rol fromString(String s) {
                return null;
            }
        });
        tblUser.setOnMouseClicked(e->{
            User user=tblUser.getSelectionModel().getSelectedItem();
            txtUserName.setText(user.getUsername());
            comboRol.setValue(user.getRol());
            btnAdd.setVisible(false);
            btnUpdate.setVisible(true);
        });
    }

    public AdminController() {


    }

    @Override
    public void cargaInicial() {
        List<User> userList=taskController.getAllUser();
        observableList.addAll(userList);
        tblUser.setItems(observableList);
        List<Rol> rolList=taskController.getAllRol();
        comboRol.getItems().addAll(rolList);
    }

    public void btnAddUser(ActionEvent actionEvent) {
        if(txtPassword.getText().equals(txtrePassword.getText())){
            taskController.createUser(txtUserName.getText(),txtPassword.getText(),comboRol.getSelectionModel().getSelectedItem().getIdrol());
            List<User> userList=taskController.getAllUser();
            observableList.clear();
            //tblUser.refresh();
            observableList.addAll(userList);
            tblUser.refresh();
        }else{
            lblMsg.setText("Password must be equals");
        }
    }

    public void btnUpdateUser(ActionEvent actionEvent) {
        if(txtPassword.getText().equals(txtrePassword.getText())){
            User user=tblUser.getSelectionModel().getSelectedItem();
            user.setPassword(txtPassword.getText());
            user.setRol(comboRol.getSelectionModel().getSelectedItem());
            taskController.updateUser(user);
            List<User> userList=taskController.getAllUser();
            observableList.clear();
            //tblUser.refresh();
            observableList.addAll(userList);
            tblUser.refresh();

        }else{
            lblMsg.setText("Passwords must to be equals");
        }
        //taskController.updateUser();
        btnAdd.setVisible(true);
        btnUpdate.setVisible(false);
    }
}
