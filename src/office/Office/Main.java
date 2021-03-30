package office.Office;

import office.doctor.Doctor;
import office.doctor.FamilyDoctor;
import office.doctor.Nurse;
import office.doctor.Pediatrician;

public class Main {
    public static void main(String[] args) {
        Doctor d1 = new Nurse("Nume", "Prenume", "email", 1980, 2011, 50);
        Doctor d2 = new FamilyDoctor("Nume", "Prenume", "email", 1970, 2009, 212);
        Doctor d3 = new Pediatrician("Nume", "Prenume", "email", 1972, 2009, 200);

        System.out.println(d1.computeSalary());
        System.out.println(d2.computeSalary());
        System.out.println(d3.computeSalary());
    }
}
