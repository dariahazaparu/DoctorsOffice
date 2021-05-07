package office.Office.service;

import office.Office.Person;
import office.patient.Adult;
import office.patient.Child;
import office.patient.Patient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class PatientService {
    public static PatientService serviceInstance = null;

    private ArrayList<Patient> patients = new ArrayList<>();

    private PatientService() {}

    public static PatientService getInstance() {
        if (serviceInstance == null) {
            serviceInstance = new PatientService();
        }

        return serviceInstance;
    }

    public void addPatient(){
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        System.out.println("Patient's personal data");
        System.out.print("\tLast name: ");
        String lastname = scanner.next();
        System.out.print("\tFirst name: ");
        String firstname = scanner.next();
        System.out.print("\tBirth year: ");
        int birth = scanner.nextInt();
        System.out.print("\tCNP: ");
        String cnp = scanner.next();

        String name;
        LocalDateTime time = LocalDateTime.now();
        if (time.getYear() - birth < 18) {
            System.out.print("\tParent ID:");
            int id = scanner.nextInt();
            Patient parent = searchParent(id);
            if (parent == null) {
                System.out.println("ID not found or parent not actually valid parent. Registration aborted.");
                return;
            }
            else {
                Patient child = new Child(lastname, firstname, birth, cnp, parent.getTel(), parent.getLastName());
                name = child.getLastName() + " " + child.getFirstName();
                patients.add(child);
            }
        } else {
            System.out.print("\tPhone number: ");
            String tel = scanner.next();
            System.out.print("\tDoes he/she have health insurance (0/1)?");
            byte hi = scanner.nextByte();
            Patient adult = new Adult(lastname, firstname, birth, cnp, tel, hi == 1);
            name = adult.getLastName() + " " + adult.getFirstName();
            patients.add(adult);
        }

        System.out.println("Patient " + name + " successfully registered.");

        AuditService audit = AuditService.getInstance();
        audit.print("add patient");
    }

    public void addPatient(Patient patient){
        patients.add(patient);
    }

    private Patient searchParent(int id) {
        Patient sch = null;
        for(var i: patients)
            if (i.getID() == id && i.computeAge() >= 18)
                sch = i;
        return sch;
    }

    public Patient findPatient(int id) {
        Patient found = null;
        for (var i: patients)
            if (i.getID() == id)
                found = i;
        return found;
    }

    public void deletePatient(int id) {

        Patient patient = findPatient(id);
        if (patient == null) {
            System.out.println("Invalid patient ID.");
            return;
        }
        String name = patient.getLastName() + " " + patient.getFirstName();
        System.out.println("Patient " + name + " successfully deleted.");
        patients.remove(patient);

        AuditService audit = AuditService.getInstance();
        audit.print("delete patient");
    }

    public void editPatient(int id) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        Patient patient = findPatient(id);
        if (patient == null) {
            System.out.println("Invalid patient ID.");
            return;
        }
        System.out.println("Old patient's data");
        patient.show();

        System.out.println("What do you want to edit?");
        System.out.println("1. Last name");
        System.out.println("2. First name");
        System.out.println("3. Birth year");
        System.out.println("4. CNP");
        System.out.println("5. Phone number");
        int opt = scanner.nextInt();
        if(opt == 1) {
            System.out.print("New last name: ");
            String name = scanner.next();
            patient.setLastName(name);
        } else if (opt == 2) {
            System.out.print("New first name: ");
            String name = scanner.next();
            patient.setFirstName(name);
        } else if (opt == 3) {
            System.out.print("New birth year: ");
            int birth = scanner.nextInt();
            patient.setBirthYear(birth);
        } else if (opt == 4) {
            System.out.print("New CNP: ");
            String cnp = scanner.next();
            patient.setCNP(cnp);
        }  else if (opt == 5) {
            System.out.print("New phone number: ");
            String tel = scanner.next();
            patient.setTel(tel);
        } else {
            System.out.println("Invalid option. Abort editing.");
            return;
        }
        System.out.println("Successfully edited.");

        AuditService audit = AuditService.getInstance();
        audit.print("edit patient");
    }

    public void displayPatients () {
        System.out.println("These are the patients registered here:");
        for (var i: patients)
            i.displayPatient();

        AuditService audit = AuditService.getInstance();
        audit.print("display patients");
    }

    public ArrayList<Adult> getAdults() {
        ArrayList<Adult> adults = new ArrayList<>();

        for (var i: patients)
            if (i instanceof Adult)
                adults.add((Adult) i);

        return adults;
    }

    public ArrayList<Child> getChidren() {
        ArrayList<Child> children = new ArrayList<>();

        for (var i: patients)
            if (i instanceof Child)
                children.add((Child) i);

        return children;
    }

    public void sortByName() {

        Comparator<? super Patient> byLastName = new Comparator<>() {
            @Override
            public int compare(Patient o1, Patient o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        };
        patients.sort(byLastName);

        AuditService audit = AuditService.getInstance();
        audit.print("sort patients by last name");
    }
}
