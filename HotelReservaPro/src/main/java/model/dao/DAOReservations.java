package model.dao;

import model.DataAccessLayerReservation;
import model.entity.Reservation;
import model.entity.ValeurInvalideException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DAOReservations implements DataAccessLayerReservation {
    private ArrayList<Reservation> reservations;
    private static int idCourant;
    private final String filename;

    public DAOReservations(String filename) {
        this.filename = filename;
        reservations = new ArrayList<>();
        idCourant = 1;
        try {
            load();
        } catch (Exception e) {
            throw new ValeurInvalideException("Erreur lors du chargement des réservations : " + e.getMessage());
        }
    }
    public DAOReservations() {
        this.filename = "C:\\Users\\sarar\\IdeaProjects\\HotelReservaPro\\src\\main\\resources\\Reservations\\Reservations.dat";
        reservations = new ArrayList<>();
        idCourant = 1;
        try {
            load();
        } catch (Exception e) {
            throw new ValeurInvalideException("Erreur lors du chargement des réservations : " + e.getMessage());
        }
    }

    @Override
    public int addReservation(Reservation reservation) {
        if (reservation == null) throw new ValeurInvalideException("Réservation invalide (null)");
        reservation.setId(idCourant++);
        reservations.add(reservation);
        save();
        return reservation.getId();
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId() == reservation.getId()) {
                reservations.set(i, reservation);
                save();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteReservation(int id) {
        boolean removed = reservations.removeIf(r -> r.getId() == id);
        if (removed) save();
        return removed;
    }

    @Override
    public boolean deleteReservation(Reservation reservation) {
        boolean removed = reservations.remove(reservation);
        if (removed) save();
        return removed;
    }

    @Override
    public Reservation getReservationById(int id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId() == id) {
                return reservation;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Reservation> getList() {
        return reservations;
    }

    @Override
    public void save() {
        try {
            File file = new File(filename);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(reservations);
            }

        } catch (IOException e) {
            throw new ValeurInvalideException("Erreur lors de l'enregistrement des réservations : " + e.getMessage());
        }
    }

    @Override
    public void load() {
        File file = new File(filename);
        if (!file.exists()) {
            reservations = new ArrayList<>();
            idCourant = 1;
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();

            if (obj instanceof ArrayList<?> list) {
                reservations = new ArrayList<>();
                for (Object item : list) {
                    if (!(item instanceof Reservation)) {
                        throw new ValeurInvalideException("Fichier corrompu : élément non-Réservation détecté.");
                    }
                    reservations.add((Reservation) item);
                }

                int maxId = 0;
                for (Reservation r : reservations) {
                    if (r.getId() > maxId) {
                        maxId = r.getId();
                    }
                }
                idCourant = maxId + 1;
            } else {
                throw new ValeurInvalideException("Fichier corrompu : contenu non reconnu.");
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new ValeurInvalideException("Erreur lors du chargement : " + e);
        }
    }
    @Override
    public boolean isChambreDisponible(int chambreId, LocalDate debut, LocalDate fin) {
        for (Reservation r : reservations) {
            if (r.getChambre().getId() == chambreId) {
                LocalDate d1 = r.getDateDebut();
                LocalDate d2 = r.getDateFin();
                if (!(fin.isBefore(d1) || debut.isAfter(d2))) {
                    return false;
                }
            }
        }
        return true;
    }




    @Override
    public String toString() {
        return "DAOReservations{" + "reservations=" + reservations + '}';
    }

    public static void main(String[] args) {
        try {
            DAOReservations dao = new DAOReservations("reservations.dat");
            System.out.println(dao);
        } catch (ValeurInvalideException e) {
            System.out.println("Erreur critique : " + e.getMessage());
        }
    }
}
