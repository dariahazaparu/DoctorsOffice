package office.Office;

import office.Appointment.Appointment;
import office.doctor.Doctor;
import office.doctor.FamilyDoctor;
import office.doctor.Nurse;
import office.doctor.Pediatrician;
import office.pacient.Adult;
import office.pacient.Child;
import office.pacient.Patient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Service {
    static ArrayList<Patient> patients = new ArrayList<>();
    static ArrayList<Doctor> doctors = new ArrayList<>();
    static ArrayList<Appointment> appointments = new ArrayList<>();


    public static void addDoctor() {
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

    public static void addPatient(){
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
                patients.add(child);
            }
        } else {
            System.out.print("\tPhone number: ");
            String tel = scanner.next();
            System.out.print("\tDoes he/she have health insurance (0/1)?");
            byte hi = scanner.nextByte();
            Patient adult = new Adult(lastname, firstname, birth, cnp, tel, hi == 1);
            patients.add(adult);
        }

        System.out.println("Patient successfully registered.");
    }

    private static Patient searchParent(int id) {
        Patient sch = null;
        for(var i: patients)
            if (i.getID() == id && i.computeAge() >= 18)
                sch = i;
        return sch;
    }

    public static FamilyDoctor randomFamilyDoctor() {
        ArrayList<FamilyDoctor> fds = new ArrayList<>();
        for (var i: doctors) {
            if (i.getClass().toString().equals("class office.doctor.FamilyDoctor")){
                FamilyDoctor fd = (FamilyDoctor) i;
                fds.add(fd);
            }
        }
        Random rand = new Random();
        int given = rand.nextInt(fds.size());
        return fds.get(given);
    }

    private static Pediatrician randomPediatrician() {
        ArrayList<Pediatrician> fds = new ArrayList<>();
        for (var i: doctors) {
            if (i.getClass().toString().equals("class office.doctor.Pediatrician")){
                Pediatrician fd = (Pediatrician) i;
                fds.add(fd);
            }
        }
        Random rand = new Random();
        int given = rand.nextInt(fds.size());
        return fds.get(given);
    }

    private static Nurse randomNurse() {
        ArrayList<Nurse> fds = new ArrayList<>();
        for (var i: doctors) {
            if (i.getClass().toString().equals("class office.doctor.Nurse")){
                Nurse fd = (Nurse) i;
                fds.add(fd);
            }
        }
        Random rand = new Random();
        int given = rand.nextInt(fds.size());
        return fds.get(given);
    }

    public static Patient findPatient(int id) {
        Patient found = null;
        for (var i: patients)
            if (i.getID() == id)
                found = i;
        return found;
    }

    public static Doctor findDoctor (int id) {
        Doctor found = null;
        for (var i: doctors)
            if(i.getID() == id)
                found = i;
        return found;
    }

    public static void makeAppointment() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        System.out.println("Appointment data");
        System.out.println("Patient's ID:");
        int id = scanner.nextInt();
        System.out.println("Does the patient need a consult(1), pediatric consult(2) or a session of questions(3)?");
        byte consult = scanner.nextByte();
        System.out.println("Date (dd-mm-yyyy--hh-mm): ");
        String data = scanner.next();
        int day = Integer.parseInt(data.substring(0,2));
        int month = Integer.parseInt(data.substring(3, 5));
        int year = Integer.parseInt(data.substring(6, 10));
        int hour = Integer.parseInt(data.substring(12, 14));
        int minute = Integer.parseInt(data.substring(15, 17));
        LocalDateTime timeOfApp = LocalDateTime.of(year, month, day, hour, minute);

        Patient patient = findPatient(id);
        if (consult == 1) {
            FamilyDoctor familyDoctor = randomFamilyDoctor();
            if (familyDoctor != null && patient != null) {
                Appointment ap = new Appointment(findPatient(id), familyDoctor, timeOfApp);
                appointments.add(ap);
            } else {
                System.out.println("Appointment invalid.");
                return;
            }

        } else if (consult == 2) {
            Pediatrician pediatrician = randomPediatrician();
            if (pediatrician != null && patient != null) {
                Appointment ap = new Appointment(patient, pediatrician, timeOfApp);
                appointments.add(ap);
            } else{
                System.out.println("Appointment invalid.");
                return;
            }
        } else if (consult == 3) {
            Nurse nurse = randomNurse();
            if (nurse != null && patient != null) {
                Appointment ap = new Appointment(patient, nurse, timeOfApp);
                appointments.add(ap);
            } else {
                System.out.println("Appointment invalid.");
                return;
            }
        } else {
            System.out.println("Type of appointment invalid. Addition aborted.");
            return;
        }
        System.out.println("Appointment made successfully.");
    }

    public static void deleteDoctor() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        System.out.print("Doctor ID for deletion:");
        int id = scanner.nextInt();
        Doctor doctor = null;
        for (var i :doctors)
            if (i.getID() == id)
                doctor = i;
        if (doctor == null)
            System.out.println("Invalid ID.");
        else
            doctors.remove(doctor);
    }

    public static void deletePatient() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        System.out.print("Patient ID for deletion:");
        int id = scanner.nextInt();
        Patient patient = null;
        for (var i :patients)
            if (i.getID() == id)
                patient = i;
        if (patient == null)
            System.out.println("Invalid ID.");
        else
            patients.remove(patient);
    }


    public static void deleteAppointment() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        System.out.print("Appointment ID for deletion:");
        int id = scanner.nextInt();
        Appointment app = null;
        for (var i :appointments)
            if (i.getID() == id)
                app = i;
        if (app == null)
            System.out.println("Invalid ID.");
        else
            appointments.remove(app);
    }

    public static void displayDoctors() {
        System.out.println("These are the doctors who work here:");
        for (var i: doctors)
            i.displayDoctor();
    }
}
