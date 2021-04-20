package office.pacient;
import office.Office.Person;

import java.time.LocalDateTime;

public abstract class Patient extends Person {

    protected int birthYear;
    protected String CNP;
    protected String tel;

    public Patient(String LastName, String FirstName, int BirthYear, String CNP, String Tel) {
        super(LastName, FirstName);
        birthYear = BirthYear;
        this.CNP = CNP;
        tel = Tel;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getID() {
        return ID;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public int computeAge(){
        LocalDateTime time = LocalDateTime.now();
        return time.getYear() - birthYear;
    }

    public abstract void displayPatient();

    public void show() {
        System.out.println("Last name: " + lastName);
        System.out.println("First name: " + firstName);
        System.out.println("Birth year: " + birthYear);
        System.out.println("CNP: " + CNP);
        System.out.println("Phone number: " + tel);
    }
}
