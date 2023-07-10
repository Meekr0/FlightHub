package views;

import jakarta.persistence.TypedQuery;
import model.Flight;
import model.Passenger;
import model.Ticket;
import org.hibernate.Session;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

public class TicketBookingView extends JFrame {
    private int ticketCount;
    private Flight selectedFlight;
    private JTable passengerTable;
    private Session session;
    private JFrame flightSearchViewFrame;
    public TicketBookingView(int ticketCount, Flight selectedFlight, Session session) {

        this.ticketCount = ticketCount;
        this.selectedFlight = selectedFlight;
        this.session = session;

        setTitle("Passenger Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1600, 800));
        setPreferredSize(new Dimension(1600, 800));
        this.setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Input Passenger Information below");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        //Disable editing for the # column
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        tableModel.addColumn("#");
        tableModel.addColumn("Passport Number");
        tableModel.addColumn("First Name");
        tableModel.addColumn("Last Name");
        tableModel.addColumn("Age");
        tableModel.addColumn("Country of Birth");
        tableModel.addColumn("Email");

        for (int i = 0; i < ticketCount; i++) {
            tableModel.addRow(new Vector());
        }

        passengerTable = new JTable(tableModel);
        passengerTable.setFillsViewportHeight(true);
        passengerTable.setPreferredScrollableViewportSize(new Dimension(1200, 400));
        passengerTable.setIntercellSpacing(new Dimension(0, 0));
        passengerTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(passengerTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton submitButton = new JButton("Book Tickets");
        submitButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setRowNumberColumn();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < passengerTable.getRowCount(); i++)
                    for(int j = 0; j < passengerTable.getColumnCount(); j++) {
                        var value = tableModel.getValueAt(i, j);
                        if(value == null || value.toString().isEmpty()) {
                            JOptionPane.showMessageDialog(TicketBookingView.this, "Not all fields have been filled.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                int tempTicketDiscounted = 0;
                //All fields filled
                for(int i = 0; i < passengerTable.getRowCount(); i++) {

                    String tempPassportNum = passengerTable.getValueAt(i, 1).toString();
                    TypedQuery<Passenger> query = session.createQuery("FROM Passenger WHERE passportNum = :passportNumber");
                    query.setParameter("passportNumber", tempPassportNum);

                    List<Passenger> tempList = query.getResultList();

                    session.beginTransaction();

                    if(!tempList.isEmpty()) { //If passenger already registered

                        Passenger passenger = tempList.get(0);
                        try {
                            Ticket ticket = new Ticket(Ticket.TicketStatus.Booked, LocalDate.now(), false, passenger, selectedFlight); //TODO - Move calculating price to constructor?
                            ticket.calculateFinalTicketPrice(selectedFlight.getBaseTicketPrice());
                            session.save(ticket);
                            session.getTransaction().commit();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else { //If new passenger

                        tempTicketDiscounted++;

                        String fName = passengerTable.getValueAt(i, 2).toString();
                        String lName = passengerTable.getValueAt(i, 3).toString();
                        int age = Integer.parseInt(passengerTable.getValueAt(i, 4).toString());
                        String countryOfBirth = passengerTable.getValueAt(i, 5).toString();
                        String email = passengerTable.getValueAt(i, 6).toString();

                        Passenger passenger = new Passenger(fName, lName, age, countryOfBirth, email, tempPassportNum);
                        try {
                            Ticket ticket = new Ticket(Ticket.TicketStatus.Booked, LocalDate.now(), true, passenger, selectedFlight);
                            ticket.calculateFinalTicketPrice(selectedFlight.getBaseTicketPrice());
                            session.save(ticket);
                            session.save(passenger);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        session.getTransaction().commit();
                    }
                }

                String confirmationMsg = "Successful reservation\nTickets bought: " + ticketCount + "\nTickets discounted: " + tempTicketDiscounted+"\n";
                confirmationMsg += "List of tickets after these reservations:\n";
                for(Ticket t : selectedFlight.getTickets()) {
                    confirmationMsg += (t.toString() + "\n");
                }
                JOptionPane.showMessageDialog(TicketBookingView.this, confirmationMsg, "Success", JOptionPane.INFORMATION_MESSAGE);
                new UserNavigationView(session).setVisible(true);
                TicketBookingView.this.setVisible(false);

            }
        });

    }

    private void setRowNumberColumn() {
        for (int i = 0; i < passengerTable.getRowCount(); i++) {
            passengerTable.setValueAt(i + 1, i, 0);
        }
    }

    public void setFlightSearchViewFrame(JFrame flightSearchViewFrame) {
        this.flightSearchViewFrame = flightSearchViewFrame;
    }
}