package model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "CabinCrew")
public class CabinCrew extends Employee {
    private LocalDate certificateExpireDate;
    @ElementCollection
    private List<String> knownLanguages;

    @ManyToMany
    private List<Flight> flights;

    public CabinCrew() {}

    public CabinCrew(String fName, String lName, int age, String countryOfOrigin, LocalDate hireDate, LocalDate certificateExpireDate, List<String> knownLanguages) {
        super(fName, lName, age, countryOfOrigin, hireDate);
        this.certificateExpireDate = certificateExpireDate;
        this.knownLanguages = knownLanguages;
        flights = new ArrayList<>();
    }

    public LocalDate getCertificateExpireDate() {
        return certificateExpireDate;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

}
