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
import java.util.Scanner;

public class Main {
    public static void Menu() {
        System.out.println("Hi, let's manage together my clinic!");
        System.out.println("We start from scratch...");
        System.out.println("Here are some commands you can use in order to lead this clinic well, I'll do the rest of the work.");
        System.out.println("1. Hire a doctor");
        System.out.println("2. Register a patient");
        System.out.println("3. Make an appointment");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        Menu();
        System.out.print("Command:");
        int opt = scanner.nextInt();
        while (opt != 0) {
            if (opt == 1)
                Service.addDoctor();
            else if (opt == 2)
                Service.addPatient();
            else if (opt == 3)
                Service.makeAppointment();
            System.out.print("Command:");
            opt = scanner.nextInt();
        }
    }
}
