package office.pacient;

public class Child extends Patient {

    private String parentName;

    public Child(String LastName, String FirstName, int BirthYear, String CNP, String Tel, String ParentName) {
        super(LastName, FirstName, BirthYear, CNP, Tel);
        parentName = ParentName;
    }

    @Override
    public void displayPatient() {
        System.out.println("\t" + lastName + " " + firstName + " -> Parent name: " + parentName);
    }

    public void show() {
        super.show();
        System.out.println("Parent name: " + parentName);
    }
}
