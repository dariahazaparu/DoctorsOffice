// https://stackabuse.com/reading-and-writing-csvs-in-java/
package office.Office.IO;

import office.Appointment.Appointment;
import office.Office.service.AppointmentsService;
import office.Office.service.AuditService;
import office.Office.service.DoctorService;
import office.Office.service.PatientService;
import office.doctor.Doctor;
import office.doctor.FamilyDoctor;
import office.doctor.Nurse;
import office.doctor.Pediatrician;
import office.patient.Adult;
import office.patient.Child;
import office.patient.Patient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

public class CSVReadService {
    public static CSVReadService serviceInstance = null;

    private CSVReadService() {}

    public static CSVReadService getInstance() {
        if (serviceInstance == null) {
            serviceInstance = new CSVReadService();
        }

        return serviceInstance;
    }

    public void readNurse(){
        DoctorService doctorService = DoctorService.getInstance();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("./resources/input/nurse.csv"));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                Nurse nurse = new Nurse(data[0], data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                doctorService.addDoctor(nurse);
            }
            csvReader.close();
        } catch (IOException err) {
            System.out.println("Failed to read nurses from CSV file.");
        }

//        AuditService audit = AuditService.getInstance();
//        audit.print("CSV read nurses");
    }

    public void readPediatrician() {
        DoctorService doctorService = DoctorService.getInstance();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("./resources/input/pediatrician.csv"));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                Pediatrician peds = new Pediatrician(data[0], data[1], data[2],Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                doctorService.addDoctor(peds);
            }
            csvReader.close();
        } catch (IOException err) {
            System.out.println("Failed to read pediatricians from CSV file.");
        }

//        AuditService audit = AuditService.getInstance();
//        audit.print("CSV read pediatricians");
    }

    public void readFamilyDoctor() {
        DoctorService doctorService = DoctorService.getInstance();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("./resources/input/familydoctor.csv"));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                FamilyDoctor fam = new FamilyDoctor(data[0], data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                doctorService.addDoctor(fam);
            }
            csvReader.close();
        } catch (IOException err) {
            System.out.println("Failed to read family doctors from CSV file.");
        }

//        AuditService audit = AuditService.getInstance();
//        audit.print("CSV read family doctors");
    }

    public void readAdult() {
        PatientService patientService = PatientService.getInstance();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("./resources/input/adult.csv"));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                Adult adult = new Adult(data[1], data[0], Integer.parseInt(data[2]), data[3], data[4], Boolean.parseBoolean(data[5]));
                patientService.addPatient(adult);
            }
            csvReader.close();
        } catch (IOException err) {
            System.out.println("Failed to read adults from CSV file.");
        }

//        AuditService audit = AuditService.getInstance();
//        audit.print("CSV read adults");
    }

    public void readChild() {
        PatientService patientService = PatientService.getInstance();

        try{
            BufferedReader csvReader = new BufferedReader(new FileReader("./resources/input/child.csv"));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                Child child = new Child(data[1], data[0], Integer.parseInt(data[2]), data[3], data[4], data[5]);
                patientService.addPatient(child);
            }
            csvReader.close();
        } catch (IOException err) {
            System.out.println("Failed to read children from CSV file.");
        }

//        AuditService audit = AuditService.getInstance();
//        audit.print("CSV read children");
    }
    public void readAppointment() {
        PatientService patientService = PatientService.getInstance();
        DoctorService doctorService = DoctorService.getInstance();
        AppointmentsService appointmentsService = AppointmentsService.getInstance();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("./resources/input/appointment.csv"));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                Patient patient = patientService.findPatient(Integer.parseInt(data[0]));
                Doctor doctor = doctorService.findDoctor(Integer.parseInt(data[1]));
                int day = Integer.parseInt(data[2].substring(0,2));
                int month = Integer.parseInt(data[2].substring(3, 5));
                int year = Integer.parseInt(data[2].substring(6, 10));
                int hour = Integer.parseInt(data[2].substring(12, 14));
                int minute = Integer.parseInt(data[2].substring(15, 17));
                LocalDateTime timeOfApp = LocalDateTime.of(year, month, day, hour, minute);
                boolean status = Boolean.parseBoolean(data[3]);

                Appointment app = new Appointment(patient, doctor, timeOfApp);
                app.setStatus(status);
                appointmentsService.addDoctorsAppointment(app, doctor);
                appointmentsService.makeAppointment(app);
            }
            csvReader.close();
        } catch (IOException err) {
            System.out.println("Failed to read appointments from CSV file.");
        }

//        AuditService audit = AuditService.getInstance();
//        audit.print("CSV read appointments");
    }

}
