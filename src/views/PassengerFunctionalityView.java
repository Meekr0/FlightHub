package views;

import model.Passenger;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PassengerFunctionalityView extends JFrame {
    private JLabel titleLabel;
    private JLabel actionLabel;
    private JButton browseFlightsButton;
    private JButton bookTicketButton;
    private JButton buyTicketButton;
    private JButton viewTicketsButton;
    private Session session;
    private FlightSearchView flightSearchView;

    public PassengerFunctionalityView(Session session) {

        this.session = session;

        setTitle("Passenger Panel");
        setMinimumSize(new Dimension(800, 600));
        setSize(new Dimension(1000, 750));
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        titleLabel = new JLabel("Passenger Panel");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        actionLabel = new JLabel("Choose your action");
        actionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        actionLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        browseFlightsButton = new JButton("Browse flights");
        bookTicketButton = new JButton("Book a ticket");
        buyTicketButton = new JButton("Buy a ticket");
        viewTicketsButton = new JButton("View your tickets");

        Dimension buttonSize = new Dimension(250, 70);
        browseFlightsButton.setPreferredSize(buttonSize);
        bookTicketButton.setPreferredSize(buttonSize);
        buyTicketButton.setPreferredSize(buttonSize);
        viewTicketsButton.setPreferredSize(buttonSize);

        browseFlightsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PassengerFunctionalityView.this, "Not a part of implementation.", "Not implemented", JOptionPane.ERROR_MESSAGE);
            }
        });

        bookTicketButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                flightSearchView = new FlightSearchView(session);
                PassengerFunctionalityView.this.setVisible(false);
            }
        });

        buyTicketButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PassengerFunctionalityView.this, "Not a part of implementation.", "Not implemented", JOptionPane.ERROR_MESSAGE);
            }
        });

        viewTicketsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(PassengerFunctionalityView.this, "Not a part of implementation.", "Not implemented", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        contentPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(actionLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        buttonPanel.add(browseFlightsButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(bookTicketButton, gbc);

        gbc.gridy = 2;
        buttonPanel.add(buyTicketButton, gbc);

        gbc.gridy = 3;
        buttonPanel.add(viewTicketsButton, gbc);

        centerPanel.add(buttonPanel, BorderLayout.CENTER);
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        add(contentPanel);

        setVisible(true);
    }
}