package db;

import java.sql.*;

import db.statements.AgencyStatements;
import db.statements.PartStatements;
import db.statements.ServiceStatements;
import listeners.SQLListener;

public class SQLConnection {

    private final String URL = "jdbc:mysql://localhost:3306/agenciakama";
    private final String USER = "root";
    private final String PASSWORD = "root";

    private Connection connection;
    protected SQLListener listener;

    public SQLConnection() {
        /*
        * Constructor de clase clase SQLConnection encargado de crear la conexion con la
        * base de datos
        * */
        testDriver();
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            fillDatabase();
        } catch (SQLSyntaxErrorException e) {
            e.printStackTrace();
            listener.onSQLException(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            listener.onSQLException(e.getMessage());
        }
    }

    public void setSQLListener(SQLListener listener) {
        this.listener = listener;
    }

    protected Connection getConnection() {
        /*
        * Devuelve la conexión actual creada
        * */
        return connection;
    }

    public void close() {
        /*
        * Cierra la conexion con la base de datos creada
        * */
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            listener.onSQLException("Error tratando de cerrar la conexion con la base de datos");
        }
    }

    protected boolean tableIsEmpty(String tableName){
        /*
        * Comprueba si la tabla pasada como parametro se encuentra vacia realizando un conteo de elementos de
        * la misma tabla dentro de la base de datos
        * */
        String sql = "SELECT COUNT(*) FROM " + tableName +" WHERE id = 1;";
        boolean isEmpty = true;
        try {
            int count = 0;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                count = resultSet.getInt("count(*)");
            }
            if(count > 0){
                isEmpty = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isEmpty;
    }

    private boolean tableExists(String tableName){
        /*
        * Comprueba la existencia de la tabla pasada como parametro en la base de datos por medio de la obtencion
        * de metadata de la misma
        * */
        boolean exists = false;
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName, null);
            if(resultSet.next()){
                exists = true;
                System.out.println("TABLE " + tableName + "ALREADY EXISTS");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            listener.onSQLException("Error trying to check if table exists");
        }
        return exists;
    }

    private void fillDatabase(){
        /*
        * Comprueba si cada una de las tablas necesarias existen en la base dedatos, en caso contrario,
        * creara la tabla que faltante
        * */
        try {
            Statement statement = connection.createStatement();

            if(!tableExists(ServiceStatements.TABLE_NAME)){
                System.out.println("CREATED TABLE " + ServiceStatements.TABLE_NAME);
                statement.execute(ServiceStatements.CREATE_TABLE);
            }
            if(!tableExists(AgencyStatements.TABLE_NAME)){
                System.out.println("CREATED TABLE " + AgencyStatements.TABLE_NAME);
                statement.execute(AgencyStatements.CREATE_TABLE);
            }
            if(!tableExists(PartStatements.TABLE_NAME)){
                System.out.println("CREATED TABLE " + PartStatements.TABLE_NAME);
                statement.execute(PartStatements.CREATE_TABLE);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            listener.onSQLException("Error trying to create tables in database");
        }
    }

    private void testDriver() {
        // Prueba el driver de MySQL JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            listener.onSQLException(e.getMessage());
        }

    }
}
