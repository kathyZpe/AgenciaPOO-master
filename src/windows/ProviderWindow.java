package windows;

import db.dao.AgencyDao;
import db.dao.PartDao;
import db.entities.Agency;
import db.entities.Part;
import db.entities.Service;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class ProviderWindow extends JFrame implements WindowListener {
    private List<Agency> agencyList;
    private List<Part> partList;
    private List<Part> partsSelected;

    private AgencyDao agencyDao;
    private PartDao partDao;

    private JPanel centerPanel;
    private JPanel northPanel;
    private JPanel westPanel;
    private JPanel costumerPanel;
    private JPanel partsPanel;
    private JPanel infoPanel;

    private DefaultListModel<Agency> agencyModel;
    private DefaultListModel<Part> partsModel;
    private DefaultListModel<Part> selectedPartsModel;

    private JList<Agency> agencyJList;
    private JList<Part> partJList;
    private JList<Part> partsSelectedJList;

    private JTextField searchTextField;

    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel modelLabel;
    private JLabel phoneLabel;
    private JLabel priceLabel;

    private JButton confirmButton;
    private JButton addButton;
    private JButton deleteButton;

    private Agency selectedAgency;
    private Service service;

    private double totalPrice;
    private double servicePrice;
    private double partsPrice;

    public ProviderWindow(Service service) {
        /*
        * Se crean objetos, se dan valores por defecto y se agregan listeners a los componentes
        * */
        this.service = service;
        totalPrice = 0;
        servicePrice = 0;
        partsPrice = 0;

        if(service.getService() == 1){ // Reparacion
            long time = service.getQuit().getTime() - service.getArrival().getTime();
            long days = (time / (1000 * 60 * 60 * 24)) % 365;
            if (days >= 1){
                servicePrice = 1000 * days;
            }
            else{
                servicePrice = 1000;
            }
        }else if(service.getService() == 0){ // Mantenimiento
            servicePrice = 1000;
        }

        agencyModel = new DefaultListModel<>();
        partsModel = new DefaultListModel<>();
        selectedPartsModel = new DefaultListModel<>();

        agencyList = new ArrayList<>();
        partList = new ArrayList<>();
        partsSelected = new ArrayList<>();

        agencyDao = new AgencyDao();
        partDao = new PartDao();

        initializePanels();
        initializeButtons();
        initializeList();
        initializeTextField();
        initializeLabels();

        searchTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                JTextField textField = ((JTextField) e.getComponent());
                // Buscara todas las agencias que tengan en su nombre lo escrito
                if (!textField.getText().isEmpty()) {
                    if (Character.isLetter(e.getKeyChar())) {
                        agencyList = agencyDao.getLikeName(textField.getText());
                        agencyModel.clear();
                        for (int i = 0; i < agencyList.size(); i++) {
                            agencyModel.addElement(agencyList.get(i));
                        }
                    }
                } else {
                    // Cuando este vacio, obtendra las agencias
                    agencyList = agencyDao.getAll();
                    agencyModel.clear();
                    for (int i = 0; i < agencyList.size(); i++) {
                        agencyModel.addElement(agencyList.get(i));
                    }
                }
            }
        });

        agencyJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedAgency = agencyJList.getSelectedValue();
                partList = partDao.getAllByAgency(selectedAgency);

                updatePartList();
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(
                    null,
                    "Estas apunto de salir, ¿Confirmas?",
                    "Proveedor",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if (response == 0){
                    dispose();
                    principal window = new principal();
                    window.run();
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = partJList.getSelectedIndex();
                    Part selectedPart = partList.get(index);
                    if (selectedPart.getUnities() > 0) {
                        selectedPart.setUnities(selectedPart.getUnities() - 1);
                        partDao.update(selectedPart);
                        partsSelected.add(new Part(
                                selectedPart.getId(), selectedPart.getName(), 1,
                                selectedPart.getPrice(), selectedPart.getAgencyId()
                            )
                        );

                        updatePartSelectedList();
                        updatePartList();
                        updatePrice();
                    }
                } catch (IndexOutOfBoundsException exception) {
                    JOptionPane.showMessageDialog(
                        null, "Selecciona una refaccion", "Error en campos", JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = partsSelectedJList.getSelectedIndex();
                    int id = partsSelected.remove(index).getId();
                    selectedPartsModel.remove(index);

                    Part part = partDao.get(id);
                    part.setUnities(part.getUnities() + 1);
                    partDao.update(part);

                    updatePrice();
                    updatePartSelectedList();
                    updatePartList();
                } catch (IndexOutOfBoundsException exception) {
                    JOptionPane.showMessageDialog(
                        null, "Selecciona una refaccion para eliminar", "Error en campos", JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });

        setUp();
    }

    private void setUp() {
        /*
        * Agrega los componentes a los paneles o frame respectivos, al igual que configura el frame
        * */
        updatePrice();

        // Panel del centro
        centerPanel.setBorder(BorderFactory.createTitledBorder("Refacciones"));
        costumerPanel.add(nameLabel);
        costumerPanel.add(surnameLabel);
        costumerPanel.add(modelLabel);
        costumerPanel.add(phoneLabel);
        partsPanel.add(partJList);
        partsPanel.add(partsSelectedJList);
        infoPanel.add(addButton);
        infoPanel.add(deleteButton);
        infoPanel.add(priceLabel);
        infoPanel.add(confirmButton);
        centerPanel.add(costumerPanel, BorderLayout.PAGE_START);
        centerPanel.add(partsPanel, BorderLayout.CENTER);
        centerPanel.add(infoPanel, BorderLayout.PAGE_END);

        // Panel superior
        northPanel.add(searchTextField);

        // Panel lateral izquierdo
        westPanel.add(agencyJList);

        add(centerPanel, BorderLayout.CENTER);
        add(northPanel, BorderLayout.NORTH);
        add(westPanel, BorderLayout.WEST);

        setTitle("Proveedor");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 500);
        addWindowListener(this);
    }

    private void initializePanels() {
        // Inicializa y configura todos los JPanel
        northPanel = new JPanel();
        centerPanel = new JPanel(new BorderLayout());

        westPanel = new JPanel();
        westPanel.setBorder(BorderFactory.createTitledBorder("Proveedores"));

        costumerPanel = new JPanel(new GridLayout(2, 2));
        costumerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        partsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        infoPanel = new JPanel();
    }

    private void initializeButtons() {
        // Inicializa y configura todos los JButton
        confirmButton = new JButton("Confirmar");
        confirmButton.setBackground(new Color(247, 191, 190, 255));

        addButton = new JButton("Añadir");
        addButton.setBackground(new Color(247, 191, 190, 255));

        deleteButton = new JButton("Eliminar");
        deleteButton.setBackground(new Color(247, 191, 190, 255));
    }

    private void initializeLabels() {
        // Inicializa y configura todos los JLabel
        nameLabel = new JLabel("Nombre: " + service.getName());
        surnameLabel = new JLabel("Apellido: " + service.getSurname());
        modelLabel = new JLabel("Modelo: " + service.getModel());
        phoneLabel = new JLabel("Telefono: " + service.getPhone());
        priceLabel = new JLabel("70000 + 1000 Total a pagar: 8000");
    }

    private void initializeList() {
        // Inicializa, configura y da valores a todos los JList
        agencyJList = new JList<>(agencyModel);
        partJList = new JList<>(partsModel);
        partsSelectedJList = new JList<>(selectedPartsModel);

        agencyList = agencyDao.getAll();

        for (int i = 0; i < agencyList.size(); i++) {
            agencyModel.addElement(agencyList.get(i));
        }

        agencyJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        agencyJList.setVisibleRowCount(-1);

        partJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        partJList.setVisibleRowCount(-1);

        partsSelectedJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        partsSelectedJList.setVisibleRowCount(-1);
    }

    private void initializeTextField() {
        // Inicializa y configura todos los JTextFields
        searchTextField = new JTextField(10);
    }

    private void updatePartList() {
        /*
        * Actualiza los elemento del JList que contiene las refacciones que ofrece la agencia seleccionada
        * */
        partsModel.clear();
        partList = partDao.getAllByAgency(selectedAgency);
        for (int i = 0; i < partList.size(); i++) {
            partsModel.addElement(partList.get(i));
        }
    }

    private void updatePartSelectedList() {
        /*
        * Actualiza los elementos del JList que contiene las refacciones seleccionadas que van a ser usadas
        * */
        selectedPartsModel.clear();
        for (int i = 0; i < partsSelected.size(); i++) {
            selectedPartsModel.addElement(partsSelected.get(i));
        }
    }

    private void updatePrice(){
        /*
        * Actualiza el JLabel que se encarga de mostrar el precio del servicio, la suma de las refacciones y la suma
        * total de ambas cosas
        * */
        totalPrice = servicePrice;
        partsPrice = 0;
        for (int i = 0; i < partsSelected.size(); i++) {
            totalPrice += partsSelected.get(i).getPrice();
            partsPrice += partsSelected.get(i).getPrice();
        }
        priceLabel.setText(servicePrice + " + " + (Math.round(partsPrice*100.0)/100.0) + "Total a pagar: $" + Math.round(totalPrice*100.0)/100.0);
    }

    public void run() {
        // Despliega la ventana
        setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        // Cierra la conexion con la base de datos cuando se cierra la ventana
        agencyDao.close();
        partDao.close();
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
