package office.Appointment;

import office.doctor.Doctor;
import office.patient.Patient;

import java.time.LocalDateTime;

public class Appointment {
    private int ID;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime timeOfAppointment;
    private boolean status;
    static int increment = 0;

    {
        this.ID = ++increment;
    }


    public Appointment(Patient patient, Doctor doctor, LocalDateTime TimeOfAppointment) {
        this.patient = patient;
        this.doctor = doctor;
        timeOfAppointment = TimeOfAppointment;
        status = false;
    }

    public int getID() {
        return ID;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getTimeOfAppointment() {
        return timeOfAppointment;
    }

    public void setTimeOfAppointment(LocalDateTime timeOfAppointment) {
        this.timeOfAppointment = timeOfAppointment;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void displayAppointment() {
        System.out.print("Patient:");
        patient.displayPatient();
        System.out.println("Doctor: " + doctor.getLastName() + " " + doctor.getFirstName());
        System.out.println("Date: " + timeOfAppointment.getDayOfMonth() + " " + timeOfAppointment.getMonth() + " " + timeOfAppointment.getYear());
        if (status)
            System.out.println("Status: attended" );
        else
            System.out.println("Status: waiting");


    }
}
