package office.Office.service;

import office.Office.Person;
import office.database.repository.AdultRepo;
import office.database.repository.ChildRepo;
import office.doctor.Nurse;
import office.patient.Adult;
import office.patient.Child;
import office.patient.Patient;

import java.time.LocalDateTime;
import java.util.*;

public class PatientService {
    public static PatientService serviceInstance = null;

    private ArrayList<Patient> patients = new ArrayList<>();
    private AdultRepo adultRepo = new AdultRepo();
    private ChildRepo childRepo = new ChildRepo();

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
                Child child = new Child(lastname, firstname, birth, cnp, parent.getTel(), parent.getLastName());
                name = child.getLastName() + " " + child.getFirstName();
                patients.add(child);
//                addChild(child);
            }
        } else {
            System.out.print("\tPhone number: ");
            String tel = scanner.next();
            System.out.print("\tDoes he/she have health insurance (0/1)?");
            byte hi = scanner.nextByte();
            Adult adult = new Adult(lastname, firstname, birth, cnp, tel, hi == 1);
            name = adult.getLastName() + " " + adult.getFirstName();
            patients.add(adult);
            addAdult(adult);
        }

        System.out.println("Patient " + name + " successfully registered.");

        AuditService audit = AuditService.getInstance();
        audit.print("add patient");
    }

    public void addPatient(Patient patient){
        patients.add(patient);
    }

    public void addAdult(Adult adult) {
        adultRepo.insert(adult);
    }

//    public void addChild(Child child){
//        childRepo.insert(child);
//    }

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

    public Adult findAdult(int id) {
        return adultRepo.find(id);
    }

//    public Child findChild(int id){
//        return childRepo.find(id);
//    }

    public void deletePatient() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        System.out.println("Do you want to delete an adult(1) or a child(2)?");
        int altopt = scanner.nextInt();
        int id;
        if (altopt == 1) {
            System.out.print("Adult ID for deletion:");
            id = scanner.nextInt();
            deleteAdult(id);
        } else if (altopt == 2) {
            System.out.print("Child ID for deletion:");
            id = scanner.nextInt();
//            deleteChild(id);
        }
    }

    private void deleteAdult(int id) {
        Adult adult = adultRepo.find(id);

        if (adult == null) {
            System.out.println("Invalid adult ID.");
            return;
        }
        String name = adult.getLastName() + " " + adult.getFirstName();
        System.out.println("Adult patient " + name + " successfully deleted.");
        adultRepo.delete(adult);

        AuditService audit = AuditService.getInstance();
        audit.print("delete adult");
    }

//    private void deleteChild(int id) {
//        Child child = childRepo.find(id);
//
//        if (child == null) {
//            System.out.println("Invalid adult ID.");
//            return;
//        }
//        String name = child.getLastName() + " " + child.getFirstName();
//        System.out.println("Child patient " + name + " successfully deleted.");
//        childRepo.delete(child);
//
//        AuditService audit = AuditService.getInstance();
//        audit.print("delete child");
//    }

    public void editPatient() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        System.out.println("Do you want to edit an adult(1) or a child(2)?");
        int altopt = scanner.nextInt();
        int id;
        if (altopt == 1) {
            System.out.print("Adult ID for editing:");
            id = scanner.nextInt();
            editAdult(id);
        } else if (altopt == 2) {
            System.out.print("Pediatrician ID for editing:");
            id = scanner.nextInt();
//            editChild(id);
        }
    }

    private void editAdult(int id) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        Adult adult = findAdult(id);


        if (adult == null) {
            System.out.println("Invalid adult ID.");
            return;
        }
        System.out.println("Old adult's data");
        adult.show();

        System.out.println("What do you want to edit?");
        System.out.println("1. Last name");
        System.out.println("2. First name");
        System.out.println("3. Birth year");
        System.out.println("4. CNP");
        System.out.println("5. Telephone number");
        System.out.println("6. Health insurance");

        int opt = scanner.nextInt();
        if(opt == 1) {
            System.out.print("New last name: ");
            String lastname = scanner.next();
            adult.setLastName(lastname);
        } else if(opt == 2) {
            System.out.print("New first name: ");
            String firstname = scanner.next();
            adult.setFirstName(firstname);
        } else if(opt == 3) {
            System.out.print("New birth year: ");
            int birth = scanner.nextInt();
            adult.setBirthYear(birth);
        } else if(opt == 4) {
            System.out.print("New CNP: ");
            String cnp = scanner.next();
            adult.setCNP(cnp);
        } else if(opt == 5) {
            System.out.print("New telephone number: ");
            String tel = scanner.next();
            adult.setTel(tel);
        } else if(opt == 6) {
            System.out.print("New health insurance (false/true): ");
            boolean hi = scanner.nextBoolean();
            adult.setHealthInsurance(hi);
        } else {
            System.out.println("Invalid option. Abort editing.");
            return;
        }

        adultRepo.update(adult);
        System.out.println("Successfully edited.");

        AuditService audit = AuditService.getInstance();
        audit.print("edit adult");

    }

//    private void editChild(int id) {
//        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
//
//        Child child = findChild(id);
//        if (child == null) {
//            System.out.println("Invalid child ID.");
//            return;
//        }
//        System.out.println("Old child's data");
//        child.show();
//
//        System.out.println("What do you want to edit?");
//        System.out.println("1. Last name");
//        System.out.println("2. First name");
//        System.out.println("3. Birth year");
//        System.out.println("4. CNP");
//        System.out.println("5. Telephone number");
//        System.out.println("6. Parent name");
//
//        int opt = scanner.nextInt();
//        if(opt == 1) {
//            System.out.print("New last name: ");
//            String lastname = scanner.next();
//            child.setLastName(lastname);
//        } else if(opt == 2) {
//            System.out.print("New first name: ");
//            String firstname = scanner.next();
//            child.setFirstName(firstname);
//        } else if(opt == 3) {
//            System.out.print("New birth year: ");
//            int birth = scanner.nextInt();
//            child.setBirthYear(birth);
//        } else if(opt == 4) {
//            System.out.print("New CNP: ");
//            String cnp = scanner.next();
//            child.setCNP(cnp);
//        } else if(opt == 5) {
//            System.out.print("New telephone number: ");
//            String tel = scanner.next();
//            child.setTel(tel);
//        } else if(opt == 6) {
//            System.out.print("New parent name: ");
//            String parent = scanner.next();
//            child.setParentName(parent);
//        } else {
//            System.out.println("Invalid option. Abort editing.");
//            return;
//        }
//        childRepo.update(child);
//        System.out.println("Successfully edited.");
//
//        AuditService audit = AuditService.getInstance();
//        audit.print("edit child");
//    }

    public void displayPatients () {
        System.out.println("These are the patients registered here:");
        displayAdult();
//        displayChild();

        AuditService audit = AuditService.getInstance();
        audit.print("display patients");
    }

    private void displayAdult() {
        ArrayList<Adult> adults = adultRepo.findAll();
        for (var i: adults) {
            i.displayPatient();
        }
    }

//    private void displayChild() {
//        ArrayList<Child> children = childRepo.findAll();
//        for (var i: children) {
//            i.displayPatient();
//        }
//    }

    public ArrayList<Adult> getAdults() {
        ArrayList<Adult> adults = new ArrayList<>();

        for (var i: patients)
            if (i instanceof Adult)
                adults.add((Adult) i);

        return adults;
    }

    public ArrayList<Child> getChildren() {
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
