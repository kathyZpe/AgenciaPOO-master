package windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import exceptions.BadInputException;
import listeners.AddClientListener;

public class AddClientWindow extends JFrame {
    private final int TEXT_FIELD_COLUMNS = 2;

    private AddClientListener listener;
    private JPanel mainPanel;

    private JButton closeButton;
    private JButton registerButton;

    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel emailLabel;
    private JLabel phoneLabel;
    private JLabel modelLabel;
    private JLabel registrationLabel;

    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField emailTextField;
    private JTextField phoneTextField;
    private JTextField modelTextField;
    private JTextField registrationTextField;

    public AddClientWindow() {
        /*
         * Se crean objetos, se dan valores por defecto y se agregan listeners a los
         * componentes
         */
        mainPanel = new JPanel(new GridLayout(14, 1));

        initializeTextField();
        initializeButtons();
        initializeLabels();

        nameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                JTextField textField = ((JTextField) e.getComponent());
                int inputLength = textField.getText().length();
                if (Character.isDigit(key) || inputLength > 11)
                    e.consume(); // Solo admite letras
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        surnameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                JTextField textField = ((JTextField) e.getComponent());
                int inputLength = textField.getText().length();
                if (Character.isDigit(key) || inputLength > 11)
                    e.consume(); // Solo admite letras
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        emailTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                JTextField textField = ((JTextField) e.getComponent());
                int inputLength = textField.getText().length();
                if (Character.isDigit(key) || inputLength > 29)
                    e.consume(); // Solo admite letras
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        phoneTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                JTextField textField = ((JTextField) e.getComponent());
                int inputLength = textField.getText().length();
                if (Character.isLetter(key) || inputLength > 9)
                    e.consume(); // Solo admite numeros
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        modelTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                JTextField textField = ((JTextField) e.getComponent());
                int inputLength = textField.getText().length();
                if (Character.isDigit(key) || inputLength > 19)
                    e.consume(); // Solo admite letras
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        registrationTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                JTextField textField = ((JTextField) e.getComponent());
                int inputLength = textField.getText().length();
                if (inputLength > 7)
                    e.consume(); // Admite numeros y letras
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cierra ventana y dispara un evento
                dispose();
                listener.OnCancel(); // Dispara indicando que se cancelo
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Valida todos los valores necesarios
                    if (nameTextField.getText().isEmpty())
                        throw new BadInputException("Nombre esta vacio");
                    if (nameTextField.getText().length() > 12)
                        throw new BadInputException("Nombre debe ser menor o igual 12 caracteres");
                    if (surnameTextField.getText().isEmpty())
                        throw new BadInputException("Apellido esta vacio");
                    if (surnameTextField.getText().length() > 12)
                        throw new BadInputException("Apellido debe ser menor o igual 12 caracteres");
                    if (emailTextField.getText().isEmpty())
                        throw new BadInputException("Correo esta vacio");
                    if (emailTextField.getText().length() > 30)
                        throw new BadInputException("Correo debe ser meno o igual a 30 caracteres");
                    if (!emailTextField.getText().contains("@"))
                        throw new BadInputException("Correo no contiene arroba");
                    if (phoneTextField.getText().isEmpty())
                        throw new BadInputException("Telefono esta vacio");
                    if (phoneTextField.getText().length() > 10)
                        throw new BadInputException("Telefeno debe ser meno o igual a 10 caracteres");
                    if (modelTextField.getText().isEmpty())
                        throw new BadInputException("Modelo esta vacio");
                    if (modelTextField.getText().length() > 20)
                        throw new BadInputException("Modelo debe ser menor o igual a 20 caracteres");
                    if (registrationTextField.getText().isEmpty())
                        throw new BadInputException("Matricula esta vacia");
                    if (registrationTextField.getText().length() == 7)
                        throw new BadInputException("Matricula debe de ser de 8 characteres");

                    dispose();
                    // Dispara evento para comunicar los datos del cliente
                    listener.OnAdd(
                            nameTextField.getText(), surnameTextField.getText(), modelTextField.getText(),
                            registrationTextField.getText(), phoneTextField.getText(), emailTextField.getText());

                } catch (BadInputException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Error en campos",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        setUp();
    }

    public void setAddClientListener(AddClientListener listener) {
        // Asigna un listener
        this.listener = listener;
    }

    private void setUp() {
        /*
         * Agrega los componentes a los paneles o frame respectivos, al igual que
         * configura el frame
         */
        mainPanel.add(nameLabel);
        mainPanel.add(nameTextField);
        mainPanel.add(surnameLabel);
        mainPanel.add(surnameTextField);
        mainPanel.add(emailLabel);
        mainPanel.add(emailTextField);
        mainPanel.add(phoneLabel);
        mainPanel.add(phoneTextField);
        mainPanel.add(modelLabel);
        mainPanel.add(modelTextField);
        mainPanel.add(registrationLabel);
        mainPanel.add(registrationTextField);

        mainPanel.add(registerButton);
        mainPanel.add(closeButton);

        add(mainPanel);

        setResizable(false);
        setTitle("Registro de Usuario");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 500);
    }

    private void initializeButtons() {
        // Inicializa y configura todos los JButton
        closeButton = new JButton("Cerrar");
        registerButton = new JButton("Registrar");
    }

    private void initializeTextField() {
        // Inicializa y configura todos los JTextField
        nameTextField = new JTextField(TEXT_FIELD_COLUMNS);
        surnameTextField = new JTextField(TEXT_FIELD_COLUMNS);
        emailTextField = new JTextField(TEXT_FIELD_COLUMNS);
        phoneTextField = new JTextField(TEXT_FIELD_COLUMNS);
        modelTextField = new JTextField(TEXT_FIELD_COLUMNS);
        registrationTextField = new JTextField(TEXT_FIELD_COLUMNS);
    }

    private void initializeLabels() {
        // Inicializa y configura todos los JLabels
        nameLabel = new JLabel("Nombre:");
        surnameLabel = new JLabel("Apellido:");
        emailLabel = new JLabel("Correo:");
        phoneLabel = new JLabel("Telefono:");
        modelLabel = new JLabel("Modelo:");
        registrationLabel = new JLabel("Matricula:");
    }

    public void run() {
        // Despliega la ventana
        setVisible(true);
    }
}
