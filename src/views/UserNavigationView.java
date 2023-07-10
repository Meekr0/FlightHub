package views;

import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserNavigationView extends JFrame {
    private JLabel titleLabel;
    private JLabel panelLabel;
    private JButton passengerButton;
    private JButton employeeButton;
    private Session session;

    private PassengerFunctionalityView passengerFunctionalityView;

    public UserNavigationView(Session session) {

        this.session = session;

        setTitle("FlightHub");

        setSize(new Dimension(1000, 500));
        setMinimumSize(new Dimension(800, 400));
        this.setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        titleLabel = new JLabel("FlightHub");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        panelLabel = new JLabel("Choose your panel");
        panelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        passengerButton = new JButton("Passenger");
        employeeButton = new JButton("Employee");

        passengerButton.setPreferredSize(new Dimension(300, 70));
        employeeButton.setPreferredSize(new Dimension(300, 70));

        passengerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                passengerFunctionalityView = new PassengerFunctionalityView(session);
                passengerFunctionalityView.setVisible(true);
                UserNavigationView.this.setVisible(false);
            }
        });

        employeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(UserNavigationView.this, "Not a part of implementation.", "Not implemented", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        contentPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(panelLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(passengerButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(employeeButton, gbc);

        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        add(contentPanel);

        setVisible(true);
    }

}