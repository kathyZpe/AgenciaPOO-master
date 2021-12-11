import db.entities.Service;

import listeners.AddClientListener;
import windows.AddClientWindow;
import windows.ProviderWindow;
import windows.principal;

import java.sql.Date;

public class App {
    public static void main(String[] args) {

        principal window = new principal();
        window.run();
    }
}
