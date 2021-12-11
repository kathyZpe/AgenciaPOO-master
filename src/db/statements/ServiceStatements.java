package db.statements;

/*
 * Clase con todos los nombres de columnas para ser usado en peticiones y la sentencia de SQL para crear la tabla
 * */

public class ServiceStatements {
    public static final String TABLE_NAME = "services";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE = "service_type";
    public static final String COLUMN_DELIVERED = "delivered";
    public static final String COLUMN_ARRIVAL = "arrival";
    public static final String COLUMN_QUIT = "quit";

    public static final String COLUMN_NAME = "user_name";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_REGISTRATION = "registration";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + COLUMN_ID
            + " INTEGER AUTO_INCREMENT PRIMARY KEY UNIQUE, " + COLUMN_TYPE + " INTEGER NOT NULL, " + COLUMN_DELIVERED
            + " BOOLEAN NOT NULL, " + COLUMN_ARRIVAL + " DATE NOT NULL, " + COLUMN_QUIT + " DATE NOT NULL, "
            + COLUMN_NAME
            + " NVARCHAR(12) NOT NULL, " + COLUMN_SURNAME + " NVARCHAR(12) NOT NULL, " + COLUMN_MODEL
            + " NVARCHAR(20) NOT NULL, "
            + COLUMN_REGISTRATION + " NVARCHAR(20) NOT NULL, " + COLUMN_PHONE + " NVARCHAR(30) NOT NULL," + COLUMN_EMAIL
            + " NVARCHAR(30) NOT NULL);";
}
