// https://stackabuse.com/reading-and-writing-csvs-in-java/
package office.Office.IO;

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

    public void writeNurse() throws IOException {
        FileWriter csvWriter = new FileWriter("./resources/output/nurse.csv");
        csvWriter.write("First name,Last name,Email,Hire year,Hours\n");
        DoctorService doctorService = DoctorService.getInstance();
        ArrayList<Nurse> nurses = doctorService.getNurses();

        for (var nurse: nurses)
            csvWriter.write(nurse.getFirstName() + "," + nurse.getLastName() + "," + nurse.getEmail() + "," + nurse.getHireYear() + "," + nurse.getHours() + '\n');
        csvWriter.close();
    }

    public void writePediatrician() throws IOException {
        FileWriter csvWriter = new FileWriter("./resources/output/pediatrician.csv");
        csvWriter.write("First name,Last name,Email,Hire year,Bonus\n");
        DoctorService doctorService = DoctorService.getInstance();
        ArrayList<Pediatrician> peds = doctorService.getPediatricians();

        for (var ped: peds)
            csvWriter.write(ped.getFirstName() + "," + ped.getLastName() + "," + ped.getEmail() + "," + ped.getHireYear() + "," + ped.getBonus() + '\n');
        csvWriter.close();
    }

    public void writeFamilyDoctor() throws IOException {
        FileWriter csvWriter = new FileWriter("./resources/output/familydoctor.csv");
        csvWriter.write("First name,Last name,Email,Hire year,No of families\n");
        DoctorService doctorService = DoctorService.getInstance();
        ArrayList<FamilyDoctor> fam = doctorService.getFamilyDoctors();

        for (var doc: fam)
            csvWriter.write(doc.getFirstName() + "," + doc.getLastName() + "," + doc.getEmail() + "," + doc.getHireYear() + "," + doc.getNoOfFamilies() + '\n');
        csvWriter.close();
    }

    public void writeAdult() throws IOException {
        FileWriter csvWriter = new FileWriter("./resources/output/adult.csv");
        csvWriter.write("First name,Last name,Birth year,CNP,Tel number,Health insurance\n");
        PatientService patientService = PatientService.getInstance();
        ArrayList<Adult> adults = patientService.getAdults();

        for (var adult: adults)
            csvWriter.write(adult.getFirstName() + "," + adult.getLastName() + "," + adult.getBirthYear()  + ","
                    + adult.getCNP() + "," + adult.getTel() + "," + adult.isHealthInsurance()  + '\n');
        csvWriter.close();
    }

    public void writeChild() throws IOException {
        FileWriter csvWriter = new FileWriter("./resources/output/child.csv");
        csvWriter.write("First name,Last name,Birth year,CNP,Tel number,Parent Name\n");
        PatientService patientService = PatientService.getInstance();
        ArrayList<Child> children = patientService.getChidren();

        for (var child: children)
            csvWriter.write(child.getFirstName() + "," + child.getLastName() + "," + child.getBirthYear()  + ","
                    + child.getCNP() + "," + child.getTel() + "," + child.getParentName() + '\n');
        csvWriter.close();
    }
}
