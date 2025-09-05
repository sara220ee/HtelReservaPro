package view;

import model.entity.Chambre;
import model.entity.Client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TabbedPanel extends JTabbedPane {
    private ClientsPanel clientsPanel;
    private ChambresPanel chambresPanel;

    public TabbedPanel() {
        setBackground(new Color(240, 248, 255));
        setFont(new Font("Segoe UI", Font.BOLD, 12));

        clientsPanel = new ClientsPanel();
        chambresPanel = new ChambresPanel();
        addTab("  Clients  ", clientsPanel);
        addTab("  Chambres  ", chambresPanel);
    }
    public ClientsPanel getClientsPanel() {
        return clientsPanel;
    }

    public ChambresPanel getChambresPanel() {
        return chambresPanel;
    }
    public void updateClientsTable(ArrayList<Client> clients) {
        clientsPanel.updateTable(clients);
    }
    public void updateChambresTable(ArrayList<Chambre> chambres) {
        chambresPanel.updateTable(chambres);
    }
    public void clearClientsTable() {
        clientsPanel.updateTable(new ArrayList<>());
    }
    public void clearChambresTable() {
        chambresPanel.updateTable(new ArrayList<>());
    }

}
