package office.doctor;

public abstract class Doctor {
    protected int ID;
    protected String lastName;
    protected String firstName;
    protected String email;
    protected int hireYear;
    static int increment = 0;

    {
        this.ID = ++increment;
    }

    public Doctor(String LastName, String FirstName, String Email, int HireYear) {
        lastName = LastName;
        firstName = FirstName;
        email = Email;
        hireYear = HireYear;
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
}
