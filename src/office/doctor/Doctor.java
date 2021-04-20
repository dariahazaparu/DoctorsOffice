package office.doctor;

import office.Office.Person;

public abstract class Doctor extends Person {

    protected String email;
    protected int hireYear;

    public Doctor(String LastName, String FirstName, String Email, int HireYear) {
        super(LastName, FirstName);
        email = Email;
        hireYear = HireYear;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHireYear() {
        return hireYear;
    }

    public void setHireYear(int hireYear) {
        this.hireYear = hireYear;
    }

    public int getID() {
        return ID;
    }

    public abstract int computeSalary();

    public abstract void displayDoctor();

    public void show() {
        System.out.println("\tLast name: " + lastName);
        System.out.println("\tFirst name: " + firstName);
        System.out.println("\tEmail: " + email);
        System.out.println("\tHire year: " + hireYear);
    }
}
