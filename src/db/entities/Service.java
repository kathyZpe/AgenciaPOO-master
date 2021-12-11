package db.entities;

import java.sql.Date;

/*
 * Clase para la creación de objetos con los atributos que puede contener un registro de servicio conforme
 * a la base de datos
 * */

public class Service {
    private int id;
    private int service;
    private boolean delivered;
    private Date arrival;
    private Date quit;
    private String name;
    private String surname;
    private String model;
    private String registration;
    private String phone;
    private String email;

    public Service(int id, int service, boolean delivered, Date arrival, Date quit, String name, String surname,
            String model, String registration, String phone, String email) {
        this.id = id;
        this.service = service;
        this.delivered = delivered;
        this.arrival = arrival;
        this.quit = quit;
        this.name = name;
        this.surname = surname;
        this.model = model;
        this.registration = registration;
        this.phone = phone;
        this.email = email;
    }

    public Service(int service, Date arrival, Date quit, String name, String surname, String model, String registration,
            String phone, String email) {
        this.id = 0;
        this.delivered = false;
        this.service = service;
        this.arrival = arrival;
        this.quit = quit;
        this.name = name;
        this.surname = surname;
        this.model = model;
        this.registration = registration;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public int getService() {
        return service;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public Date getArrival() {
        return arrival;
    }

    public Date getQuit() {
        return quit;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getModel() {
        return model;
    }

    public String getRegistration() {
        return registration;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("%d", getId());
    }
}
