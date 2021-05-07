package office.Office.service;

import office.Appointment.Appointment;
import office.doctor.Doctor;
import office.doctor.FamilyDoctor;
import office.doctor.Nurse;
import office.doctor.Pediatrician;
import office.patient.Patient;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class AppointmentsService {
    public static AppointmentsService serviceInstance = null;

    private final ArrayList<Appointment> appointments = new ArrayList<>();
    private final HashMap<Doctor, HashSet<Appointment>> doctorApp = new HashMap<>();

    private AppointmentsService() {
    }

    public static AppointmentsService getInstance() {
        if (serviceInstance == null) {
            serviceInstance = new AppointmentsService();
        }

        return serviceInstance;
    }

    public Appointment findAppointment(int id) {
        Appointment found = null;
        for (var i : appointments)
            if (i.getID() == id)
                found = i;
        return found;
    }

    public void makeAppointment() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        DoctorService doctors = DoctorService.getInstance();
        PatientService patients = PatientService.getInstance();

        System.out.println("Appointment data");
        System.out.println("Patient's ID:");
        int id = scanner.nextInt();
        System.out.println("Does the patient need a consult(1), pediatric consult(2) or a session of questions(3)?");
        byte consult = scanner.nextByte();
        System.out.println("Date (dd-mm-yyyy--hh-mm): ");
        String data = scanner.next();
        int day = Integer.parseInt(data.substring(0, 2));
        int month = Integer.parseInt(data.substring(3, 5));
        int year = Integer.parseInt(data.substring(6, 10));
        int hour = Integer.parseInt(data.substring(12, 14));
        int minute = Integer.parseInt(data.substring(15, 17));
        LocalDateTime timeOfApp = LocalDateTime.of(year, month, day, hour, minute);

        Patient patient = patients.findPatient(id);
        if (consult == 1) {
            FamilyDoctor familyDoctor = doctors.randomFamilyDoctor();
            if (familyDoctor != null && patient != null) {
                Appointment ap = new Appointment(patients.findPatient(id), familyDoctor, timeOfApp);
                appointments.add(ap);
                doctorApp.get(familyDoctor).add(ap);
            } else {
                System.out.println("Appointment invalid.");
                return;
            }

        } else if (consult == 2) {
            Pediatrician pediatrician = doctors.randomPediatrician();
            if (pediatrician != null && patient != null) {
                Appointment ap = new Appointment(patient, pediatrician, timeOfApp);
                appointments.add(ap);
                doctorApp.get(pediatrician).add(ap);
            } else {
                System.out.println("Appointment invalid.");
                return;
            }
        } else if (consult == 3) {
            Nurse nurse = doctors.randomNurse();
            if (nurse != null && patient != null) {
                Appointment ap = new Appointment(patient, nurse, timeOfApp);
                appointments.add(ap);
                doctorApp.get(nurse).add(ap);
            } else {
                System.out.println("Appointment invalid.");
                return;
            }
        } else {
            System.out.println("Type of appointment invalid. Addition aborted.");
            return;
        }
        System.out.println("Appointment made successfully.");


        AuditService audit = AuditService.getInstance();
        audit.print("make appointment");
    }

    public void makeAppointment(Appointment app) {
        appointments.add(app);
    }

    public void deleteAppointment(int id) {

        Appointment app = findAppointment(id);
        if (app == null) {
            System.out.println("Invalid appointment ID.");
            return;
        }
        appointments.remove(app);

        AuditService audit = AuditService.getInstance();
        audit.print("cancel appointment");
    }

    public void displayAppointments() {
        System.out.println("These are all appointments made at this clinic: ");
        for (var i : appointments)
            i.displayAppointment();

        AuditService audit = AuditService.getInstance();
        audit.print("display appointments");
    }

    public void editAppointment(int id) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        DoctorService doctors = DoctorService.getInstance();
        PatientService patients = PatientService.getInstance();

        Appointment app = findAppointment(id);
        if (app == null) {
            System.out.println("Invalid appointment ID.");
            return;
        }
        System.out.println("Old doctor's data");
        app.displayAppointment();

        System.out.println("What do you want to edit?");
        System.out.println("1. Patient");
        System.out.println("2. Doctor");
        System.out.println("3. Date");
        System.out.println("4. Status");

        int opt = scanner.nextInt();
        if (opt == 1) {
            System.out.print("New patient id: ");
            int pid = scanner.nextInt();
            Patient patient = patients.findPatient(pid);
            app.setPatient(patient);
        } else if (opt == 2) {
            System.out.print("New doctor id: ");
            int pid = scanner.nextInt();
            Doctor doctor = doctors.findDoctor(pid);
            app.setDoctor(doctor);
        } else if (opt == 3) {
            System.out.print("New date: ");
            String data = scanner.next();
            int day = Integer.parseInt(data.substring(0, 2));
            int month = Integer.parseInt(data.substring(3, 5));
            int year = Integer.parseInt(data.substring(6, 10));
            int hour = Integer.parseInt(data.substring(12, 14));
            int minute = Integer.parseInt(data.substring(15, 17));
            LocalDateTime timeOfApp = LocalDateTime.of(year, month, day, hour, minute);
            app.setTimeOfAppointment(timeOfApp);
        } else if (opt == 4) {
            System.out.print("New status (0/1): ");
            boolean status = scanner.nextBoolean();
            app.setStatus(status);
        } else {
            System.out.println("Invalid option. Abort editing.");
            return;
        }
        System.out.println("Successfully edited.");

        AuditService audit = AuditService.getInstance();
        audit.print("edit appointment");
    }

    public void goToAppointment() {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        System.out.println("Appointment ID attended:");
        int id = scanner.nextInt();
        Appointment app = findAppointment(id);
        if (app == null) {
            System.out.println("Invalid appointment ID.");
            return;
        }
        app.setStatus(!app.isStatus());
        System.out.println("Appointment attended.");

        AuditService audit = AuditService.getInstance();
        audit.print("attend appointment");
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void addDoctorsAppointment(Appointment app, Doctor doctor) {

        if (doctorApp.containsKey(doctor)) {
            doctorApp.get(doctor).add(app);
        } else {
            HashSet<Appointment> apps = new HashSet<>();
            apps.add(app);
            doctorApp.put(doctor, apps);
        }

    }

    public HashSet<Appointment> getDoctorsAppointments(int id){
        DoctorService doctorService = DoctorService.getInstance();
        Doctor doctor = doctorService.findDoctor(id);
        if (doctor == null) {
            System.out.println("Invalid doctor ID.");
            return null;
        }

        AuditService audit = AuditService.getInstance();
        audit.print("get doctors appointments");

        return doctorApp.get(doctor);
    }

    public Set<Appointment> getDoctorsNextAppointments(int id) {
        DoctorService doctorService = DoctorService.getInstance();
        Doctor doctor = doctorService.findDoctor(id);
        if (doctor == null) {
            System.out.println("Invalid doctor ID.");
            return null;
        }

        AuditService audit = AuditService.getInstance();
        audit.print("get doctors next appointments");

        return doctorApp.get(doctor).stream().filter(a -> !a.isStatus()).collect(Collectors.toSet());
    }
}
