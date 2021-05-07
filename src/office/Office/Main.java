package office.Office;

import office.Appointment.Appointment;
import office.Office.IO.CSVReadService;
import office.Office.IO.CSVWriteService;
import office.Office.service.AppointmentsService;
import office.Office.service.AuditService;
import office.Office.service.DoctorService;
import office.Office.service.PatientService;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void Menu() {
        System.out.println("Hi, let's manage together my clinic!");
        System.out.println("It's full of doctors here. And a lot of patients with appointments are registered.");
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
        System.out.println("14. Display appointments for a doctor");
        System.out.println("15. Doctors next appointments");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        AppointmentsService appointments = AppointmentsService.getInstance();
        DoctorService doctors = DoctorService.getInstance();
        PatientService patients = PatientService.getInstance();

        CSVReadService read = CSVReadService.getInstance();
        CSVWriteService write = CSVWriteService.getInstance();

        AuditService audit = AuditService.getInstance();

        read.readNurse();
        read.readFamilyDoctor();
        read.readPediatrician();
        read.readAdult();
        read.readChild();
        read.readAppointment();

        Menu();
        int id;
        System.out.print("Command:");
        int opt = scanner.nextInt();
        while (opt != 0) {
            switch (opt) {
                case 1:
                    doctors.addDoctor();
                    break;
                case 2:
                    System.out.print("Doctor ID for editing:");
                    id = scanner.nextInt();
                    doctors.editDoctor(id);
                    break;
                case 3:
                    System.out.print("Doctor ID for deletion:");
                    id = scanner.nextInt();
                    doctors.deleteDoctor(id);
                    break;
                case 4:
                    doctors.displayDoctors();
                    break;
                case 5:
                    patients.addPatient();
                    break;
                case 6:
                    System.out.print("Patient ID for editing:");
                    id = scanner.nextInt();
                    patients.editPatient(id);
                    break;
                case 7:
                    System.out.print("Patient ID for deletion:");
                    id = scanner.nextInt();
                    patients.deletePatient(id);
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
                    System.out.print("Appointment ID for editing:");
                    id = scanner.nextInt();
                    appointments.editAppointment(id);
                    break;
                case 12:
                    System.out.print("Appointment ID for deletion:");
                    id = scanner.nextInt();
                    appointments.deleteAppointment(id);
                    break;
                case 13:
                    appointments.displayAppointments();
                    break;
                case 14:
                    System.out.print("Doctor ID:");
                    id = scanner.nextInt();
                    HashSet<Appointment> apps = appointments.getDoctorsAppointments(id);
                    for (var app: apps) {
                        app.displayAppointment();
                    }
                    break;
                case 15:
                    System.out.print("Doctor ID:");
                    id = scanner.nextInt();
                    Set<Appointment> Apps = appointments.getDoctorsNextAppointments(id);
                    for (var app: Apps) {
                        app.displayAppointment();
                    }
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
        write.writeAppointment();
        audit.close();
    }
}
