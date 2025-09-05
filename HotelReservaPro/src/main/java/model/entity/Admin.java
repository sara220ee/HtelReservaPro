package model.entity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Admin extends Person {
    private String motDePasseHache;

    public Admin(int id, String nom, String prenom , String email , String motDePasse) {
        super(id, nom, prenom , email);
        setMotDePasse(motDePasse);
    }
    public Admin(){
        super();
        setMotDePasse("123456");
    }
    public String getMotDePasseHache() { return motDePasseHache; }

    public void setMotDePasse(String motDePasse) {
        if (motDePasse == null || motDePasse.length() < 6) {
            throw new ValeurInvalideException("Le mot de passe doit contenir au moins 6 caractères.");
        }
        this.motDePasseHache = hacherMotDePasse(motDePasse);
    }

    protected String hacherMotDePasse(String motDePasse) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(motDePasse.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur de hachage du mot de passe", e);
        }
    }
}

