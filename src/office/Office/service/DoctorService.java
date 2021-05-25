package office.Office.service;

import office.database.repository.FamilyDoctorRepo;
import office.database.repository.NurseRepo;
import office.database.repository.PediatricianRepo;
import office.doctor.Doctor;
import office.doctor.FamilyDoctor;
import office.doctor.Nurse;
import office.doctor.Pediatrician;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class DoctorService {
    public static DoctorService serviceInstance = null;

    private ArrayList<Doctor> doctors = new ArrayList<>();
    private NurseRepo nurseRepo = new NurseRepo();
    private PediatricianRepo pedsRepo = new PediatricianRepo();
    private FamilyDoctorRepo famRepo = new FamilyDoctorRepo();

    private DoctorService() {}

    public static DoctorService getInstance() {
        if (serviceInstance == null) {
            serviceInstance = new DoctorService();
        }

        return serviceInstance;
    }

    public void addDoctor() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        System.out.println("Doctor's personal data");
        System.out.print("\tLast name: ");
        String lastname = scanner.next();
        System.out.print("\tFirst name: ");
        String firstname = scanner.next();

        String name = lastname + " " + firstname;

        System.out.print("\tEmail: ");
        String email = scanner.next();
        System.out.print("\tHire year: ");
        int hire = scanner.nextInt();
        System.out.print("\tIs he/she a family doctor (1), nurse(2) or a pediatrician(3)?");
        int typeOfDoctor = scanner.nextInt();
        if (typeOfDoctor == 1) {
            System.out.print("\tHow many families does he/she has?");
            int nrFamilies = scanner.nextInt();
            FamilyDoctor familyDoctor = new FamilyDoctor(lastname, firstname, email, hire, nrFamilies);
            doctors.add(familyDoctor);
            addFamilyDoctor(familyDoctor);
        } else if (typeOfDoctor == 2) {
            System.out.print("\tHow many hours does he/she work in a week?");
            int nrHours = scanner.nextInt();
            Nurse nurse = new Nurse(lastname, firstname, email, hire, nrHours);
            doctors.add(nurse);
            addNurse(nurse);
        } else if (typeOfDoctor == 3) {
            System.out.print("\tHow high is his/her bonus?");
            int bonus = scanner.nextInt();
            Pediatrician peds = new Pediatrician(lastname, firstname, email, hire, bonus);
            doctors.add(peds);
            addPediatrician(peds);
        } else {
            System.out.println("Invalid type of doctor. Addition aborted.");
        }

        System.out.println("Doctor " + name + " successfully hired.");

        AuditService audit = AuditService.getInstance();
        audit.print("add doctor");
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public void addNurse(Nurse nurse) {
        nurseRepo.insert(nurse);
    }

    public void addPediatrician(Pediatrician ped) {
        pedsRepo.insert(ped);
    }

    public void addFamilyDoctor(FamilyDoctor fam) {
        famRepo.insert(fam);
    }

    public FamilyDoctor randomFamilyDoctor() {
        ArrayList<FamilyDoctor> fds = new ArrayList<>();
        for (var i: doctors) {
            if (i instanceof FamilyDoctor){
                FamilyDoctor fd = (FamilyDoctor) i;
                fds.add(fd);
            }
        }
        Random rand = new Random();
        int given = rand.nextInt(fds.size());
        return fds.get(given);
    }

    public Pediatrician randomPediatrician() {
        ArrayList<Pediatrician> fds = new ArrayList<>();
        for (var i: doctors) {
            if (i instanceof Pediatrician){
                Pediatrician fd = (Pediatrician) i;
                fds.add(fd);
            }
        }
        Random rand = new Random();
        int given = rand.nextInt(fds.size());
        return fds.get(given);
    }

    public Nurse randomNurse() {
        ArrayList<Nurse> fds = new ArrayList<>();
        for (var i: doctors) {
            if (i instanceof Nurse){
                Nurse fd = (Nurse) i;
                fds.add(fd);
            }
        }
        Random rand = new Random();
        int given = rand.nextInt(fds.size());
        return fds.get(given);
    }

    public void deleteDoctor(){
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        System.out.println("Do you want to delete a nurse(1), a pediatrician (2) or a family doctor(3)?");
        int altopt = scanner.nextInt();
        int id;
        if (altopt == 1) {
            System.out.print("Nurse ID for deletion:");
            id = scanner.nextInt();
            deleteNurse(id);
        } else if (altopt == 2) {
            System.out.print("Pediatrician ID for deletion:");
            id = scanner.nextInt();
            deletePediatrician(id);
        } else if (altopt == 3) {
            System.out.print("Family doctor ID for deletion:");
            id = scanner.nextInt();
            deleteFamilyDoctor(id);
        }
    }

    public void deleteNurse(int id) {
        Nurse nurse = nurseRepo.find(id);

        if (nurse == null) {
            System.out.println("Invalid nurse ID.");
            return;
        }
        String name = nurse.getLastName() + " " + nurse.getFirstName();
        System.out.println("Nurse " + name + " successfully deleted.");
        nurseRepo.delete(nurse);

        AuditService audit = AuditService.getInstance();
        audit.print("fire nurse");
    }

    public void deletePediatrician(int id) {
        Pediatrician ped = pedsRepo.find(id);

        if (ped == null) {
            System.out.println("Invalid pediatrician ID.");
            return;
        }
        String name = ped.getLastName() + " " + ped.getFirstName();
        System.out.println("Pediatrician " + name + " successfully deleted.");

        pedsRepo.delete(ped);

        AuditService audit = AuditService.getInstance();
        audit.print("fire pediatrician");
    }

    public void deleteFamilyDoctor(int id){
        FamilyDoctor fam = famRepo.find(id);

        if (fam == null) {
            System.out.println("Invalid family doctor ID.");
            return;
        }
        String name = fam.getLastName() + " " + fam.getFirstName();
        System.out.println("Family doctor " + name + " successfully deleted.");
        famRepo.delete(fam);

        AuditService audit = AuditService.getInstance();
        audit.print("fire family doctor");
    }

    public Doctor findDoctor (int id) {
        Doctor found = null;
        for (var i: doctors)
            if(i.getID() == id)
                found = i;
        return found;
    }

    public Nurse findNurse(int id) {
        return nurseRepo.find(id);
    }

    public Pediatrician findPediatrician(int id) {
        return pedsRepo.find(id);
    }

    public FamilyDoctor findFamilyDoctor(int id) {
        return famRepo.find(id);
    }

    public void displayDoctors() {
        System.out.println("These are the doctors who work here:");
        displayNurse();
        displayPediatrician();
        displayFamilyDoctors();

        AuditService audit = AuditService.getInstance();
        audit.print("display doctors");
    }

    private void displayNurse(){
        ArrayList<Nurse> nurses = nurseRepo.findAll();
        for (var i: nurses) {
            i.displayDoctor();
        }
    }

    private void displayPediatrician(){
        ArrayList<Pediatrician> peds = pedsRepo.findAll();
        for (var i: peds) {
            i.displayDoctor();
        }
    }

    private void displayFamilyDoctors(){
        ArrayList<FamilyDoctor> fams = famRepo.findAll();
        for (var i: fams) {
            i.displayDoctor();
        }
    }

    public void editDoctor(){
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        System.out.println("Do you want to edit a nurse(1), a pediatrician (2) or a family doctor(3)?");
        int altopt = scanner.nextInt();
        int id;
        if (altopt == 1) {
            System.out.print("Nurse ID for editing:");
            id = scanner.nextInt();
            editNurse(id);
        } else if (altopt == 2) {
            System.out.print("Pediatrician ID for editing:");
            id = scanner.nextInt();
            editPediatrician(id);
        } else if (altopt == 3) {
            System.out.print("Family doctor ID for editing:");
            id = scanner.nextInt();
            editFamilyDoctor(id);
        }
    }

    private void editNurse(int id) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        Nurse nurse = findNurse(id);
        if (nurse == null) {
            System.out.println("Invalid nurse ID.");
            return;
        }
        System.out.println("Old nurse's data");
        nurse.show();

        System.out.println("What do you want to edit?");
        System.out.println("1. Last name");
        System.out.println("2. First name");
        System.out.println("3. Email");
        System.out.println("4. Hire year");
        System.out.println("5. Working hours per week");

        int opt = scanner.nextInt();
        if(opt == 1) {
            System.out.print("New last name: ");
            String lastname = scanner.next();
            nurse.setLastName(lastname);
        }else if (opt == 2) {
            System.out.print("New first name: ");
            String firstname = scanner.next();
            nurse.setFirstName(firstname);
        } else if (opt == 3) {
            System.out.print("New email: ");
            String email = scanner.next();
            nurse.setEmail(email);
        } else if (opt == 4) {
            System.out.print("New hire year: ");
            int hire = scanner.nextInt();
            nurse.setHireYear(hire);
            System.out.println("New salary: " + nurse.computeSalary());
        } else if (opt == 5) {
            System.out.print("New hours: ");
            int hours = scanner.nextInt();
            nurse.setHours(hours);
        } else {
            System.out.println("Invalid option. Abort editing.");
            return;
        }
        nurseRepo.update(nurse);
        System.out.println("Successfully edited.");

        AuditService audit = AuditService.getInstance();
        audit.print("edit nurse");

    }

    private void editPediatrician(int id) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        Pediatrician ped = findPediatrician(id);
        if (ped == null) {
            System.out.println("Invalid pediatrician ID.");
            return;
        }
        System.out.println("Old pediatrician's data");
        ped.show();

        System.out.println("What do you want to edit?");
        System.out.println("1. Last name");
        System.out.println("2. First name");
        System.out.println("3. Email");
        System.out.println("4. Hire year");
        System.out.println("5. Salary bonus");

        int opt = scanner.nextInt();
        if(opt == 1) {
            System.out.print("New last name: ");
            String lastname = scanner.next();
            ped.setLastName(lastname);
        }else if (opt == 2) {
            System.out.print("New first name: ");
            String firstname = scanner.next();
            ped.setFirstName(firstname);
        } else if (opt == 3) {
            System.out.print("New email: ");
            String email = scanner.next();
            ped.setEmail(email);
        } else if (opt == 4) {
            System.out.print("New hire year: ");
            int hire = scanner.nextInt();
            ped.setHireYear(hire);
            System.out.println("New salary: " + ped.computeSalary());
        } else if (opt == 5) {
            System.out.print("New bonus: ");
            int bonus = scanner.nextInt();
            ped.setBonus(bonus);
        } else {
            System.out.println("Invalid option. Abort editing.");
            return;
        }
        pedsRepo.update(ped);
        System.out.println("Successfully edited.");

        AuditService audit = AuditService.getInstance();
        audit.print("edit pediatrician");

    }

    private void editFamilyDoctor(int id) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        FamilyDoctor fam = findFamilyDoctor(id);
        if (fam == null) {
            System.out.println("Invalid family doctor ID.");
            return;
        }
        System.out.println("Old family doctor's data");
        fam.show();

        System.out.println("What do you want to edit?");
        System.out.println("1. Last name");
        System.out.println("2. First name");
        System.out.println("3. Email");
        System.out.println("4. Hire year");
        System.out.println("5. Number of families");

        int opt = scanner.nextInt();
        if(opt == 1) {
            System.out.print("New last name: ");
            String lastname = scanner.next();
            fam.setLastName(lastname);
        }else if (opt == 2) {
            System.out.print("New first name: ");
            String firstname = scanner.next();
            fam.setFirstName(firstname);
        } else if (opt == 3) {
            System.out.print("New email: ");
            String email = scanner.next();
            fam.setEmail(email);
        } else if (opt == 4) {
            System.out.print("New hire year: ");
            int hire = scanner.nextInt();
            fam.setHireYear(hire);
            System.out.println("New salary: " + fam.computeSalary());
        } else if (opt == 5) {
            System.out.print("New number of families: ");
            int noOfFamilies = scanner.nextInt();
            fam.setNoOfFamilies(noOfFamilies);
        } else {
            System.out.println("Invalid option. Abort editing.");
            return;
        }
        famRepo.update(fam);
        System.out.println("Successfully edited.");

        AuditService audit = AuditService.getInstance();
        audit.print("edit family doctor");
    }

    public ArrayList<Nurse> getNurses() {
        ArrayList<Nurse> nurses = new ArrayList<>();

        for (var i: doctors)
            if (i instanceof Nurse) {
                nurses.add((Nurse) i);
            }

        return nurses;
    }

    public ArrayList<Pediatrician> getPediatricians() {
        ArrayList<Pediatrician> peds = new ArrayList<>();

        for (var i: doctors)
            if (i instanceof Pediatrician)
                peds.add((Pediatrician) i);

            return peds;
    }

    public ArrayList<FamilyDoctor> getFamilyDoctors() {
        ArrayList<FamilyDoctor> fam = new ArrayList<>();

        for (var i: doctors)
            if (i instanceof FamilyDoctor)
                fam.add((FamilyDoctor) i);

            return fam;
    }

    public void sortByHireYear(){

        Comparator<? super Doctor> byHireYear = new Comparator<>() {
            @Override
            public int compare(Doctor o1, Doctor o2) {
                return o1.getHireYear() - o2.getHireYear();
            }
        };
        doctors.sort(byHireYear);

        AuditService audit = AuditService.getInstance();
        audit.print("sort doctors by hire year");
    }

    public void sortBySalary() {
        Comparator<? super Doctor> bySalary = new Comparator<>() {
            @Override
            public int compare(Doctor o1, Doctor o2) {
                return o1.computeSalary() - o2.computeSalary();
            }
        };
        doctors.sort(bySalary);

        AuditService audit = AuditService.getInstance();
        audit.print("sort doctors by salary");
    }

}
