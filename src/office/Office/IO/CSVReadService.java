// https://stackabuse.com/reading-and-writing-csvs-in-java/
package office.Office.IO;

import office.Office.DoctorService;
import office.doctor.Doctor;
import office.doctor.FamilyDoctor;
import office.doctor.Nurse;
import office.doctor.Pediatrician;

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
}
