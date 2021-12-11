package db.dao;

import db.SQLConnection;
import db.entities.Agency;
import db.entities.Part;
import db.statements.PartStatements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Clase que hereda SQLConnection e implementa la interface Dao especificando el uso de la clase Part para los
 * objetos
 * */

public class PartDao extends SQLConnection implements Dao<Part> {
    private Connection connection;

    public PartDao() {
        super();
        connection = getConnection();
        fillTable();
    }

    @Override
    public void fillTable() {
        /*
         * Llena la tabla de agencias en caso de que este vacia con datos por default
         * */
        if(tableIsEmpty(PartStatements.TABLE_NAME)){
            for (int i = 0; i < 3; i++) {
                save( new Part(
                    "Bujia", 3, 5.30, i + 1
                ));
                save( new Part(
                    "Retrovisor", 3, 50.30, i + 1
                ));
                save( new Part(
                    "Motor", 3, 5000.30, i + 1
                ));
            }
        }
    }

    @Override
    public void save(Part part) {
        // Guarda el objeto de tipo Part en la base de datos
        String sql = "INSERT INTO parts(part_name, unities,price,agency_id) VALUES(?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, part.getName());
            statement.setInt(2, part.getUnities());
            statement.setDouble(3, part.getPrice());
            statement.setInt(4, part.getAgencyId());

            statement.execute();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            listener.onSQLException("Error trying to create part");
        }
    }

    @Override
    public Part get(int id) {
        // Devuelve objeto de tipo Part con los datos de la columna que coincida con el id
        Part part = null;
        String sql = "SELECT * FROM parts WHERE id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int temp_id = resultSet.getInt(PartStatements.COLUMN_ID);
                String name = resultSet.getString(PartStatements.COLUMN_NAME);
                int unities = resultSet.getInt(PartStatements.COLUMN_UNITIES);
                double price = resultSet.getDouble(PartStatements.COLUMN_PRICE);
                int agencyId = resultSet.getInt(PartStatements.COLUMN_AGENCY);

                part = new Part(temp_id, name, unities, price, agencyId);
            }
            resultSet.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
            listener.onSQLException("Error trying to get part");
        }
        return part;
    }

    @Override
    public List<Part> getAll() {
        return null;
    }

    public List<Part> getAllByAgency(Agency agency){
        /*
        * Devuelve una lista de objetos de tipo Part que pertenezcan a la agencia (Agency) pasada como parametro
        * */
        String sql = "SELECT parts.id, parts.part_name, parts.unities, parts.price, parts.agency_id FROM parts INNER JOIN " +
            "agencies ON parts.agency_id = agencies.id WHERE agency_id = ?";
        List<Part> partList = new ArrayList<Part>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, agency.getId());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(PartStatements.COLUMN_ID);
                String name = resultSet.getString(PartStatements.COLUMN_NAME);
                int unities = resultSet.getInt(PartStatements.COLUMN_UNITIES);
                double price = resultSet.getDouble(PartStatements.COLUMN_PRICE);
                int agencyId = resultSet.getInt(PartStatements.COLUMN_AGENCY);
                Part part = new Part(id,name,unities,price,agencyId);
                partList.add(part);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            listener.onSQLException("Error trying to get all parts by agency");
        }
        return partList;
    }

    @Override
    public void update(Part part) {
        // Actualiza la cantidad de unidades del tipo de refacciones, usando el id del objeto para identificar la columna
        String sql = "UPDATE parts SEt unities = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, part.getUnities());
            statement.setInt(2, part.getId());

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            listener.onSQLException("Error trying to update part");
        }
    }

    @Override
    public void delete(int id) {

    }
}
