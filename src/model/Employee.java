package model;

import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

@MappedSuperclass
public abstract class Employee extends Person {
    private LocalDate hireDate;

    public Employee() {}
    public Employee(String fName, String lName, int age, String countryOfOrigin, LocalDate hireDate) {
        super(fName, lName, age, countryOfOrigin);
        this.hireDate = hireDate;
    }

}
