package com.fadedbytes.colegio.client;

import com.fadedbytes.colegio.api.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    public JPanel background;
    private JTabbedPane tabs;
    private JPanel tabInicio;
    private JPanel tabLoad;
    private JTextField inputID;
    private JButton loginButton;
    private JLabel textBienvenida;
    private JLabel textEmail;
    private JLabel textSalary;
    private JLabel textMedia;

    public MainMenu() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(inputID.getText());
            }
        });
    }

    private void login(String id) {
        User user;
        try {
            user = User.getUser(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            cry("El ID no es válido, debe ser un número entero");
            return;
        }

        if (user == null) {
            cry("El ID no existe");
        }

        updateUser(user);

    }

    private void updateUser(User user) {

    }

    private void cry(String message) {
        JOptionPane.showMessageDialog(background, message);
    }
}
