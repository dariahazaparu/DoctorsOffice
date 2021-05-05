// https://stackabuse.com/reading-and-writing-csvs-in-java/
package office.Office.IO;

public class CSVWriteService {
    public static CSVWriteService serviceInstance = null;

    private CSVWriteService() {}

    public static CSVWriteService getInstance() {
        if (serviceInstance == null) {
            serviceInstance = new CSVWriteService();
        }

        return serviceInstance;
    }
}
