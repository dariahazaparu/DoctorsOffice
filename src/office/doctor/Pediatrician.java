package office.doctor;

import java.time.LocalDateTime;

public class Pediatrician extends Doctor {

    private int bonus;

    public Pediatrician(String LastName, String FirstName, String Email, int HireYear, int Bonus) {
        super(LastName, FirstName, Email, HireYear);
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
        LocalDateTime time = LocalDateTime.now();
        return 1000 * (time.getYear() - hireYear) + bonus;
    }
}
