package model;

import jakarta.persistence.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Aircraft")
public class Aircraft {
    @Id
    @Column(unique = true)
    private String registrationNumber;
    private String producer;
    private String model;
    private Year productionYear;
    private int seats;
    private int range;
    @ManyToOne
    private Airline airline;
    @OneToMany
    private List<Flight> flights;

    public Aircraft() {}

    public Aircraft(String registrationNumber, String producer, String model, Year productionYear, int seats, int range) {
        this.registrationNumber = registrationNumber;
        this.producer = producer;
        this.model = model;
        this.productionYear = productionYear;
        this.seats = seats;
        this.range = range;
        this.flights = new ArrayList<>();
    }

    public Airline getAirline() {
        return airline;
    }

    public void addAircraftToAirline(Airline newAirline) {
        this.airline = newAirline;
        newAirline.addAircraft(this);
    }

    public int getSeats() {
        return seats;
    }

    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

}
