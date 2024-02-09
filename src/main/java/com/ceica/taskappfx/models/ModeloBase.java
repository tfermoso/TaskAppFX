package com.ceica.taskappfx.models;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public abstract class ModeloBase {
    private static final String CONFIG_FILE = "config.properties";
    protected static String URL;
    protected static String USUARIO;
    protected static String PASSWORD;


    static {
        cargarConfiguracion();
    }

    private static void cargarConfiguracion() {
        Properties propiedades = new Properties();
        try (FileInputStream entrada = new FileInputStream(CONFIG_FILE)) {
            propiedades.load(entrada);
            URL = propiedades.getProperty("db.url");
            USUARIO = propiedades.getProperty("db.usuario");
            PASSWORD = propiedades.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método abstracto para obtener el nombre de la tabla
    protected abstract String getNombreTabla();

    // Métodos para CRUD

    public boolean insertar(String sql, Object... parametros) {

        sql = "insert into " + getNombreTabla() + " " + sql;
        return ejecutarQuery(sql, parametros);

    }

    public boolean actualizar(String sql, Object... parametros) {
        sql = "update " + getNombreTabla() + " set " + sql;
        return ejecutarQuery(sql, parametros);
    }

    public boolean borrar(String sql, Object... parametros) {
        sql = "delete from " + getNombreTabla() + " where " + sql;
        return ejecutarQuery(sql, parametros);
    }
    //Método que devuelve la conexion a la bbdd
    public Connection getConnection(){
        try {
            Connection conexion=DriverManager.getConnection(URL,USUARIO,PASSWORD);
            return conexion;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Método genérico para ejecutar consultas SQL
    private boolean ejecutarQuery(String sql, Object... parametros) {
        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            // Establecer los valores de los parámetros
            for (int i = 0; i < parametros.length; i++) {
                preparedStatement.setObject(i + 1, parametros[i]);
            }

            // Ejecutar la consulta
            if(preparedStatement.executeUpdate()>0){
                return true;
            }else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

