package windows;

import db.dao.ServiceDao;
import db.entities.Service;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ServiceWindow extends JFrame implements WindowListener {
    private JPanel northPanel;
    private JPanel centerPanel;
    private JPanel southPanel;

    private JLabel titleLabel;
    private JLabel titleTypeLabel;
    private JLabel typeLabel;
    private JLabel titleArrivalLabel;
    private JLabel arrivalLabel;
    private JLabel titleQuitLabel;
    private JLabel quitLabel;
    private JLabel titleDeliveredLabel;
    private JLabel deliveredLabel;

    private JButton deleteButton;
    private JButton closeButton;
    private JButton deliverButton;

    private Service serviceSelected;
    private ServiceDao serviceDao;

    public interface ServiceWindowListener {
        void onDelete(); // Evento para cuando se elimina el servicio
        void onDelivered(); // Evento para cuando se entrega el servicio
    }

    private ServiceWindowListener listener;

    public ServiceWindow(Service service) {
        /*
        * Se crean objetos, se dan valores por defecto y se agregan listeners a los componentes
        * */
        this.serviceSelected = service;
        serviceDao = new ServiceDao();

        initializePanels();
        initializeButtons();
        initializeLabels();

        deliverButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                serviceSelected.setDelivered(true);
                serviceDao.update(serviceSelected);
                deliveredLabel.setText("Entregado");
                listener.onDelivered();
            }

        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serviceDao.delete(serviceSelected.getId()); // Borra el
                listener.onDelete();
                dispose();
            }
        });

        closeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }

        });

        setUp();

        setInfo();
    }

    private void setUp() {
        /*
         * Agrega los componentes a los paneles o frame respectivos, al igual que
         * configura el frame
         */
        // Panel norte
        northPanel.add(titleLabel);

        // Panel Central
        centerPanel.add(titleTypeLabel);
        centerPanel.add(typeLabel);
        centerPanel.add(titleArrivalLabel);
        centerPanel.add(arrivalLabel);
        centerPanel.add(titleQuitLabel);
        centerPanel.add(quitLabel);
        centerPanel.add(titleDeliveredLabel);
        centerPanel.add(deliveredLabel);

        // Panel Sur
        southPanel.add(deleteButton);
        southPanel.add(closeButton);
        southPanel.add(deliverButton);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        setTitle("Servicio");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        addWindowListener(this);
        setSize(300, 400);
    }

    public void setServiceWindowListener(ServiceWindowListener serviceWindowListener) {
        // Asigna un listener
        this.listener = serviceWindowListener;
    }

    public void run() {
        // Despliega la ventana
        setVisible(true);
    }

    private void initializePanels() {
        // Inicializa y configura todos los JPanel
        northPanel = new JPanel();
        northPanel.setBackground(new Color(247, 191, 190, 255));
        northPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        centerPanel = new JPanel(new GridLayout(10, 1));
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        southPanel = new JPanel();
        southPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    private void initializeButtons() {
        // Inicializa y configura todos los JButton
        deleteButton = new JButton("Borrar");
        deleteButton.setBackground(new Color(255, 255, 255, 255));

        closeButton = new JButton("Cerrar");
        closeButton.setBackground(new Color(247, 191, 190, 255));
        
        deliverButton = new JButton("Entregar");
        deliverButton.setBackground(new Color(247, 191, 190, 255));
    }

    private void initializeLabels() {
        // Inicializa y configura todos los JLabel
        titleLabel = new JLabel("Ver Servicio");

        titleTypeLabel = new JLabel("Tipo:");
        typeLabel = new JLabel("PLACEHOLDER");

        titleArrivalLabel = new JLabel("Fecha Entrada:");
        arrivalLabel = new JLabel("PLACEHOLDER");

        titleQuitLabel = new JLabel("Fecha Salida");
        quitLabel = new JLabel("PLACEHOLDER");

        titleDeliveredLabel = new JLabel("Estado de Entrega:");
        deliveredLabel = new JLabel("PLACEHOLDER");
    }

    private void setInfo() {
        // Establece la informacion que sera desplega en los componentes
        if (serviceSelected.getService() == 0) {
            typeLabel.setText("Reparacion");
        } else if (serviceSelected.getService() == 1) {
            typeLabel.setText("Mantenimiento");
        }

        arrivalLabel.setText(serviceSelected.getArrival().toString());
        quitLabel.setText(serviceSelected.getQuit().toString());
        if (serviceSelected.isDelivered()) {
            deliveredLabel.setText("Entregado");
        } else {
            deliveredLabel.setText("No entregado");
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        // Cierra conexion con la base de datos
        serviceDao.close();
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

}
