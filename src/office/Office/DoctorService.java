package office.Office;

import office.doctor.Doctor;
import office.doctor.FamilyDoctor;
import office.doctor.Nurse;
import office.doctor.Pediatrician;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DoctorService {
    public static DoctorService serviceInstance = null;

    private ArrayList<Doctor> doctors = new ArrayList<>();

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
        System.out.print("\tEmail: ");
        String email = scanner.next();
        System.out.print("\tHire year: ");
        int hire = scanner.nextInt();
        System.out.print("\tIs he/she a family doctor (1), nurse(2) or a pediatrician(3)?");
        int typeOfDoctor = scanner.nextInt();
        if (typeOfDoctor == 1) {
            System.out.print("\tHow many families does he/she has?");
            int nrFamilies = scanner.nextInt();
            Doctor familyDoctor = new FamilyDoctor(lastname, firstname, email, hire, nrFamilies);
            doctors.add(familyDoctor);
        } else if (typeOfDoctor == 2) {
            System.out.print("\tHow many hours does he/she work in a week?");
            int nrHours = scanner.nextInt();
            Doctor nurse = new Nurse(lastname, firstname, email, hire, nrHours);
            doctors.add(nurse);
        } else if (typeOfDoctor == 3) {
            System.out.print("\tHow high is his/her bonus?");
            int bonus = scanner.nextInt();
            Doctor peds = new Pediatrician(lastname, firstname, email, hire, bonus);
            doctors.add(peds);
        } else {
            System.out.println("Invalid type of doctor. Addition aborted.");
        }
        System.out.println("Doctor added successfully.");
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

    public void deleteDoctor() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        System.out.print("Doctor ID for deletion:");
        int id = scanner.nextInt();
        Doctor doctor = findDoctor(id);
        if (doctor == null) {
            System.out.println("Invalid doctor ID.");
            return;
        }

        doctors.remove(doctor);
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
    }

    public void editDoctor() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        System.out.print("Doctor ID for editing:");
        int id = scanner.nextInt();
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
//        if (doctor.getClass().toString().equals("class office.doctor.FamilyDoctor")) {
//            System.out.println("5. Number of families");
//        } else if (doctor.getClass().toString().equals("class office.doctor.Nurse")) {
//            System.out.println("5. Working hours per week");
//        } else if (doctor.getClass().toString().equals("class office.doctor.Pediatrician")) {
//            System.out.println("5. Salary bonus");
//        }

        int opt = scanner.nextInt();
        if(opt == 1) {
            System.out.print("New last name: ");
            String name = scanner.next();
            doctor.setLastName(name);
        } else if (opt == 2) {
            System.out.print("New first name: ");
            String name = scanner.next();
            doctor.setFirstName(name);
        } else if (opt == 3) {
            System.out.print("New email: ");
            String email = scanner.next();
            doctor.setEmail(email);
        } else if (opt == 4) {
            System.out.print("New hire year: ");
            int hire = scanner.nextInt();
            doctor.setHireYear(hire);
            System.out.println("New salary: " + doctor.computeSalary());
        } else {
            System.out.println("Invalid option. Abort editing.");
            return;
        }
//        else if (opt == 5) {
//            if (doctor.getClass().toString().equals("class office.doctor.FamilyDoctor")) {
//            } else if (doctor.getClass().toString().equals("class office.doctor.Nurse")) {
//            } else if (doctor.getClass().toString().equals("class office.doctor.Pediatrician")) {
//            }
//        }
        System.out.println("Successfully edited.");

    }
}
