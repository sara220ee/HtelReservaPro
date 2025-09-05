package model.entity;


import java.io.Serializable;

public class Chambre implements Identifiable , Serializable {
    private int id;
    private int numero;
    private double prix;
    private TypeChambre type;
    private String Image ;

    public Chambre(int id , int numero, double prix, TypeChambre type , String image) {
        setId(id);
        setNumero(numero);
        setPrix(prix);
        setType(type);
        setImage(image);
    }
    public Chambre() {
        setId(0);
        setNumero(1);
        setPrix(1);
        setType(TypeChambre.SINGLE);
        setImage("chambre.png");
    }
    public String getImage() {
        return Image;
    }
    public void setImage(String image) {
        this.Image = image;
    }


    public void setId(int id) {
        this.id = id;
    }
    @Override
    public int getId() {
        return id;
    }

    public int getNumero() { return numero; }
    public double getPrix() { return prix; }
    public TypeChambre getType() { return type; }

    public void setNumero(int numero) {
        if (numero < 0) throw new ValeurInvalideException("Le numéro de chambre doit être positif !");
        this.numero = numero;
    }

    public void setPrix(double prix) {
        if (prix <= 0) throw new ValeurInvalideException("Le prix doit être positif !");
        this.prix = prix;
    }

    public void setType(TypeChambre type) throws ValeurInvalideException {
        if (type == null) {
            throw new ValeurInvalideException("Type de chambre invalide !");
        }
        this.type = type;
    }
    @Override
    public String toString() {
        return "id:" + id + " " + type + ", numero=" + numero;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Chambre chambre = (Chambre) obj;

        return id == chambre.id &&
                numero == chambre.numero &&
                Double.compare(chambre.prix, prix) == 0 &&
                type == chambre.type;
    }
}