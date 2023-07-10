package model;

import jakarta.persistence.*;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Airport")
public class Airport {
    @Id
    @Column(unique = true)
    private String ICAO;
    private String country;
    private String city;
    private String name;
    @OneToMany(mappedBy = "departureAirport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> departureFlights;
    @OneToMany(mappedBy = "arrivalAirport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> arrivalFlights;

    public Airport() {}

    public Airport(String ICAO, String country, String city, String name) {
        this.ICAO = ICAO;
        this.country = country;
        this.city = city;
        this.name = name;
        departureFlights = new ArrayList<>();
        arrivalFlights = new ArrayList<>();
    }

    public String getICAO() {
        return ICAO;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public List<Flight> getDepartureFlights() {
        return departureFlights;
    }

    public List<Flight> getArrivalFlights() {
        return arrivalFlights;
    }

    public void addDepartingFlight(Flight flight) {
        departureFlights.add(flight);
    }

    public void addArrivingFlight(Flight flight) {
        arrivalFlights.add(flight);
    }

    public List<Airport> getAirportsByCountry(String country, Session session) {
        TypedQuery<Airport> query = session.createQuery("FROM Airport a WHERE a.country = :country", Airport.class);
        query.setParameter("country", country);
        return query.getResultList();
    }

}
