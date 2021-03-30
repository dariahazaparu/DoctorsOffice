package office.doctor;

public class Nurse extends Doctor {

    private int hours;

    public Nurse(String LastName, String FirstName, String Email, int BirthYear, int HireYear, int Hours) {
        super(LastName, FirstName, Email, BirthYear, HireYear);
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
        return 1000 * (2021 - hireYear) + 10 * hours;
    }
}

