package office.Office.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AuditService {
    public static AuditService serviceInstance = null;
    private BufferedWriter csvBuffer;

    private AuditService() {
        open();
    }

    private void open(){
        try {
            FileWriter csvWriter = new FileWriter("./resources/output/audit.csv", true);
            csvBuffer = new BufferedWriter(csvWriter);
        } catch (IOException err) {
            System.out.println("Failed to open audit file.");
        }
    }

    public static AuditService getInstance() {
        if (serviceInstance == null) {
            serviceInstance = new AuditService();

        }

        return serviceInstance;
    }

    public void print(String action){
        try {
            LocalDateTime t = LocalDateTime.now();
            csvBuffer.write(action + "," + t.getDayOfMonth() + "-" + t.getMonth() + "-" + t.getYear() + " "
                    + t.getHour() + ":" + t.getMinute() + ":" + t.getSecond() + '\n');
        } catch (IOException err) {
            System.out.println("Failed to write in audit file.");
        }
    }

    public void close() {
        try {
            csvBuffer.flush();
            csvBuffer.close();
        } catch (IOException err) {
            System.out.println("Failed to close audit file.");
        }
    }
}
