package model;

import model.entity.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;

public interface DataAccessLayerReservation {
    int addReservation(Reservation reservation);
    boolean updateReservation(Reservation reservation);
    boolean deleteReservation(int id);
    boolean deleteReservation(Reservation reservation);
    Reservation getReservationById(int id);
    ArrayList<Reservation> getList();
    void save();
    void load();
    boolean isChambreDisponible(int chambreId, LocalDate debut, LocalDate fin);
}
