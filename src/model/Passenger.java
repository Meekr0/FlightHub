package model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Passenger")
/*@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "passportNum"),
        indexes = @Index(columnList = "passportNum", unique = true)
)*/
public class Passenger extends Person {
    @Id
    @Column(unique = true)
    private String passportNum;
    private String email;
    @OneToOne(optional = true)
    private CarryOnLuggage carryOnLuggage;
    @OneToOne(optional = true)
    private RegisteredLuggage registeredLuggage;
    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    public Passenger() {}

    public Passenger(String fName, String lName, int age, String countryOfOrigin, String email, String passportNum) {
        super(fName, lName, age, countryOfOrigin);
        this.passportNum = passportNum;
        this.email = email;
        tickets = new ArrayList<>();
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public CarryOnLuggage getCarryOnLuggage() {
        return carryOnLuggage;
    }

    public void setCarryOnLuggage(CarryOnLuggage carryOnLuggage) {
        this.carryOnLuggage = carryOnLuggage;
        carryOnLuggage.setPassenger(this);
    }

    public String getPassportNum() {
        return this.passportNum;
    }

    public RegisteredLuggage getRegisteredLuggage() {
        return registeredLuggage;
    }

    public void setRegisteredLuggage(RegisteredLuggage registeredLuggage) {
        this.registeredLuggage = registeredLuggage;
        registeredLuggage.setPassenger(this);
    }

    public void addTicket(Ticket ticket) {
        if(!tickets.contains(ticket))
            this.tickets.add(ticket);
    }

    @Override
    public String toString() {
        return this.passportNum + ", " + this.getfName() + " " + this.getlName() + ", age = " +
            this.getAge() + ", from " + this.getCountryOfOrigin();
    }

}
