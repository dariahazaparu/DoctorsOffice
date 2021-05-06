package office.Office;

import office.Office.IO.CSVReadService;
import office.Office.IO.CSVWriteService;
import office.Office.service.AppointmentsService;
import office.Office.service.DoctorService;
import office.Office.service.PatientService;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void Menu() {
        System.out.println("Hi, let's manage together my clinic!");
        System.out.println("It's full of doctors here. And a lot of patients registered.");
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
        System.out.println("12. Cancel appointment");
        System.out.println("13. Display appointments");
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        AppointmentsService appointments = AppointmentsService.getInstance();
        DoctorService doctors = DoctorService.getInstance();
        PatientService patients = PatientService.getInstance();

        CSVReadService read = CSVReadService.getInstance();
        CSVWriteService write = CSVWriteService.getInstance();

        read.readNurse();
        read.readFamilyDoctor();
        read.readPediatrician();
        read.readAdult();
        read.readChild();
        read.readAppointment();

        Menu();
        System.out.print("Command:");
        int opt = scanner.nextInt();
        while (opt != 0) {
            switch (opt) {
                case 1:
                    doctors.addDoctor();
                    break;
                case 2:
                    doctors.editDoctor();
                    break;
                case 3:
                    doctors.deleteDoctor();
                    break;
                case 4:
                    doctors.displayDoctors();
                    break;
                case 5:
                    patients.addPatient();
                    break;
                case 6:
                    patients.editPatient();
                    break;
                case 7:
                    patients.deletePatient();
                    break;
                case 8:
                    patients.displayPatients();
                    break;
                case 9:
                    appointments.makeAppointment();
                    break;
                case 10:
                    appointments.goToAppointment();
                    break;
                case 11:
                    appointments.editAppointment();
                    break;
                case 12:
                    appointments.deleteAppointment();
                    break;
                case 13:
                    appointments.displayAppointments();
                    break;
            }

            System.out.print("Command:");
            opt = scanner.nextInt();
        }

        write.writeNurse();
        write.writePediatrician();
        write.writeFamilyDoctor();
        write.writeAdult();
        write.writeChild();
    }
}
