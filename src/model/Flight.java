package model;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Flight")
public class Flight {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    private String flightNum;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private Duration flightTime;
    private double baseTicketPrice;
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;
    @ManyToOne
    private Aircraft aircraft;
    @ManyToOne
    private Airport departureAirport;
    @ManyToOne
    private Airport arrivalAirport;
    @ManyToMany(mappedBy = "flightsAsPilot", cascade = CascadeType.ALL)
    private List<Pilot> pilots;
    @ManyToOne
    private Pilot captain;
    @ManyToMany(mappedBy = "flights", cascade = CascadeType.ALL)
    private List<CabinCrew> cabinCrewList;

    public Flight() {}

    public Flight(String flightNum, LocalDate departureDate, LocalTime departureTime, Duration flightTime, double baseTicketPrice) {
        this.flightNum = flightNum;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.flightTime = flightTime;
        this.baseTicketPrice = baseTicketPrice;
        tickets = new ArrayList<>();
        pilots = new ArrayList<>();
        cabinCrewList = new ArrayList<>();
    }

    public String getFlightNum() {
        return flightNum;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public Duration getFlightTime() {
        return flightTime;
    }

    public double getBaseTicketPrice() {
        return baseTicketPrice;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public Airline getAirlines() {
        return aircraft.getAirline();
    }

    public List<Pilot> getPilots() { return pilots; }

    public Pilot getCaptain() { return captain; }


    public void addPilot(Pilot pilot) {
        if(pilot.getScheduledMedicalAssessmentDate().isAfter(this.departureDate))
            if(!this.pilots.contains(pilot)) {
                pilots.add(pilot);
                pilot.addFlight(this);
            }
    }

    public void addCaptain(Pilot pilot) {
        if(pilots.contains(pilot) && pilot.hasCaptainQualifications()) {
            this.captain = pilot;
            pilot.addFlightAsCaptain(this);
        } else
            System.err.println("This pilot is not assigned to this flight. Assign him first.");
    }

    public void addCabinCrew(CabinCrew cabinCrew) {
        if(cabinCrew.getCertificateExpireDate().isAfter(this.departureDate))
            if(!this.cabinCrewList.contains(cabinCrew)) {
                cabinCrewList.add(cabinCrew);
                cabinCrew.addFlight(this);
            }
    }

    public void addDepartureAirport(Airport airport) {
        this.departureAirport = airport;
        if(!airport.getDepartureFlights().contains(this))
            airport.addDepartingFlight(this);
    }

    public void addArrivalAirport(Airport airport) {
        this.arrivalAirport = airport;
        if(!airport.getArrivalFlights().contains(this))
            airport.addArrivingFlight(this);
    }

    public void addAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
        aircraft.addFlight(this);
    }

    public void addTicket(Ticket ticket) {
        if(!tickets.contains(ticket))
            this.tickets.add(ticket);
    }

    public int calculateFreeSeats() {
        int seats = aircraft.getSeats();

        int seatsTaken = 0;
        if(this.getTickets() == null || this.getTickets().isEmpty())
            return seats;

        for(Ticket t : this.getTickets()) {
            if(t.getTicketStatus() != Ticket.TicketStatus.Cancelled)
                seatsTaken++;
        }
        return seats - seatsTaken;
    }

    public String getDepartureCity() {
        return this.departureAirport.getCity();
    }

    public String getArrivalCity() {
        return this.arrivalAirport.getCity();
    }

    @Override
    public String toString() {
        return this.getFlightNum() + ", " + this.getDepartureDate() + ", " + this.getDepartureTime();
    }

}
