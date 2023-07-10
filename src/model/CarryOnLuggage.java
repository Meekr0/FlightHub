package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;

@Entity(name = "CarryOnLuggage")
public class CarryOnLuggage extends Luggage {
    public enum LuggageType { Backpack, Bag, Suitcase}
    static double MAX_WEIGHT= 10.0d;
    @Enumerated
    private LuggageType luggageType;
    @OneToOne(mappedBy = "carryOnLuggage")
    private Passenger passenger;

    public CarryOnLuggage() {}
    public CarryOnLuggage(double weight, LuggageType luggageType) {
        super(weight);
        this.luggageType = luggageType;
    }
    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

}
