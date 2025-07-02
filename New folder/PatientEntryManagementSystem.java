package com.mycompany.patiententrymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

// Abstraction
abstract class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getBasicInfo() {
        return "Name: " + name + "\nAge: " + age;
    }

    public abstract String getType();
}

// Regular Patient (Inheritance)
class   RegularPatient extends Person {
    public RegularPatient(String name, int age) {
        super(name, age);
    }

    @Override
    public String getType() {
        return "Regular Patient"; // POLYMORPHISM(OVERRIDE)
    }
}

// Emergency Patient (Inheritance)
class EmergencyPatient extends Person {
    public EmergencyPatient(String name, int age) {
        super(name, age);
    }

    @Override
    public String getType() {
        return "Emergency Patient";
    }
}

// MAIN
public class PatientEntryManagementSystem {

    // MAIN METHOD
    public static void main(String[] args) {
        showLoginScreen();  // Start with login portal
    }

    //  LOGIN SCREEN
    private static void showLoginScreen() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 200);
        loginFrame.setLayout(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.getContentPane().setBackground(new Color(240, 240, 255));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 80, 25);
        JTextField userField = new JTextField();
        userField.setBounds(110, 30, 140, 25);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 80, 25);
        JPasswordField passField = new JPasswordField();
        passField.setBounds(110, 70, 140, 25);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(100, 110, 100, 30);
        loginBtn.setBackground(new Color(0, 61, 215, 230));
        loginBtn.setForeground(Color.WHITE);

        loginFrame.add(userLabel); loginFrame.add(userField);
        loginFrame.add(passLabel); loginFrame.add(passField);
        loginFrame.add(loginBtn);

        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = String.valueOf(passField.getPassword()).trim();

            if ((username.equals("Hassi") && password.equals("123"))||(username.equals("Ehti") && password.equals("159"))) {
                JOptionPane.showMessageDialog(loginFrame, "Login successful!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
                loginFrame.dispose();  // Close login window
                showPatientEntryForm();  // Open main form
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginFrame.setVisible(true);
    }

    //  PATIENT ENTRY FORM
    private static void showPatientEntryForm() {
        JFrame frame = new JFrame("Patient Entry Form");
        frame.setSize(350, 250);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(230, 245, 255)); // Light blue

        // Name input
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 80, 25);
        JTextField nameField = new JTextField();
        nameField.setBounds(100, 20, 200, 25);

        // Age input
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(20, 60, 80, 25);
        JTextField ageField = new JTextField();
        ageField.setBounds(100, 60, 200, 25);

        // Patient Type
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setBounds(20, 100, 80, 25);
        String[] types = {"Regular", "Emergency"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        typeBox.setBounds(100, 100, 200, 25);

        // Submit button
        JButton submitBtn = new JButton("Submit");
        submitBtn.setBounds(100, 140, 100, 30);
        submitBtn.setBackground(new Color(0, 123, 255));
        submitBtn.setForeground(Color.WHITE);

        // Add components to frame
        frame.add(nameLabel); frame.add(nameField);
        frame.add(ageLabel); frame.add(ageField);
        frame.add(typeLabel); frame.add(typeBox);
        frame.add(submitBtn);

        // Submit Button Action
        submitBtn.addActionListener((ActionEvent e) -> {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();

            // Validate name
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Name cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate age
            int age;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Age must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create patient object
            String type = (String) typeBox.getSelectedItem();
            Person patient = type.equals("Emergency") ? new EmergencyPatient(name, age) : new RegularPatient(name, age);
            String patientData = patient.getBasicInfo() + "\nType: " + patient.getType() + "\n---\n";

            // Save to file
            try {
                try (FileWriter fw = new FileWriter("patients.txt", true) // append mode
                ) {
                    fw.write(patientData);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving to file.", "File Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(frame, patientData, "Patient Info Saved", JOptionPane.INFORMATION_MESSAGE);
            nameField.setText("");
            ageField.setText("");
            typeBox.setSelectedIndex(0);
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}