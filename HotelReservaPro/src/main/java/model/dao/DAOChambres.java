package model.dao;

import model.DataAccessLayerChambre;
import model.entity.Chambre;
import model.entity.TypeChambre;
import model.entity.ValeurInvalideException;

import java.io.*;
import java.util.ArrayList;

public class DAOChambres implements DataAccessLayerChambre {
    private ArrayList<Chambre> chambres;
    private static int idCourant;
    private final String filename;

    public DAOChambres(String filename) {
        this.filename = filename;
        chambres = new ArrayList<>();
        idCourant = 1;
        try {
            load();
        } catch (Exception e) {
            throw new ValeurInvalideException("Erreur lors du chargement des chambres : " + e.getMessage());
        }
    }
    public DAOChambres() {
        this.filename = "C:\\Users\\sarar\\IdeaProjects\\HotelReservaPro\\src\\main\\resources\\Chambres\\Chambres.dat";
        chambres = new ArrayList<>();
        idCourant = 1;
        try {
            load();
        } catch (Exception e) {
            throw new ValeurInvalideException("Erreur lors du chargement des chambres : " + e.getMessage());
        }
    }


    @Override
    public int addChambre(Chambre chambre) {
        if (chambre == null) throw new ValeurInvalideException("Chambre invalide (null)");
        chambre.setId(idCourant++);
        chambres.add(chambre);
        save();
        return chambre.getId();
    }

    @Override
    public boolean updateChambre(Chambre chambre) {
        for (int i = 0; i < chambres.size(); i++) {
            if (chambres.get(i).getId() == chambre.getId()) {
                chambres.set(i, chambre);
                save();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteChambre(int id) {
        boolean removed = chambres.removeIf(c -> c.getId() == id);
        if (removed) save();
        return removed;
    }

    @Override
    public boolean deleteChambre(Chambre chambre) {
        boolean removed = chambres.remove(chambre);
        if (removed) save();
        return removed;
    }

    @Override
    public Chambre getChambreById(int id) {
        for (Chambre chambre : chambres) {
            if (chambre.getId() == id) {
                return chambre;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Chambre> getList() {
        return chambres;
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
                oos.writeObject(chambres);
            }

        } catch (IOException e) {
            throw new ValeurInvalideException("Erreur lors de l'enregistrement des chambres : " + e.getMessage());
        }
    }

    @Override
    public void load() {
        File file = new File(filename);
        if (!file.exists()) {
            chambres = new ArrayList<>();
            idCourant = 1;
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();

            if (obj instanceof ArrayList<?> list) {
                chambres = new ArrayList<>();
                for (Object item : list) {
                    if (!(item instanceof Chambre)) {
                        throw new ValeurInvalideException("Fichier corrompu : élément non-Chambre détecté.");
                    }
                    chambres.add((Chambre) item);
                }

                int maxId = 0;
                for (Chambre c : chambres) {
                    if (c.getId() > maxId) {
                        maxId = c.getId();
                    }
                }
                idCourant = maxId + 1;
            } else {
                throw new ValeurInvalideException("Fichier corrompu : contenu non reconnu.");
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new ValeurInvalideException("Erreur lors du chargement : " + e.getMessage());
        }
    }
    @Override
    public void importerChambresDepuisCSV(String nomFichier) throws ValeurInvalideException {
        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                try {
                    String[] champs = ligne.split(";");
                    int numero = Integer.parseInt(champs[0].trim());
                    double prix = Double.parseDouble(champs[1].trim().replace(',', '.'));
                    TypeChambre type = TypeChambre.valueOf(champs[2].trim().toUpperCase());
                    String image = champs[3].trim();
                    Chambre chambre = new Chambre(0, numero, prix, type, image);
                    addChambre(chambre);
                } catch (Exception e) {
                    System.err.println("Ligne ignorée (format invalide) : " + ligne + " -> " + e.getMessage());

                }
            }
        } catch (IOException e) {
            throw new ValeurInvalideException("Erreur lors de l'import CSV : " + e.getMessage());
        }
    }

    @Override

    public void exporterChambresVersCSV(String nomFichier) throws ValeurInvalideException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier))) {
            for (Chambre chambre : getList()) {
                writer.write(String.format("%d;%.2f;%s;%s\n",
                        chambre.getNumero(),
                        chambre.getPrix(),
                        chambre.getType().name(),
                        chambre.getImage()));
            }
        } catch (IOException e) {
            throw new ValeurInvalideException("Erreur lors de l'export CSV : " + e.getMessage());
        }

    }


    @Override
    public String toString() {
        return "DAOChambres{" + "chambres=" + chambres + '}' ;
    }

    public static void main(String[] args) {
        try {
            DAOChambres dao = new DAOChambres("chambres.dat");

            dao.addChambre(new Chambre(0, 101, 89.99, model.entity.TypeChambre.SINGLE, "single.jpg"));
            dao.addChambre(new Chambre(0, 102, 120.00, model.entity.TypeChambre.DOUBLE, "double.jpg"));

            Chambre c = dao.getChambreById(1);
            if (c != null) {
                c.setPrix(99.99);
                dao.updateChambre(c);
            }

            dao.deleteChambre(2);
            System.out.println(dao);

        } catch (ValeurInvalideException e) {
            System.out.println("Erreur critique : " + e.getMessage());
        }
    }

}
