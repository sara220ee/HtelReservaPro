package authentication;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVMapAuthenticator extends Authenticator {

    private final Map<String, String> admins;

    public CSVMapAuthenticator(String filePath) {
        admins = new HashMap<>();
        loadAdminsFromCSV(filePath);
    }
    public CSVMapAuthenticator() {
        admins = new HashMap<>();
        admins.put("sara.rahmouni@gmail.com", "sara123");
    }
    public void setAdmins(Map<String, String> admins) {
        this.admins.putAll(admins);
    }
    public Map<String, String> getAdmins() {
        return admins;
    }

    private void loadAdminsFromCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String username = parts[3];
                    String motDePasseHache = parts[4];
                    admins.put(username, motDePasseHache);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        return "CSVMapAuthenticator{" + "admins=" + admins + '}';
    }
    @Override
    protected boolean isLoginExists(String username) {
        return admins.containsKey(username);
    }
    @Override
    protected String getPassword(String username) {
        return admins.get(username);
    }
}

