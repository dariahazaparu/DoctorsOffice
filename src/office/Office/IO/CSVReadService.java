// https://stackabuse.com/reading-and-writing-csvs-in-java/
package office.Office.IO;

import office.Office.DoctorService;
import office.Office.PatientService;
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

public class CSVReadService {
    public static CSVReadService serviceInstance = null;

    private CSVReadService() {}

    public static CSVReadService getInstance() {
        if (serviceInstance == null) {
            serviceInstance = new CSVReadService();
        }

        return serviceInstance;
    }

    public void readNurse() throws IOException {
        DoctorService doctorService = DoctorService.getInstance();
        BufferedReader csvReader = new BufferedReader(new FileReader("./resources/input/nurse.csv"));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            Doctor nurse = new Nurse(data[0], data[1], data[2],Integer.parseInt(data[3]), Integer.parseInt(data[4]));
            doctorService.addDoctor(nurse);
        }
        csvReader.close();
    }

    public void readPediatrician() throws IOException {
        DoctorService doctorService = DoctorService.getInstance();
        BufferedReader csvReader = new BufferedReader(new FileReader("./resources/input/pediatrician.csv"));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            Doctor peds = new Pediatrician(data[0], data[1], data[2],Integer.parseInt(data[3]), Integer.parseInt(data[4]));
            doctorService.addDoctor(peds);
        }
        csvReader.close();
    }

    public void readFamilyDoctor() throws IOException {
        DoctorService doctorService = DoctorService.getInstance();
        BufferedReader csvReader = new BufferedReader(new FileReader("./resources/input/familydoctor.csv"));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            Doctor fam = new FamilyDoctor(data[0], data[1], data[2],Integer.parseInt(data[3]), Integer.parseInt(data[4]));
            doctorService.addDoctor(fam);
        }
        csvReader.close();
    }

    public void readAdult() throws IOException {
        PatientService patientService = PatientService.getInstance();
        BufferedReader csvReader = new BufferedReader(new FileReader("./resources/input/adult.csv"));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            Patient adult = new Adult(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4], Boolean.parseBoolean(data[5]));
            patientService.addPatient(adult);
        }
        csvReader.close();
    }

    public void readChild() throws IOException {
        PatientService patientService = PatientService.getInstance();
        BufferedReader csvReader = new BufferedReader(new FileReader("./resources/input/child.csv"));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            Patient child = new Child(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4], data[5]);
            patientService.addPatient(child);
        }
        csvReader.close();
    }
}
