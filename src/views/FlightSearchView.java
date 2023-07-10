package views;

import jakarta.persistence.TypedQuery;
import model.Flight;
import org.hibernate.Session;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class FlightSearchView {
    private JFrame frame;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField dateField1;
    private JTextField dateField2;
    private JButton searchButton;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField ticketCountField;
    private JButton bookButton;
    private JButton incrementButton;
    private JButton decrementButton;
    List<Flight> flights;
    private Session session;
    TicketBookingView ticketBookingView;

    public FlightSearchView(Session session) {
        this.session = session;
        flights = new ArrayList<>();

        frame = new JFrame("Flight Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1600, 800));
        frame.setPreferredSize(new Dimension(1600, 800));
        frame.setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel();

        JLabel label1 = new JLabel("Departure Country:");
        JLabel label2 = new JLabel("Departure City:");
        JLabel label3 = new JLabel("Arrival Country:");
        JLabel label4 = new JLabel("Arrival City:");
        JLabel dateLabel1 = new JLabel("From Date:");
        JLabel dateLabel2 = new JLabel("To Date:");
        JLabel flightsLabel = new JLabel("Flights:");
        JLabel ticketCountLabel = new JLabel("Ticket Count:");

        textField1 = new JTextField(10);
        textField2 = new JTextField(10);
        textField3 = new JTextField(10);
        textField4 = new JTextField(10);

        dateField1 = new JTextField(10);
        dateField2 = new JTextField(10);

        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tableModel.setRowCount(0);

                if(!session.getTransaction().isActive())
                    session.beginTransaction();

                String departureCountry = textField1.getText();
                String departureCity = textField2.getText();
                String arrivalCountry = textField3.getText();
                String arrivalCity = textField4.getText();
                String fromDate = dateField1.getText();
                String toDate = dateField2.getText();

                try {

                    LocalDate fromLocalDate = LocalDate.parse(fromDate);
                    LocalDate toLocalDate = LocalDate.parse(toDate);

                    if(fromLocalDate.isBefore(LocalDate.now()))
                        JOptionPane.showMessageDialog(frame, "invalid date range: must start today or later.");
                    else if(toLocalDate.isBefore(LocalDate.now()))
                        JOptionPane.showMessageDialog(frame, "invalid date range: must end today or later.");
                    else if(fromLocalDate.isAfter(toLocalDate))
                        JOptionPane.showMessageDialog(frame, "invalid date range: end date must not be before start date.");
                    else {

                        String query = "FROM Flight f WHERE f.departureAirport.country = :departureCountry " +
                                "AND f.departureAirport.city = :departureCity " +
                                "AND f.arrivalAirport.country = :arrivalCountry " +
                                "AND f.arrivalAirport.city = :arrivalCity " +
                                "AND f.departureDate >= :fromDate " +
                                "AND f.departureDate <= :toDate";

                        TypedQuery<Flight> flightQuery = session.createQuery(query, Flight.class);
                        flightQuery.setParameter("departureCountry", departureCountry);
                        flightQuery.setParameter("departureCity", departureCity);
                        flightQuery.setParameter("arrivalCountry", arrivalCountry);
                        flightQuery.setParameter("arrivalCity", arrivalCity);
                        flightQuery.setParameter("fromDate", fromLocalDate);
                        flightQuery.setParameter("toDate", toLocalDate);

                        flights = flightQuery.getResultList();

                        if(flights.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "Didn't find any connections. Will look for alternatives now.");

                            query = "FROM Flight f WHERE f.departureAirport.country = :departureCountry " +
                                    "AND f.arrivalAirport.country = :arrivalCountry " +
                                    "AND f.departureDate >= :fromDate " +
                                    "AND f.departureDate <= :toDate";

                            flightQuery = session.createQuery(query, Flight.class);
                            flightQuery.setParameter("departureCountry", departureCountry);
                            flightQuery.setParameter("arrivalCountry", arrivalCountry);
                            flightQuery.setParameter("fromDate", fromLocalDate);
                            flightQuery.setParameter("toDate", toLocalDate);

                            flights = flightQuery.getResultList();

                            if(!flights.isEmpty())
                                for (Flight flight : flights)
                                    addFlight(flight);
                            else
                                JOptionPane.showMessageDialog(frame, "Didn't find any connections between those countries.");
                        } else {
                            for (Flight flight : flights) {
                                addFlight(flight);
                            }
                        }
                    }
                    session.getTransaction().commit();
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid date format. Please enter dates in the format 'YYYY-MM-DD'.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        tableModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 10) {  // column for checkboxes
                    return Boolean.class;
                }
                return super.getColumnClass(column);
            }
        };
        tableModel.setColumnIdentifiers(new Object[] {
                "Flight number",
                "Departure date",
                "Departure time",
                "Departure City",
                "Arrival City",
                "Departure Airport",
                "Arrival Airport",
                "Flight Time",
                "Base Ticket Price",
                "Airlines",
                "Selected"
        });
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(10).setCellRenderer(new CheckBoxRenderer());

        JScrollPane scrollPane = new JScrollPane(table);

        ticketCountField = new JTextField(5);
        ticketCountField.setText("0");
        ticketCountField.setEditable(false);

        bookButton = new JButton("Book Tickets");
        incrementButton = new JButton("+");
        decrementButton = new JButton("-");
        incrementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = Integer.parseInt(ticketCountField.getText());
                value++;
                ticketCountField.setText(Integer.toString(value));
            }
        });
        decrementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = Integer.parseInt(ticketCountField.getText());
                if (value > 0) {
                    value--;
                    ticketCountField.setText(Integer.toString(value));
                }
            }
        });

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table.getSelectedRow();
                if(selectedRow != -1) {
                    Flight selectedFlight = flights.get(selectedRow);
                    int ticketCount = Integer.parseInt(ticketCountField.getText());

                    if(selectedFlight.getDepartureDate().isBefore(LocalDate.now().plusWeeks(1))) {
                        JOptionPane.showMessageDialog(frame, "You can't book tickets for a flight less than a week away.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if(ticketCount == 0) {
                        JOptionPane.showMessageDialog(frame, "Ticket count must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int freeSeatsOnSelectedFlight = selectedFlight.calculateFreeSeats();
                        if(freeSeatsOnSelectedFlight == 0)
                            JOptionPane.showMessageDialog(frame, "There are no seats left on this flight.", "Error", JOptionPane.ERROR_MESSAGE);
                        else if(freeSeatsOnSelectedFlight < ticketCount)
                            JOptionPane.showMessageDialog(frame, "There are not enough seats left on this flight. Only " + freeSeatsOnSelectedFlight + " seats are available.", "Error", JOptionPane.ERROR_MESSAGE);
                        else {
                            ticketBookingView = new TicketBookingView(ticketCount, selectedFlight, session);
                            ticketBookingView.setVisible(true);
                            ticketBookingView.setFlightSearchViewFrame(frame);
                            frame.setVisible(false);
                        }
                    }
                }
            }
        });

        //Select and unselect checkboxes
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int column = table.getColumnModel().getColumnIndex("Selected");
                if (column != -1 && table.columnAtPoint(e.getPoint()) == column && e.getButton() == MouseEvent.BUTTON1) {
                    int row = table.rowAtPoint(e.getPoint());
                    Boolean currentValue = (Boolean) table.getValueAt(row, column);

                    if (currentValue != null) {
                        if (currentValue)  // if selected
                            table.setValueAt(false, row, column);
                        else { //if not selected
                            for (int i = 0; i < table.getRowCount(); i++)
                                if (i != row)
                                    table.setValueAt(false, i, column);
                            table.setValueAt(true, row, column);
                        }
                    }
                }
            }
        });

        topPanel.add(label1);
        topPanel.add(textField1);
        topPanel.add(label2);
        topPanel.add(textField2);
        topPanel.add(label3);
        topPanel.add(textField3);
        topPanel.add(label4);
        topPanel.add(textField4);
        topPanel.add(dateLabel1);
        topPanel.add(dateField1);
        topPanel.add(dateLabel2);
        topPanel.add(dateField2);
        topPanel.add(searchButton);

        centerPanel.add(flightsLabel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        bottomPanel.add(ticketCountLabel);
        bottomPanel.add(ticketCountField);
        bottomPanel.add(incrementButton);
        bottomPanel.add(decrementButton);
        bottomPanel.add(bookButton);

        topPanel.setLayout(new FlowLayout());
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        bottomPanel.setLayout(new FlowLayout());

        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void addFlight(Flight flight) {

        long totalMin = flight.getFlightTime().toMinutes();
        long hours = totalMin / 60;
        long min = totalMin % 60;

        String flightTime = String.format("%02d:%02d", hours, min);

        Object[] rowData = new Object[] {
                flight.getFlightNum(),
                flight.getDepartureDate(),
                flight.getDepartureTime(),
                flight.getDepartureCity(),
                flight.getArrivalCity(),
                flight.getDepartureAirport().getICAO(),
                flight.getArrivalAirport().getICAO(),
                flightTime,
                flight.getBaseTicketPrice(),
                flight.getAirlines().getName()
        };
        tableModel.addRow(rowData);
        table.setValueAt(false, tableModel.getRowCount() - 1, 10);
    }

    private class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
        public CheckBoxRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            if (value instanceof Boolean) {
                setSelected((Boolean) value);
            } else {
                setSelected(Boolean.FALSE);
            }
            return this;
        }
    }

}