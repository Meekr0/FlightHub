package model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Pilot")
public class Pilot extends Employee {
    private double totalHoursFlown;
    private LocalDate scheduledMedicalAssessmentDate;
    private boolean hasCaptainQualifications;

    @ManyToMany
    private List<Flight> flightsAsPilot;
    @OneToMany(mappedBy = "captain")
    private List<Flight> flightsAsCaptain;

    public Pilot() {}

    public Pilot(String fName, String lName, int age, String countryOfOrigin, LocalDate hireDate, double totalHoursFlown, LocalDate scheduledMedicalAssessmentDate, boolean hasCaptainQualifications) {
        super(fName, lName, age, countryOfOrigin, hireDate);
        this.totalHoursFlown = totalHoursFlown;
        this.scheduledMedicalAssessmentDate = scheduledMedicalAssessmentDate;
        this.hasCaptainQualifications = hasCaptainQualifications;
        flightsAsPilot = new ArrayList<>();
        flightsAsCaptain = new ArrayList<>();
    }

    public LocalDate getScheduledMedicalAssessmentDate() {
        return scheduledMedicalAssessmentDate;
    }

    public boolean hasCaptainQualifications() {
        return hasCaptainQualifications;
    }

    public List<Flight> getFlights() {
        return flightsAsPilot;
    }

    public void addFlight(Flight flight) {
        flightsAsPilot.add(flight);
    }

    public void addFlightAsCaptain(Flight flight) { flightsAsCaptain.add(flight); }

}
