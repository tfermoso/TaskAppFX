package com.ceica.taskappfx.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User extends ModeloBase{
    private int iduser;
    private String username;
    private String password;
    private Rol rol;

    public User() {
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    protected String getNombreTabla() {
        return "user";
    }


    @Override
    public String toString() {
        return "User{" +
                "iduser=" + iduser +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rol=" + rol +
                '}';
    }

    public User login(String username, String password) {
        User user=new User();
        Connection conn=user.getConnection();
        String sql="select iduser,username,rol.idrol,description from " +
                "user left join rol on user.idrol=rol.idrol " +
                "where username=? and password=?";
        try {
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,username);
            pst.setString(2,password);
            ResultSet resultSet=pst.executeQuery();
            if(resultSet.next()){
                user.iduser=resultSet.getInt("iduser");
                user.username=resultSet.getString("username");
                Rol rol=new Rol();
                rol.setIdrol(resultSet.getInt("idrol"));
                rol.setDescription(resultSet.getString("description"));
                user.rol=rol;
                return user;
            }else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public List<User> getAll() {
        List<User> userList=new ArrayList<>();
        User user=new User();
        Connection conn=user.getConnection();
        String consulta="select iduser,username,user.idrol,description " +
                "from user inner join rol on user.idrol=rol.idrol";
        try {
            Statement stm=conn.createStatement();
            ResultSet resultSet=stm.executeQuery(consulta);
            while (resultSet.next()){
                User user1=new User();
                user1.setIduser(resultSet.getInt("iduser"));
                user1.setUsername(resultSet.getString("username"));
                Rol rol1=new Rol();
                rol1.setIdrol(resultSet.getInt("user.idrol"));
                rol1.setDescription(resultSet.getString("description"));
                user1.setRol(rol1);
                userList.add(user1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
}
