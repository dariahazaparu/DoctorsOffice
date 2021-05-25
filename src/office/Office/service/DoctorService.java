package office.Office.service;

import office.database.repository.FamilyDoctorRepo;
import office.database.repository.NurseRepo;
import office.database.repository.PediatricianRepo;
import office.doctor.Doctor;
import office.doctor.FamilyDoctor;
import office.doctor.Nurse;
import office.doctor.Pediatrician;

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

    public void deleteDoctor(int id) {

        Doctor doctor = findDoctor(id);
        if (doctor == null) {
            System.out.println("Invalid doctor ID.");
            return;
        }
        if (doctor instanceof Nurse){
            nurseRepo.delete((Nurse)doctor);
        }
        if (doctor instanceof Pediatrician) {
            pedsRepo.delete((Pediatrician) doctor);
        }
        if (doctor instanceof FamilyDoctor){
            famRepo.delete((FamilyDoctor) doctor);
        }
        String name = doctor.getLastName() + " " + doctor.getFirstName();
        System.out.println("Doctor " + name + " successfully deleted.");
        doctors.remove(doctor);

        AuditService audit = AuditService.getInstance();
        audit.print("fire doctor");
    }

    public Doctor findDoctor (int id) {
        Doctor found = null;
        for (var i: doctors)
            if(i.getID() == id)
                found = i;
        return found;
    }

    public void displayDoctors() {
        System.out.println("These are the doctors who work here:");
        for (var i: doctors)
            i.displayDoctor();

        AuditService audit = AuditService.getInstance();
        audit.print("display doctor");
    }

    public void editDoctor(int id) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        Doctor doctor = findDoctor(id);
        if (doctor == null) {
            System.out.println("Invalid doctor ID.");
            return;
        }
        System.out.println("Old doctor's data");
        doctor.show();

        System.out.println("What do you want to edit?");
        System.out.println("1. Last name");
        System.out.println("2. First name");
        System.out.println("3. Email");
        System.out.println("4. Hire year");
        if (doctor instanceof FamilyDoctor) {
            System.out.println("5. Number of families");
        } else if (doctor instanceof Nurse) {
            System.out.println("5. Working hours per week");
        } else if (doctor instanceof Pediatrician) {
            System.out.println("5. Salary bonus");
        }
        String lastname = "";
        String firstname = "";
        String email = "";
        int hire = 0;
        int opt = scanner.nextInt();
        if(opt == 1) {
            System.out.print("New last name: ");
            lastname = scanner.next();
            doctor.setLastName(lastname);
        } else if (opt == 2) {
            System.out.print("New first name: ");
            firstname = scanner.next();
            doctor.setFirstName(firstname);
        } else if (opt == 3) {
            System.out.print("New email: ");
            email = scanner.next();
            doctor.setEmail(email);
        } else if (opt == 4) {
            System.out.print("New hire year: ");
            hire = scanner.nextInt();
            doctor.setHireYear(hire);
            System.out.println("New salary: " + doctor.computeSalary());
        } else if (opt == 5) {
            if (doctor instanceof FamilyDoctor) {
                System.out.print("New no of families: ");
                int nr = scanner.nextInt();
                FamilyDoctor fam = new FamilyDoctor(lastname, firstname, email, hire, nr);

            } else if (doctor instanceof Nurse) {
                System.out.print("New hours: ");
                int hours = scanner.nextInt();
                Nurse nurse = nurseRepo.find(id);
                nurse.setLastName(lastname);
                nurse.setFirstName(firstname);
                nurse.setEmail(email);
                nurse.setHireYear(hire);
                nurse.setHours(hours);
                nurseRepo.update(nurse);
            } else if (doctor instanceof Pediatrician) {
            }
        } else {
            System.out.println("Invalid option. Abort editing.");
            return;
        }
        System.out.println("Successfully edited.");

        AuditService audit = AuditService.getInstance();
        audit.print("edit doctor");

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
