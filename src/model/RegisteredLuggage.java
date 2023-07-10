package model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity(name = "RegisteredLuggage")
public class RegisteredLuggage extends Luggage {
    static double MAX_WEIGHT= 30.0d;
    private boolean hasAnimals;
    @OneToOne(mappedBy = "registeredLuggage")
    private Passenger passenger;
    public RegisteredLuggage() {}
    public RegisteredLuggage(double weight, boolean hasAnimals) {
        super(weight);
        this.hasAnimals = hasAnimals;
    }
    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
