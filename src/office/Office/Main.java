package office.Office;

import office.Appointment.Appointment;
import office.doctor.Doctor;
import office.doctor.FamilyDoctor;
import office.doctor.Nurse;
import office.doctor.Pediatrician;
import office.pacient.Adult;
import office.pacient.Child;
import office.pacient.Patient;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Doctor d1 = new Nurse("Nume", "Prenume", "email", 1980, 2011, 50);
        Doctor d2 = new FamilyDoctor("Nume", "Prenume", "email", 1970, 2009, 212);
        Doctor d3 = new Pediatrician("Nume", "Prenume", "email", 1972, 2009, 200);

        System.out.println(d1.computeSalary());
        System.out.println(d2.computeSalary());
        System.out.println(d3.computeSalary());

        Patient p1 = new Adult("Nume", "Prenume", 1981, "CNP", "000 0000 000", true );
        Patient p2 = new Child("Nume", "Prenume", 1981, "CNP", "000 0000 000", "Parinte" );

        p1.displayPatient();
        p2.displayPatient();

        Appointment a1 = new Appointment(p1, d1, LocalDateTime.now());
        a1.displayAppointment();

    }
}
