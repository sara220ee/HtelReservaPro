package view;

import controller.ControllerHotel;

import javax.swing.*;
import java.awt.*;

public class MenuBarPanel extends JMenuBar {
    public JMenuItem menuItemAddClient;
    public JMenuItem menuItemAddChambre;
    public JMenuItem menuItemDeleteClient;
    public JMenuItem menuItemDeleteChambre;
    public JMenuItem menuItemImporterClients;
    public JMenuItem menuItemImporterChambres;
    public JMenuItem menuItemExporterClients;
    public JMenuItem menuItemExporterChambres;
    public JMenuItem logoutItem ;
    private JMenu themeMenu;
    private JMenuItem themeLight;
    private JMenuItem themeDark;
    private JMenuItem itemFormatDate;


    public MenuBarPanel() {

        JMenu sessionMenu = createStyledMenu("Session");
        logoutItem = createStyledMenuItem("Déconnecter");
        JMenuItem menuItemQuit = new JMenuItem("Quitter");
        menuItemQuit.addActionListener(e -> System.exit(0));
        sessionMenu.add(logoutItem);
        sessionMenu.addSeparator();
        sessionMenu.add(menuItemQuit);

        themeMenu = createStyledMenu("Thème");
        themeLight = createStyledMenuItem("Thème Clair");
        themeDark = createStyledMenuItem("Thème Normal");

        // Ajouter les actions au menu Thème
        themeMenu.add(themeLight);
        themeMenu.add(themeDark);

        // Ajouter le menu Thème à la barre de menu

        JMenu importerMenu = createStyledMenu("Importer");
        menuItemImporterClients = createStyledMenuItem("Importer Clients");
        menuItemImporterChambres = createStyledMenuItem("Importer Chambres");
        importerMenu.add(menuItemImporterClients);
        importerMenu.add(menuItemImporterChambres);

        JMenu exporterMenu = createStyledMenu("Exporter");
        menuItemExporterClients = createStyledMenuItem("Exporter Clients");
        menuItemExporterChambres = createStyledMenuItem("Exporter Chambres");
        exporterMenu.add(menuItemExporterClients);
        exporterMenu.add(menuItemExporterChambres);

        JMenu ajouterMenu = createStyledMenu("Ajouter");
        menuItemAddClient = createStyledMenuItem("Ajouter Client");
        menuItemAddChambre = createStyledMenuItem("Ajouter Chambre");
        ajouterMenu.add(menuItemAddClient);
        ajouterMenu.add(menuItemAddChambre);

        JMenu supprimerMenu = createStyledMenu("Supprimer");
        menuItemDeleteClient = createStyledMenuItem("Supprimer Client");
        menuItemDeleteChambre = createStyledMenuItem("Supprimer Chambre");
        supprimerMenu.add(menuItemDeleteClient);
        supprimerMenu.add(menuItemDeleteChambre);

        JMenu menuParametres = createStyledMenu("Paramètres");
        itemFormatDate = createStyledMenuItem("Changer format date");
        itemFormatDate.setActionCommand("Changer Format Date");
        menuParametres.add(itemFormatDate);

        logoutItem.setActionCommand("Déconnecter");
        menuItemAddClient.setActionCommand("Ajouter Client");
        menuItemAddChambre.setActionCommand("Ajouter Chambre");
        menuItemDeleteClient.setActionCommand("Supprimer Client");
        menuItemDeleteChambre.setActionCommand("Supprimer Chambre");
        menuItemImporterClients.setActionCommand("Importer Clients");
        menuItemImporterChambres.setActionCommand("Importer Chambres");
        menuItemExporterClients.setActionCommand("Exporter Clients");
        menuItemExporterChambres.setActionCommand("Exporter Chambres");
        themeLight.setActionCommand("Clair");
        themeDark.setActionCommand("Darck");

        add(sessionMenu);
        add(menuParametres);
        add(themeMenu);
        add(importerMenu);
        add(exporterMenu);
        add(ajouterMenu);
        add(supprimerMenu);
    }
    public void setController(ControllerHotel controller) {
        logoutItem.addActionListener(controller);
        menuItemAddClient.addActionListener(controller);
        menuItemAddChambre.addActionListener(controller);
        menuItemDeleteClient.addActionListener(controller);
        menuItemDeleteChambre.addActionListener(controller);
        menuItemImporterClients.addActionListener(controller);
        menuItemImporterChambres.addActionListener(controller);
        menuItemExporterClients.addActionListener(controller);
        menuItemExporterChambres.addActionListener(controller);
        themeLight.addActionListener(controller);
        themeDark.addActionListener(controller);
        itemFormatDate.addActionListener(controller);
    }
    private JMenu createStyledMenu(String title) {
        JMenu menu = new JMenu(title);
        menu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menu.setForeground(Color.darkGray);
        return menu;
    }

    private JMenuItem createStyledMenuItem(String title) {
        JMenuItem item = new JMenuItem(title);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return item;
    }
}
