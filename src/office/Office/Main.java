package office.Office;

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
        Service service = Service.getInstance();
        Menu();
        System.out.print("Command:");
        int opt = scanner.nextInt();
        while (opt != 0) {
            switch (opt) {
                case 1:
                    service.addDoctor();
                case 2:
                    service.editDoctor();
                case 3:
                    service.deleteDoctor();
                case 4:
                    service.displayDoctors();
                case 5:
                    service.addPatient();
                case 6:
                    service.editPatient();
                case 7:
                    service.deletePatient();
                case 8:
                    service.displayPatients();
                case 9:
                    service.makeAppointment();
                case 10:
                    service.goToAppointment();
                case 11:
                    service.editAppointment();
                case 12:
                    service.deleteAppointment();
                case 13:
                    service.displayAppointments();
            }

            System.out.print("Command:");
            opt = scanner.nextInt();
        }
    }
}
