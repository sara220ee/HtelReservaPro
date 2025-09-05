package authentication;
import java.util.List;
import model.entity.Admin;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SERMapAuthenticator extends Authenticator {
    private final Map<String, String> admins;
    public SERMapAuthenticator(String filePath) {
        admins = new HashMap<>();
        loadAdminsFromFile(filePath);
    }
    public SERMapAuthenticator() {
        admins = new HashMap<>();
        admins.put("sara.rahmouni@gmail.com", "sara123");
    }
    public void setAdmins(Map<String, String> admins) {
        this.admins.putAll(admins);
    }
    public Map<String, String> getAdmins() {
        return admins;
    }
    @Override
    public String toString() {
        return "SERMapAuthenticator{" +
                "admins=" + admins +
                '}';
    }
    private void loadAdminsFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Admin> serializedAdmins = (List<Admin>) ois.readObject();
            for (Admin admin : serializedAdmins) {
                String username = admin.getEmail();
                String motDePasseHache = admin.getMotDePasseHache();
                admins.put(username, motDePasseHache);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement des administrateurs : " + e.getMessage());
            e.printStackTrace();
        }
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
/*
Quand tu mets implements Serializable dans une classe, tu lui dis que cette classe peut être convertie en un flux d'octets
*/
