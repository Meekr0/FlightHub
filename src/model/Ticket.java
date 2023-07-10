package model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity(name = "Ticket")
public class Ticket {
    public enum TicketStatus { Booked, Paid, Cancelled }
    static final double DISCOUNT = 0.1d;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    private TicketStatus ticketStatus;
    private LocalDate acquireDate;
    private boolean isDiscounted;
    private double finalPrice;

    @ManyToOne
    private Passenger passenger;
    @ManyToOne
    private Flight flight;

    public Ticket() {}

    public Ticket(TicketStatus ticketStatus, LocalDate acquireDate, boolean isDiscounted, Passenger passenger, Flight flight) throws Exception {
        if(passenger == null || flight == null)
            throw new Exception("Passenger and Flight can't be null");
        this.ticketStatus = ticketStatus;
        this.acquireDate = acquireDate;
        this.isDiscounted = isDiscounted;
        this.passenger = passenger;
        this.flight = flight;
        passenger.addTicket(this);
        flight.addTicket(this);
        calculateFinalTicketPrice(flight.getBaseTicketPrice());
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void calculateFinalTicketPrice(double baseTicketPrice) {
        double finalTicketPrice = baseTicketPrice;
        if(isDiscounted)
            finalTicketPrice *= (1 - DISCOUNT);
        setFinalPrice(finalTicketPrice);
    }

    @Override
    public String toString() {
        return "Ticket: passenger = " + this.getPassenger().getPassportNum() + ", flight: " + this.getFlight().getFlightNum() + ", price = " + this.getFinalPrice();
    }


}
