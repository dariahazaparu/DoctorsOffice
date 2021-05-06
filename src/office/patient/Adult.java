package office.patient;

public class Adult extends Patient{

    private boolean healthInsurance;

    public Adult(String LastName, String FirstName, int BirthYear, String CNP, String Tel, boolean HealthInsurance) {
        super(LastName, FirstName, BirthYear, CNP, Tel);
        healthInsurance = HealthInsurance;
    }

    public boolean isHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(boolean healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    @Override
    public void displayPatient() {
        System.out.println("\t" + lastName + " " + firstName + " -> Insurance: " + healthInsurance);
    }

    public void show() {
        super.show();
        System.out.println("Health Insurance: " + healthInsurance);
    }
}
