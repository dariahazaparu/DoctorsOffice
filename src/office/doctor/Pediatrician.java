package office.doctor;

public class Pediatrician extends Doctor {

    private int bonus;

    public Pediatrician(String LastName, String FirstName, String Email, int BirthYear, int HireYear, int Bonus) {
        super(LastName, FirstName, Email, BirthYear, HireYear);
        bonus = Bonus;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    @Override
    public int computeSalary() {
        return 1000 * (2021 - hireYear) + bonus;
    }
}
