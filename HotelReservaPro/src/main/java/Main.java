import controller.ControllerHotel;
import model.dao.DAOChambres;
import model.dao.DAOClients;
import model.dao.DAOReservations;
import view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            DAOClients clientsDAO = new DAOClients();
            DAOChambres chambresDAO = new DAOChambres();
            DAOReservations reservationsDAO = new DAOReservations();
            MainFrame frame = new MainFrame();

            ControllerHotel controller = new ControllerHotel(frame, clientsDAO, chambresDAO, reservationsDAO);
            controller.run();
        });
    }
}
