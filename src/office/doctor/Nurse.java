package office.doctor;

import java.time.LocalDateTime;

public class Nurse extends Doctor {

    private int hours;

    public Nurse(String LastName, String FirstName, String Email, int HireYear, int Hours) {
        super(LastName, FirstName, Email, HireYear);
        hours = Hours;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public int computeSalary() {
        LocalDateTime time = LocalDateTime.now();
        return 1000 * (time.getYear() - hireYear) + 10 * hours;
    }

    @Override
    public void displayDoctor() {
        System.out.println(ID + "\tDoctor " + lastName + " " + firstName + " (" + email + ") has been working here since "
                + hireYear + " as a nurse.");
    }

    public void show() {
        super.show();
        System.out.println("\tWorking hours per week: " + hours);
    }
}

