import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import views.FlightSearchView;
import views.UserNavigationView;

import javax.swing.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        StandardServiceRegistry registry = null;
        SessionFactory sessionFactory = null;

        try {
            registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();

            Session session = sessionFactory.openSession();

            addDataToDb(session);

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    //new FlightSearchView(session); //Skip navigation
                    new UserNavigationView(session);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }

    }

    static void addDataToDb(Session session) throws Exception {

        session.beginTransaction();

        //WAW-PAR
        Flight f1 = new Flight("LOT 333", LocalDate.of(2023, 7, 7), LocalTime.of(8, 0), Duration.ofHours(2), 200);
        Flight f2 = new Flight("AFR 1147", LocalDate.of(2023, 7, 10), LocalTime.of(12, 30), Duration.ofHours(2), 250);
        Flight f3 = new Flight("LOT 333", LocalDate.of(2023, 7, 14), LocalTime.of(8, 0), Duration.ofHours(2), 200);
        Flight f4 = new Flight("AFR 1147", LocalDate.of(2023, 7, 17), LocalTime.of(12, 30), Duration.ofHours(2), 250);

        //PAR-WAW
        Flight f5 = new Flight("LOT 336", LocalDate.of(2023, 7, 8), LocalTime.of(22, 0), Duration.ofHours(2), 200);
        Flight f6 = new Flight("AFR 1246", LocalDate.of(2023, 7, 11), LocalTime.of(8, 30), Duration.ofHours(2), 250);
        Flight f7 = new Flight("LOT 336", LocalDate.of(2023, 7, 15), LocalTime.of(22, 0), Duration.ofHours(2), 200);
        Flight f8 = new Flight("AFR 1246", LocalDate.of(2023, 7, 18), LocalTime.of(8, 30), Duration.ofHours(2), 250);

        //KRA-LYO
        Flight f9 = new Flight("AFR 7351 ", LocalDate.of(2023, 7, 21), LocalTime.of(9, 15), Duration.ofHours(3).plusMinutes(15), 180);

        //LYO-KRA
        Flight f10 = new Flight("AFR 7361", LocalDate.of(2023, 7, 21), LocalTime.of(15, 30), Duration.ofHours(2).plusMinutes(15), 180);

        //WAW-FRA
        Flight f11 = new Flight("DLH 1351", LocalDate.of(2023, 7, 25), LocalTime.of(14,10), Duration.ofHours(2), 250);
        //FRA-WAW
        Flight f12 = new Flight("DLH 1352", LocalDate.of(2023, 7, 25), LocalTime.of(20,0), Duration.ofHours(2), 250);

        //WAW-MUN
        Flight f13 = new Flight("LOT 375", LocalDate.of(2023, 7, 14), LocalTime.of(12,30), Duration.ofHours(1).plusMinutes(30), 250);
        //MUN-WAW
        Flight f14 = new Flight("LOT 354", LocalDate.of(2023, 7, 15), LocalTime.of(17,30), Duration.ofHours(1).plusMinutes(30), 250);

        //FRA-PAR
        Flight f15 = new Flight("DLH 1052", LocalDate.of(2023, 7, 12), LocalTime.of(22,30), Duration.ofHours(1).plusMinutes(45), 300);
        Flight f16 = new Flight("DLH 1047", LocalDate.of(2023, 7, 13), LocalTime.of(4,30), Duration.ofHours(1).plusMinutes(45), 300);

        //FRA-MUN
        Flight f17 = new Flight("DLH 123", LocalDate.of(2023, 7, 18), LocalTime.of(13,30), Duration.ofHours(1), 90);
        Flight f18 = new Flight("DLH 122", LocalDate.of(2023, 7, 18), LocalTime.of(16,30), Duration.ofHours(1), 90);

        //GDA-MUN
        Flight f19 = new Flight("LOT 3832", LocalDate.of(2023, 7, 2), LocalTime.of(11,30), Duration.ofHours(1).plusMinutes(30), 150);
        Flight f20 = new Flight("LOT 3835", LocalDate.of(2023, 7, 3), LocalTime.of(11,30), Duration.ofHours(1).plusMinutes(30), 150);

        //Airports
        Airport airport_chopin = new Airport("EPWA", "Poland", "Warsaw", "Warsaw Chopin Airport");
        Airport airport_jp2 = new Airport("EPKK", "Poland", "Kraków", "John Paul II International Airport");
        Airport airport_lw = new Airport("EPGD", "Poland", "Gdańsk", "Gdańsk Lech Wałęsa Airport");

        Airport airport_cdg = new Airport("LFPG", "France", "Paris", "Paris Charles De Gaulle Airport");
        Airport airport_lyon = new Airport("LFLL", "France", "Lyon", "Lyon Airport Saint-Exupery");

        Airport airport_frankfurt = new Airport("EDDF", "Germany", "Frankfurt", "Frankfurt am Main");
        Airport airport_munich = new Airport("EDDM", "Germany", "Munich", "Munich Airport");

        //Aircrafts
        Aircraft ac1 = new Aircraft("ABC123", "Airbus", "A320", Year.of(2015), 180, 5000);
        Aircraft ac2 = new Aircraft("DEF456", "Boeing", "737", Year.of(2012), 200, 4500);
        Aircraft ac3 = new Aircraft("MNO345", "Airbus", "A330", Year.of(2018), 250, 7000);
        Aircraft ac4 = new Aircraft("PQR678", "Boeing", "787", Year.of(2017), 300, 8000);
        Aircraft ac5 = new Aircraft("BCD890", "Airbus", "A350", Year.of(2019), 350, 9000);
        Aircraft ac6 = new Aircraft("XYZ987", "Airbus", "A320", Year.of(2013), 170, 5000);
        Aircraft ac7 = new Aircraft("JKL012", "Cessna", "172", Year.of(2010), 4, 800);
        Aircraft ac8 = new Aircraft("VWX234", "Embraer", "E195", Year.of(2016), 120, 3500);

        //Airlines
        Airline airline_lot = new Airline("PLL Lot", "Poland", Year.of(1928));
        Airline airline_af = new Airline("Air France", "France", Year.of(1933));
        Airline airline_lh = new Airline("Lufthansa", "Germany", Year.of(1953));

        //Passengers
        Passenger p1 = new Passenger("Jan", "Kowalski", 45, "Poland", "jankowalski@gmail.com", "WA 2011232");
        Passenger p2 = new Passenger("Anna", "Nowak", 32, "Poland", "annanowak@gmail.com", "WA 3847562");
        Passenger p3 = new Passenger("Michael", "Schmidt", 50, "Germany", "michaelschmidt@gmail.com", "DE 9276415");
        Passenger p4 = new Passenger("Eva", "Müller", 41, "Germany", "evamuller@gmail.com", "DE 7492801");
        Passenger p5 = new Passenger("Sophie", "Lefebvre", 28, "France", "sophielefebvre@gmail.com", "FR 1034576");
        Passenger p6 = new Passenger("Lucas", "Dubois", 34, "France", "lucasdubois@gmail.com", "FR 8250174");
        Passenger p7 = new Passenger("David", "Smith", 37, "United Kingdom", "davidsmith@gmail.com", "UK 5928374");
        Passenger p8 = new Passenger("Oliver", "Jones", 29, "United Kingdom", "oliverjones@gmail.com", "UK 3157902");

        //Tickets
        Ticket t1 = new Ticket(Ticket.TicketStatus.Paid, LocalDate.now(), false, p3, f18);
        Ticket t2 = new Ticket(Ticket.TicketStatus.Booked, LocalDate.now(), false, p4, f18);
        Ticket t3 = new Ticket(Ticket.TicketStatus.Cancelled, LocalDate.of(2023, 6, 20), false, p8, f18);

        //Important for presenting ^
        //mess v

        Ticket t4 = new Ticket(Ticket.TicketStatus.Booked, LocalDate.of(2023, 6, 21), false, p6, f4);
        Ticket t5 = new Ticket(Ticket.TicketStatus.Cancelled, LocalDate.of(2023, 5, 25), false, p7, f11);
        Ticket t6 = new Ticket(Ticket.TicketStatus.Cancelled, LocalDate.of(2023, 5, 25), false, p7, f12);
        Ticket t7 = new Ticket(Ticket.TicketStatus.Paid, LocalDate.of(2023, 6, 1), true, p2, f3);
        Ticket t8 = new Ticket(Ticket.TicketStatus.Paid, LocalDate.of(2023, 6, 1), false, p1, f3);
        Ticket t9 = new Ticket(Ticket.TicketStatus.Cancelled, LocalDate.of(2023, 5, 11), true, p1, f1);
        Ticket t10 = new Ticket(Ticket.TicketStatus.Paid, LocalDate.now(), false, p5, f9);
        Ticket t11 = new Ticket(Ticket.TicketStatus.Booked, LocalDate.of(2023, 6, 7), true, p8, f15);
        Ticket t12 = new Ticket(Ticket.TicketStatus.Paid, LocalDate.of(2023, 6, 14), true, p5, f2);

        //Pilots
        Pilot pilot1 = new Pilot("Antoni", "Kowalski", 60, "Poland", LocalDate.of(1990, 12, 12), 8000.0d, LocalDate.of(2024, 1, 14), true);
        Pilot pilot2 = new Pilot("Jerzy", "Malinowski", 39, "Poland", LocalDate.of(2012, 3, 15), 2500.0d, LocalDate.of(2023, 10, 14), false);
        Pilot pilot3 = new Pilot("Hans", "Wagner", 26, "Germany", LocalDate.of(2018, 3, 6), 1400.0d, LocalDate.of(2025, 03, 01), true);

        //Cabin Crew
        List<String> tempLangList1 = List.of("Polish", "English", "French");
        List<String> tempLangList2 = List.of("English");
        List<String> tempLangList3 = List.of("French", "English");
        CabinCrew cc1 = new CabinCrew("Joanna", "Kowalska", 27, "Poland", LocalDate.of(2017, 10, 8), LocalDate.of(2023, 8, 27), tempLangList1);
        CabinCrew cc2 = new CabinCrew("Charlotte", "Davis", 45, "United Kingdom", LocalDate.of(2010, 4, 10), LocalDate.of(2024, 1, 10), tempLangList2);
        CabinCrew cc3 = new CabinCrew("Camille", "Dupont", 31, "France", LocalDate.of(2015, 9, 14), LocalDate.of(2023, 12, 30), tempLangList3);

        //CarryOn luggage
        CarryOnLuggage cl1 = new CarryOnLuggage(7.5, CarryOnLuggage.LuggageType.Backpack);
        CarryOnLuggage cl2 = new CarryOnLuggage(8.2, CarryOnLuggage.LuggageType.Suitcase);
        CarryOnLuggage cl3 = new CarryOnLuggage(9.7, CarryOnLuggage.LuggageType.Bag);

        //Registered Luggage
        RegisteredLuggage rl1 = new RegisteredLuggage(25.5, false);
        RegisteredLuggage rl2 = new RegisteredLuggage(28.0, true);
        RegisteredLuggage rl3 = new RegisteredLuggage(19.2, false);

        //Add Airports to Flights
        //WAW-PAR
        f1.addDepartureAirport(airport_chopin);
        f1.addArrivalAirport(airport_cdg);
        f2.addDepartureAirport(airport_chopin);
        f2.addArrivalAirport(airport_cdg);
        f3.addDepartureAirport(airport_chopin);
        f3.addArrivalAirport(airport_cdg);
        f4.addDepartureAirport(airport_chopin);
        f4.addArrivalAirport(airport_cdg);

        f5.addDepartureAirport(airport_cdg);
        f5.addArrivalAirport(airport_chopin);
        f6.addDepartureAirport(airport_cdg);
        f6.addArrivalAirport(airport_chopin);
        f7.addDepartureAirport(airport_cdg);
        f7.addArrivalAirport(airport_chopin);
        f8.addDepartureAirport(airport_cdg);
        f8.addArrivalAirport(airport_chopin);

        //KRK-LYO
        f9.addDepartureAirport(airport_jp2);
        f9.addArrivalAirport(airport_lyon);
        f10.addDepartureAirport(airport_lyon);
        f10.addArrivalAirport(airport_jp2);

        //WAW-FRK
        f11.addDepartureAirport(airport_chopin);
        f11.addArrivalAirport(airport_frankfurt);
        f12.addDepartureAirport(airport_frankfurt);
        f12.addArrivalAirport(airport_chopin);

        //WAW-MUN
        f13.addDepartureAirport(airport_chopin);
        f13.addArrivalAirport(airport_munich);
        f14.addDepartureAirport(airport_munich);
        f14.addArrivalAirport(airport_chopin);

        //FRA-PAR
        f15.addDepartureAirport(airport_frankfurt);
        f15.addArrivalAirport(airport_cdg);
        f16.addDepartureAirport(airport_cdg);
        f16.addArrivalAirport(airport_frankfurt);

        //FRA-MUN
        f17.addDepartureAirport(airport_frankfurt);
        f17.addArrivalAirport(airport_munich);
        f18.addDepartureAirport(airport_munich);
        f18.addArrivalAirport(airport_frankfurt);

        //GDA-MUN
        f19.addDepartureAirport(airport_lw);
        f19.addArrivalAirport(airport_munich);
        f20.addDepartureAirport(airport_munich);
        f20.addArrivalAirport(airport_lw);

        //Add Aircrafts to Airlines
        f1.addAircraft(ac1);
        f3.addAircraft(ac1);
        f5.addAircraft(ac1);
        f7.addAircraft(ac1);

        f2.addAircraft(ac2);
        f4.addAircraft(ac2);
        f6.addAircraft(ac2);
        f8.addAircraft(ac2);

        f9.addAircraft(ac3);
        f10.addAircraft(ac3);

        f11.addAircraft(ac4);
        f12.addAircraft(ac4);

        f13.addAircraft(ac5);
        f14.addAircraft(ac5);

        f15.addAircraft(ac6);
        f16.addAircraft(ac6);

        f17.addAircraft(ac7);
        f18.addAircraft(ac7);

        f19.addAircraft(ac8);
        f20.addAircraft(ac8);

        //Add Aircrafts to Flights
        ac1.addAircraftToAirline(airline_lot);
        ac2.addAircraftToAirline(airline_af);
        ac3.addAircraftToAirline(airline_af);
        ac4.addAircraftToAirline(airline_lh);
        ac5.addAircraftToAirline(airline_lot);
        ac6.addAircraftToAirline(airline_lh);
        ac7.addAircraftToAirline(airline_lh);
        ac8.addAircraftToAirline(airline_lot);

        //Add Pilots to Flights
        f1.addPilot(pilot1); // test subset -> comment this one out
        f1.addPilot(pilot2);
        f1.addCaptain(pilot1);

        f3.addPilot(pilot1);
        f3.addPilot(pilot2);
        f3.addCaptain(pilot1);

        f5.addPilot(pilot1);
        f5.addPilot(pilot2);
        f5.addCaptain(pilot1);

        f7.addPilot(pilot1);
        f7.addPilot(pilot2);
        f7.addCaptain(pilot1);

        f17.addPilot(pilot3);
        f17.addCaptain(pilot3);
        f18.addPilot(pilot3);
        f18.addCaptain(pilot3);

        //Add cabin crew to Flights
        f1.addCabinCrew(cc1);
        f3.addCabinCrew(cc1);
        f5.addCabinCrew(cc1);
        f7.addCabinCrew(cc1);

        f2.addCabinCrew(cc3);
        f4.addCabinCrew(cc3);
        f6.addCabinCrew(cc3);
        f8.addCabinCrew(cc3);

        f11.addCabinCrew(cc2);
        f12.addCabinCrew(cc2);

        //Add luggage to passengers
        p1.setCarryOnLuggage(cl1);
        p1.setRegisteredLuggage(rl1);
        p2.setRegisteredLuggage(rl2);
        p3.setCarryOnLuggage(cl2);
        p4.setCarryOnLuggage(cl3);
        p8.setRegisteredLuggage(rl3);

        session.save(f1);
        session.save(f2);
        session.save(f3);
        session.save(f4);
        session.save(f5);
        session.save(f6);
        session.save(f7);
        session.save(f8);
        session.save(f9);
        session.save(f10);
        session.save(f11);
        session.save(f12);
        session.save(f13);
        session.save(f14);
        session.save(f15);
        session.save(f16);
        session.save(f17);
        session.save(f18);
        session.save(f19);
        session.save(f20);

        session.save(ac1);
        session.save(ac2);
        session.save(ac3);
        session.save(ac4);
        session.save(ac5);
        session.save(ac6);
        session.save(ac7);
        session.save(ac8);

        session.save(airline_lot);
        session.save(airline_lh);
        session.save(airline_af);

        session.save(airport_chopin);
        session.save(airport_jp2);
        session.save(airport_lw);
        session.save(airport_cdg);
        session.save(airport_lyon);
        session.save(airport_frankfurt);
        session.save(airport_munich);

        session.save(p1);
        session.save(p2);
        session.save(p3);
        session.save(p4);
        session.save(p5);
        session.save(p6);
        session.save(p7);
        session.save(p8);

        session.save(t1);
        session.save(t2);
        session.save(t3);
        session.save(t4);
        session.save(t5);
        session.save(t6);
        session.save(t7);
        session.save(t8);
        session.save(t9);
        session.save(t10);
        session.save(t11);
        session.save(t12);

        session.save(pilot1);
        session.save(pilot2);
        session.save(pilot3);

        session.save(cc1);
        session.save(cc2);
        session.save(cc3);

        session.save(cl1);
        session.save(cl2);
        session.save(cl3);

        session.save(rl1);
        session.save(rl2);
        session.save(rl3);

        session.getTransaction().commit();
    }

}