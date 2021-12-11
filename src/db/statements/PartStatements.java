package db.statements;

/*
 * Clase con todos los nombres de columnas para ser usado en peticiones y la sentencia de SQL para crear la tabla
 * */

public class PartStatements {
    public static final String TABLE_NAME = "parts";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "part_name";
    public static final String COLUMN_UNITIES = "unities";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_AGENCY = "agency_id";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + COLUMN_ID +
        " INTEGER AUTO_INCREMENT PRIMARY KEY UNIQUE, " + COLUMN_NAME + " NVARCHAR(30) NOT NULL, " + COLUMN_UNITIES +
        " INTEGER(10) NOT NULL, " + COLUMN_PRICE + " DOUBLE(6,2) NOT NULL, " + COLUMN_AGENCY + " INTEGER NOT NULL, " +
        "FOREIGN KEY (" + COLUMN_AGENCY + ") REFERENCES " + AgencyStatements.TABLE_NAME + "(" + AgencyStatements.COLUMN_ID + "));";
}
