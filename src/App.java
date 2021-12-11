import db.entities.Service;

import listeners.AddClientListener;
import windows.AddClientWindow;
import windows.ProviderWindow;
import windows.principal;

import java.sql.Date;

public class App {
    public static void main(String[] args) {
        long now = System.currentTimeMillis();
        Date dateNow = new Date(now);
        Date dateTomorrow = Date.valueOf("2021-12-10");
        Service service = new Service(0, dateNow, dateTomorrow, "Gamaliel", "Garcia", "Nissa", "0000XXX",
            "5951140476", "egamagz@hotmail.com");

        principal window = new principal();

        //ProviderWindow window = new ProviderWindow(service);
        window.run();
    }
}
