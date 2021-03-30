package office.pacient;
import java.time.LocalDateTime;

public abstract class Patient {
    protected int ID;
    protected String lastName;
    protected String firstName;
    protected int birthYear;
    protected String CNP;
    protected String tel;
    static int increment = 0;

    {
        this.ID = ++increment;
    }

    public Patient(String LastName, String FirstName, int BirthYear, String CNP, String Tel) {
        lastName = LastName;
        firstName = FirstName;
        birthYear = BirthYear;
        this.CNP = CNP;
        tel = Tel;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
