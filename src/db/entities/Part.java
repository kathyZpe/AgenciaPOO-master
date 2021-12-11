package db.entities;

/*
 * Clase para la creación de objetos con los atributos que puede contener una Refaccion conforme a la base de datos
 * */

public class Part {
    private int id;
    private String name;
    private int unities;
    private double price;
    private int agencyId;

    public Part(int id, String name, int unities, double price, int agencyId) {
        this.id = id;
        this.name = name;
        this.unities = unities;
        this.price = price;
        this.agencyId = agencyId;
    }

    public Part(String name, int unities, double price, int agencyId) {
        this.name = name;
        this.unities = unities;
        this.price = price;
        this.agencyId = agencyId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUnities() {
        return unities;
    }

    public double getPrice() {
        return price;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setUnities(int unities) {
        this.unities = unities;
    }

    @Override
    public String toString() {
        return name + "-" + unities;
    }
}
