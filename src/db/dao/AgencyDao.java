package db.dao;

import db.SQLConnection;
import db.entities.Agency;
import db.statements.AgencyStatements;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
* Clase que hereda SQLConnection e implementa la interface Dao especificando el uso de la clase Agency para los
* objetos
* */

public class AgencyDao extends SQLConnection implements Dao<Agency> {
    private Connection connection;

    public AgencyDao() {
        super();
        connection = getConnection();
        fillTable();
    }

    @Override
    public void fillTable() {
        /*
        * Llena la tabla de agencias en caso de que este vacia con datos por default
        * */
        if(tableIsEmpty(AgencyStatements.TABLE_NAME)){
            save(new Agency("Nissan"));
            save(new Agency("Miaussan"));
            save(new Agency("Chevrolet"));
        }
    }

    @Override
    public void save(Agency agency) {
        // Guarda el objeto de tipo Agency en la base de datos
        String sql = "INSERT INTO agencies (agency_name) VALUES (?);";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, agency.getName());

            statement.execute();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            listener.onSQLException("Error trying to create agency");
        }
    }

    @Override
    public Agency get(int id) {
        // Devuelve objeto de tipo Agency con los datos de la columna que coincida con el id
        Agency agency = null;
        String sql = "SELECT * FROM agencies WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int temp_id = resultSet.getInt(AgencyStatements.COLUMN_ID);
                String name = resultSet.getString(AgencyStatements.COLUMN_NAME);

                agency = new Agency(temp_id, name);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            listener.onSQLException("Error trying to get agency");
        }
        return agency;
    }

    public List<Agency> getLikeName(String name){
        /*
        * Devuelve una lista de objetos de Agency con los datos de las columnas donde su nombre contenga el nombre
        * pasado por parametro
        * */
        String sql = "SELECT * FROM agencies WHERE UPPER(agency_name) LIKE ?;";
        List<Agency> agencyList = new ArrayList<Agency>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name.toUpperCase() + "%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int temp_id = resultSet.getInt(AgencyStatements.COLUMN_ID);
                String temp_name = resultSet.getString(AgencyStatements.COLUMN_NAME);
                Agency agency = new Agency(temp_id, temp_name);
                agencyList.add(agency);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            listener.onSQLException("Error trying to get agency by name");
        }
        return agencyList;
    }

    @Override
    public List<Agency> getAll() {
        // Devuelve una lista de objetos de Agency con los datos de las columnas de toda la tabla
        String sql = "SELECT * FROM agencies;";
        List<Agency> agencyList = new ArrayList<Agency>();
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int temp_id = resultSet.getInt(AgencyStatements.COLUMN_ID);
                String name = resultSet.getString(AgencyStatements.COLUMN_NAME);
                Agency agency = new Agency(temp_id, name);
                agencyList.add(agency);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            listener.onSQLException("Error trying to get agency by name");
        }
        return agencyList;
    }

    @Override
    public void update(Agency agency) {
    }

    @Override
    public void delete(int id) {

    }
}
