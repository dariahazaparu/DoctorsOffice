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
        System.out.println("0. Exit");
        System.out.println("1. Hire a doctor");
        System.out.println("2. Edit doctor");
        System.out.println("3. Fire a doctor");
        System.out.println("4. Display doctors");
        System.out.println("5. Register a patient");
        System.out.println("6. Edit patient");
        System.out.println("7. Delete a patient");
        System.out.println("8. Display patients");
        System.out.println("9. Make an appointment");
        System.out.println("10. Go to appointment");
        System.out.println("11. Edit appointment");
        System.out.println("12. Delete appointment");
        System.out.println("13. Display appointments");
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
                Service.editDoctor();
            else if (opt == 3)
                Service.deleteDoctor();
            else if (opt == 4)
                Service.displayDoctors();
            else if (opt == 5)
                Service.addPatient();
            else if (opt == 6)
                Service.editPatient();
            else if (opt == 7)
                Service.deletePatient();
            else if (opt == 8)
                Service.displayPatients();
            else if (opt == 9)
                Service.makeAppointment();
            else if (opt == 10)
                Service.goToAppointment();
            else if (opt == 11)
                Service.editAppointment();
            else if (opt == 12)
                Service.deleteAppointment();
            else if (opt == 13)
                Service.displayAppointments();
            System.out.print("Command:");
            opt = scanner.nextInt();
        }
    }
}
