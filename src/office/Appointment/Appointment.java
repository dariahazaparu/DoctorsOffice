package office.Appointment;

import office.doctor.Doctor;
import office.pacient.Patient;

import java.sql.Time;
import java.time.LocalDateTime;

public class Appointment {
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime timeOfAppointment;


    public Appointment(Patient patient, Doctor doctor, LocalDateTime TimeOfAppointment) {
        this.patient = patient;
        this.doctor = doctor;
        timeOfAppointment = TimeOfAppointment;
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

    public void displayAppointment() {
        System.out.println("Patient:");
        patient.displayPatient();
        System.out.println("Doctor:\n" + doctor.getLastName());
        System.out.println("Date: " + timeOfAppointment.getDayOfMonth() + " " + timeOfAppointment.getMonth() + " " + timeOfAppointment.getYear());

    }
}
