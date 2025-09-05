package model.entity;
import java.io.Serializable;
import java.util.Objects;

public class Person implements Identifiable , Serializable {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    public Person() {
        setId(0);
        setNom("sara");
        setPrenom("Rahmouni");
        setEmail("sara@gmail.com");
    }
    public Person(int id, String nom, String prenom , String email) {
        setId(id);
        setNom(nom);
        setPrenom(prenom);
        setEmail(email);
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new ValeurInvalideException("Email invalide !");
        }
        this.email = email;
    }
    public void setId(int id) {
        if (id < 0 ) {
            throw new ValeurInvalideException("L'ID ne peut pas être Negative.");
        }
        this.id = id;
    }

    public void setNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new ValeurInvalideException("Le nom ne peut pas être vide.");
        }
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        if (prenom == null || prenom.trim().isEmpty()) {
            throw new ValeurInvalideException("Le prénom ne peut pas être vide.");
        }
        this.prenom = prenom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person personne = (Person) o;
        return id == personne.id &&
                nom.equals(personne.nom) &&
                prenom.equals(personne.prenom) &&
                email.equals(personne.email);
    }


    @Override
    public String toString() {
        return nom + " " + prenom + " (ID: " + id + ")";
    }
}

