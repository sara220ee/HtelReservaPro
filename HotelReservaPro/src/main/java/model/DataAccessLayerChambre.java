package model;

import model.entity.Chambre;
import java.util.ArrayList;

public interface DataAccessLayerChambre {
    int addChambre(Chambre chambre);
    boolean updateChambre(Chambre chambre);
    boolean deleteChambre(int id);
    boolean deleteChambre(Chambre chambre);
    Chambre getChambreById(int id);
    ArrayList<Chambre> getList();
    void importerChambresDepuisCSV(String cheminFichier);
    void exporterChambresVersCSV(String cheminFichier);
    void save();
    void load();
}
