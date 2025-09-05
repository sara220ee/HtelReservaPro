package model.entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Reservation implements Identifiable , Serializable {
    int id;
    private Client client;
    private Chambre chambre;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public Reservation(int id , Client client, Chambre chambre, LocalDate dateDebut, LocalDate dateFin) {
        setId(id);
        setClient(client);
        setChambre(chambre);
        setDateDebut(dateDebut);
        setDateFin(dateFin);
    }
    public Reservation() {
        this.id = 0;
        this.client = null;
        this.chambre = null;
        this.dateDebut = null;
        this.dateFin = null;
    }
    public Client getClient() { return client; }
    public Chambre getChambre() { return chambre; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public void setClient(Client client) {
        if (client == null) throw new ValeurInvalideException("Le client ne peut pas être null !");
        this.client = client;
    }

    public void setChambre(Chambre chambre) {
        if (chambre == null) throw new ValeurInvalideException("La chambre ne peut pas être null !");
        this.chambre = chambre;
    }

    public void setDateDebut(LocalDate dateDebut) {
        if (dateDebut == null || (this.dateFin != null && dateDebut.isAfter(this.dateFin))) {
            throw new ValeurInvalideException("La date de début doit être avant la date de fin.");
        }
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDate dateFin) {
        if (dateFin == null || (this.dateDebut != null && dateFin.isBefore(this.dateDebut))) {
            throw new ValeurInvalideException("La date de fin doit être après la date de début.");
        }
        this.dateFin = dateFin;
    }
    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", client=" + client + ", chambre=" + chambre + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id &&
                Objects.equals(client, that.client) &&
                Objects.equals(chambre, that.chambre) &&
                Objects.equals(dateDebut, that.dateDebut) &&
                Objects.equals(dateFin, that.dateFin);
    }
}

