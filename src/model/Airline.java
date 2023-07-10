package model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Airline")
public class Airline {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    private String name;
    private String countryOfOrigin;
    private Year establishmentYear;

    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Aircraft> aircrafts;

    public Airline() {}

    public Airline(String name, String countryOfOrigin, Year establishmentYear) {
        this.name = name;
        this.countryOfOrigin = countryOfOrigin;
        this.establishmentYear = establishmentYear;
        aircrafts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addAircraft(Aircraft aircraft) {
        this.aircrafts.add(aircraft);
    }

}
