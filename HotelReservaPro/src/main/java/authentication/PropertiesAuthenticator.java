package authentication;

import java.io.*;
import java.util.Properties;
import java.util.Map;

public class PropertiesAuthenticator extends Authenticator {
    private final Properties users;

    public PropertiesAuthenticator() {
        users = new Properties();
        users.setProperty("sara.rahmouni@gmail.com", "sara123");
    }

    public PropertiesAuthenticator(String filePath) {
        users = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            users.load(fis);
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier : " + e.getMessage());
        }
    }

    public Properties getUsers() {
        return users;
    }

    @Override
    protected boolean isLoginExists(String username) {
        return users.containsKey(username);
    }

    @Override
    protected String getPassword(String username) {
        return users.getProperty(username);
    }

    @Override
    public String toString() {
        return "PropertiesAuthenticator{" + "users=" + users + '}';
    }
}
