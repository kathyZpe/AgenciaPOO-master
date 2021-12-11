package db.dao;

import java.util.List;

/*
* Interface para implementar metodos para obtener acceso a datos por medio de objetos
* */

public interface Dao<T> {
    /*
    * Llena la tabla con datos por defecto en caso de que este vacia
    * */
    void fillTable();

    /*
    * Crea nueva columna con los datos proporcionados por los atributos del objeto
    * */
    void save(T t);

    /*
    * Obtiene datos de una columna cuyo id coincida con el proporcionado, devolviendo un objeto
    * */
    T get(int id);

    /*
    * Devuelve una lista de objetos con todo el contenido de la tabla
    * */
    List<T> getAll();

    /*
    * Actualiza los valores de una columna con los atributos del objeto pasado como parametro
    * */
    void update(T t);

    /*
    * Borra los valores de la columna de la tabla cuyo id coincida con el proporcionado
    * */
    void delete(int id);
}
