// https://stackabuse.com/reading-and-writing-csvs-in-java/
package office.Office.IO;

import office.Appointment.Appointment;
import office.Office.service.AppointmentsService;
import office.Office.service.AuditService;
import office.Office.service.DoctorService;
import office.Office.service.PatientService;
import office.doctor.FamilyDoctor;
import office.doctor.Nurse;
import office.doctor.Pediatrician;
import office.patient.Adult;
import office.patient.Child;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVWriteService {
    public static CSVWriteService serviceInstance = null;

    private CSVWriteService() {}

    public static CSVWriteService getInstance() {
        if (serviceInstance == null) {
            serviceInstance = new CSVWriteService();
        }

        return serviceInstance;
    }

    public void writeNurse() {
        DoctorService doctorService = DoctorService.getInstance();

        try {
            FileWriter csvWriter = new FileWriter("./resources/output/nurse.csv");
            csvWriter.write("First name,Last name,Email,Hire year,Hours\n");
            ArrayList<Nurse> nurses = doctorService.getNurses();

            for (var nurse: nurses)
                csvWriter.write(nurse.getFirstName() + "," + nurse.getLastName() + "," + nurse.getEmail() + "," + nurse.getHireYear() + "," + nurse.getHours() + '\n');
            csvWriter.close();
        } catch (IOException err) {
            System.out.println("Failed to write nurses into CSV file.");
        }

        AuditService audit = AuditService.getInstance();
        audit.print("CSV write nurses");
    }

    public void writePediatrician() {
        DoctorService doctorService = DoctorService.getInstance();

        try {
            FileWriter csvWriter = new FileWriter("./resources/output/pediatrician.csv");
            csvWriter.write("First name,Last name,Email,Hire year,Bonus\n");
            ArrayList<Pediatrician> peds = doctorService.getPediatricians();

            for (var ped: peds)
                csvWriter.write(ped.getFirstName() + "," + ped.getLastName() + "," + ped.getEmail() + "," + ped.getHireYear() + "," + ped.getBonus() + '\n');
            csvWriter.close();
        } catch (IOException err) {
            System.out.println("Failed to write pediatricians into CSV file.");
        }

        AuditService audit = AuditService.getInstance();
        audit.print("CSV write pediatricians");
    }

    public void writeFamilyDoctor() {
        DoctorService doctorService = DoctorService.getInstance();

        try {
            FileWriter csvWriter = new FileWriter("./resources/output/familydoctor.csv");
            csvWriter.write("First name,Last name,Email,Hire year,No of families\n");
            ArrayList<FamilyDoctor> fam = doctorService.getFamilyDoctors();

            for (var doc: fam)
                csvWriter.write(doc.getFirstName() + "," + doc.getLastName() + "," + doc.getEmail() + "," + doc.getHireYear() + "," + doc.getNoOfFamilies() + '\n');
            csvWriter.close();
        } catch (IOException err) {
            System.out.println("Failed to write family doctors into CSV file.");
        }

        AuditService audit = AuditService.getInstance();
        audit.print("CSV write family doctors");
    }

    public void writeAdult() {
        PatientService patientService = PatientService.getInstance();

        try {
            FileWriter csvWriter = new FileWriter("./resources/output/adult.csv");
            csvWriter.write("First name,Last name,Birth year,CNP,Tel number,Health insurance\n");
            ArrayList<Adult> adults = patientService.getAdults();

            for (var adult: adults)
                csvWriter.write(adult.getFirstName() + "," + adult.getLastName() + "," + adult.getBirthYear()  + ","
                        + adult.getCNP() + "," + adult.getTel() + "," + adult.isHealthInsurance()  + '\n');
            csvWriter.close();
        } catch (IOException err) {
            System.out.println("Failed to write adults into CSV file.");
        }

        AuditService audit = AuditService.getInstance();
        audit.print("CSV write adults");
    }

    public void writeChild() {
        PatientService patientService = PatientService.getInstance();

        try {
            FileWriter csvWriter = new FileWriter("./resources/output/child.csv");
            csvWriter.write("First name,Last name,Birth year,CNP,Tel number,Parent Name\n");
            ArrayList<Child> children = patientService.getChidren();

            for (var child: children)
                csvWriter.write(child.getFirstName() + "," + child.getLastName() + "," + child.getBirthYear()  + ","
                        + child.getCNP() + "," + child.getTel() + "," + child.getParentName() + '\n');
            csvWriter.close();
        } catch (IOException err) {
            System.out.println("Failed to write children into CSV file.");
        }

        AuditService audit = AuditService.getInstance();
        audit.print("CSV write children");
    }

    public void writeAppointment() {
        AppointmentsService appService = AppointmentsService.getInstance();

        try {
            FileWriter csvWriter = new FileWriter("./resources/output/appointment.csv");
            csvWriter.write("Patient,Doctor,Time of appointment,Status\n");
            ArrayList<Appointment> apps = appService.getAppointments();

            String status;
            for (var app: apps) {
                status = app.isStatus() ? "attended" : "waiting";
                csvWriter.write(app.getPatient().getLastName() + " " + app.getPatient().getFirstName() + "," +
                        app.getDoctor().getLastName() + " " + app.getDoctor().getFirstName() + "," +
                        app.getTimeOfAppointment() + "," + status + '\n');
            }
            csvWriter.close();
        } catch (IOException err) {
            System.out.println("Failed to write appointments into CSV file.");
        }

        AuditService audit = AuditService.getInstance();
        audit.print("CSV write appointments");
    }
}
