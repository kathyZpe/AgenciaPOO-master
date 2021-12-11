package windows;

import db.entities.Service;
import exceptions.BadInputException;
import listeners.AddClientListener;
import db.dao.ServiceDao;

import javax.security.auth.PrivateCredentialPermission;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.DataInput;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.regex.*;
import java.util.regex.Pattern;
import java.sql.Date;

public class principal extends JFrame {

    private JPanel contenidoPanel;
    private JPanel subPanel1;
    private JPanel subPanel2;
    private JPanel northPanel;

    private JButton nextButton;
    private JButton insertButton;

    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel emailLabel;
    private JLabel phoneLabel;
    private JLabel modelLabel;

    private JLabel registrationLabel;
    private JLabel serviceLabel;

    private JLabel dateInputL;
    private JLabel dateQuitL;

    private JTextField dateInputF;
    private JTextField dateQuitF;
    private JTextField searchTextField;

    private JComboBox<String> combo;

    private JList<Service> serviceJList;

    private DefaultListModel<Service> serviceModel;

    private ServiceDao serviceDao;
    private Service service;

    private List<Service> serviceList;

    private String newName;
    private String newSurname;
    private String newModel;
    private String newRegistration;
    private String newPhone;
    private String newEmail;
    private int typeService;

    public principal() {

        serviceDao = new ServiceDao();
        serviceModel = new DefaultListModel<>();
        serviceList = new ArrayList<>();

        initializeLabel();
        initializeButton();
        initializeList();
        initializeText();
        initializeCombobox();
        initializePanels();

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if (newName == null)
                        throw new BadInputException("No se registro el nombre");
                    if (newSurname == null)
                        throw new BadInputException("No se registro el apellido");
                    if (newModel == null)
                        throw new BadInputException("No se registro el modelo");
                    if (newRegistration == null)
                        throw new BadInputException("No se registro el matricula");
                    if (newPhone == null)
                        throw new BadInputException("No se registro el telefono");
                    if (newEmail == null)
                        throw new BadInputException("No se registro el correo");
                    if (dateInputF.getText().isEmpty())
                        throw new BadInputException("No se registro fecha de entrada");
                    if (Pattern.matches("^(20(\\d{2}))(\\/|-)(0[1-9]|1[0-2])\\2([0-2][0-9]|3[0-1])$",
                            dateInputF.getText()))
                        throw new BadInputException("El formato de entrada es el incorrecto");
                    if (dateQuitF.getText().isEmpty())
                        throw new BadInputException("No se registro fecha de salida");
                    if (Pattern.matches("^(20(\\d{2}))(\\/|-)(0[1-9]|1[0-2])\\2([0-2][0-9]|3[0-1])$",
                            dateQuitF.getText()))
                        throw new BadInputException("El formato de entrada es el incorrecto");

                    Date arrival = Date.valueOf(dateInputF.getText());
                    Date quit = Date.valueOf(dateQuitF.getText());

                    dispose();
                    Service service = new Service(typeService,
                            arrival,
                            quit,
                            newName,
                            newSurname,
                            newModel,
                            newRegistration,
                            newPhone,
                            newEmail);
                    serviceDao.save(service);
                    ProviderWindow window = new ProviderWindow(service);
                    window.run();

                } catch (BadInputException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Error en campos",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

        });

        combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                typeService = combo.getSelectedIndex();
            }
        });

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AddClientWindow window = new AddClientWindow();
                window.setAddClientListener(new AddClientListener() {

                    @Override
                    public void OnCancel() {

                        JOptionPane.showMessageDialog(null, "No se registraron los datos del cliente",
                                "Registro cliente",
                                JOptionPane.INFORMATION_MESSAGE);
                    }

                    @Override
                    public void OnAdd(String name, String surname, String model, String registration, String phone,
                            String email) {
                        newName = name;
                        newSurname = surname;
                        newModel = model;
                        newRegistration = registration;
                        newPhone = phone;
                        newEmail = email;

                        updateLabelByAdding();
                    }

                });
                window.run();
            }

        });

        serviceJList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                service = serviceJList.getSelectedValue();
                updateLabelsByService();
            }

        });

        dateInputF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                JTextField textField = ((JTextField) e.getComponent());
                int inputLength = textField.getText().length();
                if (inputLength > 9)
                    e.consume();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        dateQuitF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                JTextField textField = ((JTextField) e.getComponent());
                int inputLength = textField.getText().length();
                if (inputLength > 9)
                    e.consume();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        searchTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (Character.isLetter(e.getKeyChar())) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                JTextField textField = ((JTextField) e.getComponent());
                if (!textField.getText().isEmpty()) {
                    int id = Integer.parseInt(textField.getText());
                    Service tempService = serviceDao.get(id);
                    serviceModel.clear();
                    serviceModel.addElement(tempService);

                } else {
                    updateServiceListAll();
                }
            }
        });

        serviceJList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    ServiceWindow serviceWindow = new ServiceWindow(service);
                    serviceWindow.setServiceWindowListener(new ServiceWindow.ServiceWindowListener() {

                        @Override
                        public void onDelete() {
                            updateServiceListAll();
                            clearLabels();
                            service = null;
                        }

                    });
                    serviceWindow.run();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        setUp();
    }

    private void setUp() {

        subPanel1.add(nameLabel);
        subPanel1.add(surnameLabel);
        subPanel1.add(emailLabel);
        subPanel1.add(phoneLabel);
        subPanel1.add(modelLabel);
        subPanel1.add(registrationLabel);
        subPanel1.add(insertButton);

        subPanel2.add(serviceLabel);
        subPanel2.add(serviceJList);

        subPanel2.add(dateInputL);
        subPanel2.add(dateInputF);
        subPanel2.add(dateQuitL);
        subPanel2.add(dateQuitF);
        subPanel2.add(combo);

        subPanel2.add(nextButton);

        contenidoPanel.add(subPanel1);
        contenidoPanel.add(subPanel2);

        northPanel.add(searchTextField);

        add(contenidoPanel, BorderLayout.CENTER);
        add(northPanel, BorderLayout.NORTH);

        setTitle("Inicio");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initializePanels() {

        contenidoPanel = new JPanel(new GridLayout(1, 2));
        subPanel1 = new JPanel();
        subPanel1.setBorder(BorderFactory.createTitledBorder("Cliente"));
        subPanel1.setLayout(new GridLayout(14, 2));
        subPanel2 = new JPanel();
        subPanel2.setBorder(BorderFactory.createTitledBorder("Lista Servicios"));
        subPanel2.setLayout(new GridLayout(8, 2));

        northPanel = new JPanel();
    }

    private void initializeButton() {
        nextButton = new JButton("Añadir Servicio");
        insertButton = new JButton("Añadir Cliente");
    }

    private void initializeText() {
        dateQuitF = new JTextField();
        dateInputF = new JTextField();
        searchTextField = new JTextField(10);
    }

    private void initializeLabel() {
        nameLabel = new JLabel("Nombre:");
        surnameLabel = new JLabel("Apellido:");
        emailLabel = new JLabel("Correo:");
        phoneLabel = new JLabel("Telefono:");
        modelLabel = new JLabel("Modelo:");
        registrationLabel = new JLabel("Matricula:");
        serviceLabel = new JLabel("Lista de Servicios:");
        dateInputL = new JLabel("Fecha Entrada AAAA/MM/DD");
        dateQuitL = new JLabel("Fecha Salida  AAAA/MM/DD");
    }

    private void initializeList() {
        serviceJList = new JList<>(serviceModel);

        serviceList = serviceDao.getAll();
        if (serviceList != null) {

            for (int index = 0; index < serviceList.size(); index++) {
                serviceModel.addElement(serviceList.get(index));
            }

        }

        serviceJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        serviceJList.setVisibleRowCount(-1);
    }

    private void initializeCombobox() {
        combo = new JComboBox<>();
        combo.addItem("Mantenimiento");
        combo.addItem("Refacciones");
    }

    private void updateServiceListAll() {
        serviceModel.clear();
        serviceList = serviceDao.getAll();
        if (serviceList != null) {

            for (int index = 0; index < serviceList.size(); index++) {
                serviceModel.addElement(serviceList.get(index));
            }
        }
    }

    private void updateLabelByAdding() {
        nameLabel.setText("Nombre: " + newName);
        surnameLabel.setText("Apellido: " + newSurname);
        emailLabel.setText("Correo: " + newEmail);
        phoneLabel.setText("Telefono: " + newPhone);
        modelLabel.setText("Modelo: " + newModel);
        registrationLabel.setText("Matricula: " + newRegistration);
    }

    private void updateLabelsByService() {
        nameLabel.setText("Nombre: " + service.getName());
        surnameLabel.setText("Apellido: " + service.getSurname());
        emailLabel.setText("Correo: " + service.getEmail());
        phoneLabel.setText("Telefono: " + service.getPhone());
        modelLabel.setText("Modelo: " + service.getModel());
        registrationLabel.setText("Matricula: " + service.getRegistration());
    }

    private void clearLabels() {
        nameLabel.setText("Nombre:");
        surnameLabel.setText("Apellido:");
        emailLabel.setText("Correo:");
        phoneLabel.setText("Telefono:");
        modelLabel.setText("Modelo:");
        registrationLabel.setText("Matricula:");
    }

    public void run() {
        setVisible(true);
    }

}
