package db.entities;

/*
* Clase para la creación de objetos con los atributos que puede contener una Agencia conforme a la base de datos
* */

public class Agency {
    private int id;
    private String name;

    public Agency(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Agency(String name) {
        this.id = 0;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Devuelve el nombre de la agencia
    @Override
    public String toString() {
        return name;
    }
}
