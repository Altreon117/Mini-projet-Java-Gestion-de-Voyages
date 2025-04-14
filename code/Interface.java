import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Interface extends JFrame {

    private CardLayout mainLayout;
    private JPanel mainPanel;

    private List<Client> clients = new ArrayList<>();
    private List<Stay> stays = new ArrayList<>();
    private List<HotelBooking> hotelBookings = new ArrayList<>();
    private List<PlaneTicket> planeTickets = new ArrayList<>();

    public Interface() {
        setTitle("Voyage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        mainLayout = new CardLayout();
        mainPanel = new JPanel(mainLayout);

        // Création des différents panneaux
        JPanel clientsPanel = createClientsPanel();
        JPanel staysPanel = createStaysPanel();
        JPanel hotelBookingsPanel = createHotelBookingsPanel();
        JPanel planeTicketsPanel = createPlaneTicketsPanel();
        JPanel searchPanel = createSearchPanel();

        // Ajout des panneaux au panneau principal avec des noms pour la navigation
        mainPanel.add(clientsPanel, "Clients");
        mainPanel.add(staysPanel, "Séjours");
        mainPanel.add(hotelBookingsPanel, "Réservations d'hôtel");
        mainPanel.add(planeTicketsPanel, "Billets d'avion");
        mainPanel.add(searchPanel, "Recherche & Affichage");

        // Création du menu principal
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem clientsItem = new JMenuItem("Clients");
        clientsItem.addActionListener(e -> mainLayout.show(mainPanel, "Clients"));
        menu.add(clientsItem);

        JMenuItem staysItem = new JMenuItem("Séjours");
        staysItem.addActionListener(e -> {
            JPanel newStaysPanel = createStaysPanel();
            mainPanel.add(newStaysPanel, "Séjours");
            mainLayout.show(mainPanel, "Séjours");
        });
        menu.add(staysItem);

        JMenuItem hotelBookingsItem = new JMenuItem("Réservations d'hôtel");
        hotelBookingsItem.addActionListener(e -> mainLayout.show(mainPanel, "Réservations d'hôtel"));
        menu.add(hotelBookingsItem);

        JMenuItem planeTicketsItem = new JMenuItem("Billets d'avion");
        planeTicketsItem.addActionListener(e -> mainLayout.show(mainPanel, "Billets d'avion"));
        menu.add(planeTicketsItem);

        JMenuItem searchItem = new JMenuItem("Recherche & Affichage");
        searchItem.addActionListener(e -> mainLayout.show(mainPanel, "Recherche & Affichage"));
        menu.add(searchItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createClientsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField nameField = new JTextField(20);
        JTextField IDField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField travelDocsField = new JTextField(20);
        JButton addButton = new JButton("Ajouter Client");
        
        formPanel.add(new JLabel("Nom Prénom:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("ID:"));
        formPanel.add(IDField);
        formPanel.add(new JLabel("Adresse:"));
        formPanel.add(addressField);
        formPanel.add(new JLabel("")); // Espace
        formPanel.add(addButton);

        DefaultTableModel clientTableModel = new DefaultTableModel(new Object[]{"Nom", "ID", "Adresse"}, 0);
        JTable clientTable = new JTable(clientTableModel);
        JScrollPane tableScrollPane = new JScrollPane(clientTable);

        JPanel detailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel detailsLabel = new JLabel("Sélectionnez un client pour voir les détails.");
        detailsPanel.add(detailsLabel);
        JButton associateStayButton = new JButton("Associer un séjour");
        detailsPanel.add(associateStayButton);
        associateStayButton.setEnabled(false); // Désactivé par défaut

        addButton.addActionListener(e -> {
            String nom = nameField.getText();
            int id = Integer.parseInt(IDField.getText());
            String addresse = addressField.getText();
            if (!nom.isEmpty()) {
                Client newClient = new Client(nom, id, addresse);
                clients.add(newClient);
                clientTableModel.addRow(new Object[]{newClient.getNom(), newClient.getId(), newClient.getAdresse()});
                // Réinitialiser les champs
                nameField.setText("");
                IDField.setText("");
                addressField.setText("");
                travelDocsField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Le nom et le prénom sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        clientTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = clientTable.getSelectedRow();
                if (selectedRow != -1) {
                    Client selectedClient = clients.get(selectedRow);
                    detailsLabel.setText("Détails de: " + selectedClient.getNom());
                    associateStayButton.setEnabled(true);
                    // Ici, vous pourriez afficher les séjours associés à ce client dans une autre partie de l'interface
                } else {
                    detailsLabel.setText("Sélectionnez un client pour voir les détails.");
                    associateStayButton.setEnabled(false);
                }
            }
        });

        associateStayButton.addActionListener(e -> {
            int selectedRow = clientTable.getSelectedRow();
            if (selectedRow != -1) {
                Client selectedClient = clients.get(selectedRow);

                // Créer un tableau des séjours disponibles avec une description formatée
                String[] stayDescriptions = new String[stays.size()];
                for (int i = 0; i < stays.size(); i++) {
                    Stay stay = stays.get(i);
                    String debutFormatted = stay.getStart().getJours() + "/" + stay.getStart().getMois() + "/" + stay.getStart().getAnnee();
                    String finFormatted = stay.getEnd().getJours() + "/" + stay.getEnd().getMois() + "/" + stay.getEnd().getAnnee();
                    stayDescriptions[i] = "Début : " + debutFormatted + ", Fin : " + finFormatted + ", Transport : " + stay.getTransport() + ", Chambre Hôtel : " + stay.getReservedStayHotel();
                }

                String selectedDescription = (String) JOptionPane.showInputDialog(
                        Interface.this,
                        "Sélectionner un séjour à associer à " + selectedClient.getNom() + ":",
                        "Associer un séjour",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        stayDescriptions, // On utilise notre tableau de descriptions formatées ici !
                        stayDescriptions.length > 0 ? stayDescriptions[0] : null
                );

                Stay selectedStay = null;
                if (selectedDescription != null) {
                    // On doit retrouver l'objet Stay correspondant à la description choisie
                    for (Stay stay : stays) {
                        String debutFormatted = stay.getStart().getJours() + "/" + stay.getStart().getMois() + "/" + stay.getStart().getAnnee();
                        String finFormatted = stay.getEnd().getJours() + "/" + stay.getEnd().getMois() + "/" + stay.getEnd().getAnnee();
                        String description = "Début : " + debutFormatted + ", Fin : " + finFormatted + ", Transport : " + stay.getTransport() + ", Chambre Hôtel : " + stay.getReservedStayHotel();
                        if (description.equals(selectedDescription)) {
                            selectedStay = stay;
                            break;
                        }
                    }

                    // Si un séjour a été trouvé, l'associer au client
                    if (selectedStay != null) {
                        selectedClient.addStayClient(selectedStay);
                        JOptionPane.showMessageDialog(Interface.this, "Le séjour a été associé à " + selectedClient.getNom() + ".", "Séjour associé", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            } else {
                JOptionPane.showMessageDialog(Interface.this, "Veuillez sélectionner un client.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(detailsPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createStaysPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(0, 4, 5, 5)); // Utilisation de 4 colonnes pour les jours, mois, années

        // Champs pour la date de début
        JLabel startDateLabel = new JLabel("Date de début:");
        JTextField startDayField = new JTextField(2);
        JTextField startMonthField = new JTextField(2);
        JTextField startYearField = new JTextField(4);

        // Champs pour la date de fin
        JLabel endDateLabel = new JLabel("Date de fin:");
        JTextField endDayField = new JTextField(2);
        JTextField endMonthField = new JTextField(2);
        JTextField endYearField = new JTextField(4);

        JComboBox<String> planeTicketCombo = new JComboBox<>();
        JComboBox<String> hotelBookingCombo = new JComboBox<>();
        JButton addStayButton = new JButton("Créer/Modifier Séjour");

        planeTicketCombo.addItem("Sélectionner un billet");
        for (PlaneTicket ticket : planeTickets) {
            planeTicketCombo.addItem(ticket.getReference());
        }

        hotelBookingCombo.addItem("Sélectionner une réservation");
        for (HotelBooking booking : hotelBookings) {
            hotelBookingCombo.addItem(String.valueOf(booking.getNombreRoom()));
        }

        // Ajout des composants pour la date de début
        formPanel.add(startDateLabel);
        formPanel.add(new JLabel("Jour:"));
        formPanel.add(new JLabel("Mois:"));
        formPanel.add(new JLabel("Année:"));
        formPanel.add(startDayField);
        formPanel.add(startMonthField);
        formPanel.add(startYearField);

        // Ajout des composants pour la date de fin
        formPanel.add(endDateLabel);
        formPanel.add(new JLabel("Jour:"));
        formPanel.add(new JLabel("Mois:"));
        formPanel.add(new JLabel("Année:"));
        formPanel.add(endDayField);
        formPanel.add(endMonthField);
        formPanel.add(endYearField);

        formPanel.add(new JLabel("Billet d'avion (Référence):"));
        formPanel.add(planeTicketCombo);
        formPanel.add(new JLabel("Réservation d'hôtel (ID):"));
        formPanel.add(hotelBookingCombo);
        formPanel.add(new JLabel("")); // Espace
        formPanel.add(addStayButton);

        DefaultTableModel stayTableModel = new DefaultTableModel(new Object[]{"Début", "Fin", "Billet", "Chambre Hôtel"}, 0);
        for (Stay stay : stays) {
            stayTableModel.addRow(new Object[]{stay.getStart().getJours() + "/" + stay.getStart().getMois() + "/" + stay.getStart().getAnnee(), stay.getEnd().getJours() + "/" + stay.getEnd().getMois() + "/" + stay.getEnd().getAnnee(), stay.getTransport(), stay.getReservedStayHotel()});
        }
        JTable stayTable = new JTable(stayTableModel);
        JScrollPane tableScrollPane = new JScrollPane(stayTable);

        addStayButton.addActionListener(e -> {
            try {
                int startDay = Integer.parseInt(startDayField.getText());
                int startMonth = Integer.parseInt(startMonthField.getText());
                int startYear = Integer.parseInt(startYearField.getText());
                Date startDate = new Date(startDay, startMonth, startYear);
                String startDateFormated = startDay + "/" + startMonth + "/" + startYear;

                int endDay = Integer.parseInt(endDayField.getText());
                int endMonth = Integer.parseInt(endMonthField.getText());
                int endYear = Integer.parseInt(endYearField.getText());
                Date endDate = new Date(endDay, endMonth, endYear);
                String endDateFormated = endDay + "/" + endMonth + "/" + endYear;

                String selectedTicket = (String) planeTicketCombo.getSelectedItem();
                String selectedBooking = (String) hotelBookingCombo.getSelectedItem();

                if (!selectedTicket.equals("Sélectionner un billet") && !selectedBooking.equals("Sélectionner une réservation")) {
                    Stay newStay = new Stay(startDate, endDate, selectedTicket, selectedBooking); // Ici, tu peux stocker l'objet Date ou sa représentation en chaîne
                    stays.add(newStay);
                    stayTableModel.addRow(new Object[]{startDateFormated, endDateFormated, selectedTicket, selectedBooking}); // Affichage des objets Date dans le tableau
                    // Réinitialiser les champs
                    startDayField.setText("");
                    startMonthField.setText("");
                    startYearField.setText("");
                    endDayField.setText("");
                    endMonthField.setText("");
                    endYearField.setText("");
                    planeTicketCombo.setSelectedIndex(0);
                    hotelBookingCombo.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(this, "Veuillez sélectionner un billet d'avion et une réservation d'hôtel.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer des nombres valides pour les jours, les mois et les années.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createHotelBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(0, 4, 5, 5)); // Utilisation de 4 colonnes pour organiser les dates

        JComboBox<String> roomTypeCombo = new JComboBox<>(new String[]{"Single", "Double", "Family"});

        // Champs pour la date de début
        JLabel startDateLabel = new JLabel("Date de début:");
        JTextField startDayField = new JTextField(2);
        JTextField startMonthField = new JTextField(2);
        JTextField startYearField = new JTextField(4);

        // Champs pour la date de fin
        JLabel endDateLabel = new JLabel("Date de fin:");
        JTextField endDayField = new JTextField(2);
        JTextField endMonthField = new JTextField(2);
        JTextField endYearField = new JTextField(4);

        JTextField nightsField = new JTextField(5);
        JCheckBox smokerCheckBox = new JCheckBox("Fumeur");
        JTextField roomNumberField = new JTextField(5);
        JButton addBookingButton = new JButton("Ajouter Réservation");

        formPanel.add(new JLabel("Type de chambre:"));
        formPanel.add(roomTypeCombo);

        // Ajout des composants pour la date de début
        formPanel.add(startDateLabel);
        formPanel.add(new JLabel("Jour:"));
        formPanel.add(new JLabel("Mois:"));
        formPanel.add(new JLabel("Année:"));
        formPanel.add(startDayField);
        formPanel.add(startMonthField);
        formPanel.add(startYearField);

        // Ajout des composants pour la date de fin
        formPanel.add(endDateLabel);
        formPanel.add(new JLabel("Jour:"));
        formPanel.add(new JLabel("Mois:"));
        formPanel.add(new JLabel("Année:"));
        formPanel.add(endDayField);
        formPanel.add(endMonthField);
        formPanel.add(endYearField);

        formPanel.add(new JLabel("Nombre de nuits:"));
        formPanel.add(nightsField);
        formPanel.add(new JLabel("")); // Espace pour alignement
        formPanel.add(new JLabel("")); // Espace pour alignement

        formPanel.add(new JLabel("")); // Espace
        formPanel.add(smokerCheckBox);
        formPanel.add(new JLabel("Numéro de chambre:"));
        formPanel.add(roomNumberField);
        formPanel.add(new JLabel("")); // Espace
        formPanel.add(addBookingButton);

        DefaultTableModel bookingTableModel = new DefaultTableModel(new Object[]{"Type", "Début", "Fin", "Nuits", "Fumeur", "Chambre"}, 0);
        JTable bookingTable = new JTable(bookingTableModel);
        JScrollPane tableScrollPane = new JScrollPane(bookingTable);

        addBookingButton.addActionListener(e -> {
            String roomType = (String) roomTypeCombo.getSelectedItem();
            String nuits = nightsField.getText();
            boolean isSmoking = smokerCheckBox.isSelected();
            String nombreRoomStr = roomNumberField.getText();

            try {
                int startDay = Integer.parseInt(startDayField.getText());
                int startMonth = Integer.parseInt(startMonthField.getText());
                int startYear = Integer.parseInt(startYearField.getText());
                Date startDate = new Date(startDay, startMonth, startYear);
                String startDateFormated = startDay + "/" + startMonth + "/" + startYear;

                int endDay = Integer.parseInt(endDayField.getText());
                int endMonth = Integer.parseInt(endMonthField.getText());
                int endYear = Integer.parseInt(endYearField.getText());
                Date endDate = new Date(endDay, endMonth, endYear);
                String endDateFormated = endDay + "/" + endMonth + "/" + endYear;

                int nombreNuit = Integer.parseInt(nuits);
                int nombreRoom = Integer.parseInt(nombreRoomStr);

                HotelBooking newBooking = new HotelBooking(nombreRoom, startDate, endDate, nombreNuit, roomType, isSmoking);
                hotelBookings.add(newBooking);
                bookingTableModel.addRow(new Object[]{newBooking.getRoomType(), startDateFormated, endDateFormated, newBooking.getNombreNuit(), newBooking.isSmoking() ? "Oui" : "Non", newBooking.getNombreRoom()});

                // Réinitialiser les champs
                startDayField.setText("");
                startMonthField.setText("");
                startYearField.setText("");
                endDayField.setText("");
                endMonthField.setText("");
                endYearField.setText("");
                nightsField.setText("");
                smokerCheckBox.setSelected(false);
                roomNumberField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Le nombre de nuits et le numéro de chambre doivent être des nombres entiers.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createPlaneTicketsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField referenceField = new JTextField(20);
        JTextField companyField = new JTextField(20);
        JTextField datesField = new JTextField(15);
        JButton addTicketButton = new JButton("Ajouter Billet");

        formPanel.add(new JLabel("Référence du billet:"));
        formPanel.add(referenceField);
        formPanel.add(new JLabel("")); // Espace
        formPanel.add(addTicketButton);

        DefaultTableModel ticketTableModel = new DefaultTableModel(new Object[]{"Référence"}, 0);
        JTable ticketTable = new JTable(ticketTableModel);
        JScrollPane tableScrollPane = new JScrollPane(ticketTable);

        addTicketButton.addActionListener(e -> {
            String reference = referenceField.getText();

            if (!reference.isEmpty()) {
                PlaneTicket newTicket = new PlaneTicket(reference);
                planeTickets.add(newTicket);
                ticketTableModel.addRow(new Object[]{newTicket.getReference()});
                // Réinitialiser les champs
                referenceField.setText("");
                companyField.setText("");
                datesField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel searchInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Rechercher Séjours par Nom/ID Client");
        JTextArea searchResultsArea = new JTextArea(10, 40);
        searchResultsArea.setEditable(false);
        JScrollPane resultsScrollPane = new JScrollPane(searchResultsArea);

        searchInputPanel.add(new JLabel("Nom ou ID Client:"));
        searchInputPanel.add(searchField);
        searchInputPanel.add(searchButton);

        JPanel allBookingsPanel = new JPanel(new BorderLayout());
        DefaultTableModel allBookingsTableModel = new DefaultTableModel(new Object[]{"Type", "Dates", "Nuits", "Chambre"}, 0);
        JTable allBookingsTable = new JTable(allBookingsTableModel);
        JScrollPane allBookingsScrollPane = new JScrollPane(allBookingsTable);
        allBookingsPanel.add(new JLabel("Toutes les Réservations d'Hôtel:"), BorderLayout.NORTH);
        allBookingsPanel.add(allBookingsScrollPane, BorderLayout.CENTER);

        JPanel stayDetailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel stayDetailsLabel = new JLabel("Sélectionnez un séjour pour voir les détails.");
        stayDetailsPanel.add(stayDetailsLabel);
        JButton calculatePriceButton = new JButton("Calculer le prix du séjour");
        stayDetailsPanel.add(calculatePriceButton);
        calculatePriceButton.setEnabled(false);

        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().toLowerCase();
            searchResultsArea.setText("");
            boolean foundClient = false;
            for (Client client : clients) {
                // Vérifier si le terme de recherche correspond à l'ID ou au nom du client
                if (String.valueOf(client.getId()).equals(searchTerm) || client.getNom().toLowerCase().contains(searchTerm)) {
                    foundClient = true;
                    if (!client.getClientStays().isEmpty()) {
                        searchResultsArea.append("Séjours pour le client : " + client.getNom() + " (ID: " + client.getId() + ")\n");
                        for (Stay stay : client.getClientStays()) {
                            String debutFormatted = stay.getStart().getJours() + "/" + stay.getStart().getMois() + "/" + stay.getStart().getAnnee();
                            String finFormatted = stay.getEnd().getJours() + "/" + stay.getEnd().getMois() + "/" + stay.getEnd().getAnnee();
                            searchResultsArea.append("  Début : " + debutFormatted + ", Fin : " + finFormatted + ", Transport : " + stay.getTransport() + ", Chambre Hôtel : " + stay.getReservedStayHotel() + "\n");
                        }
                    } else {
                        searchResultsArea.append("Aucun séjour associé au client : " + client.getNom() + " (ID: " + client.getId() + ")\n");
                    }
                }
            }
            if (!foundClient) {
                searchResultsArea.append("Aucun client trouvé avec le nom ou l'ID : " + searchTerm + "\n");
            }
        });

        // Peupler le tableau de toutes les réservations (nécessite la logique pour accéder à hotelBookings)
        for (HotelBooking booking : hotelBookings) {
            allBookingsTableModel.addRow(new Object[]{booking.getRoomType(), booking.getStart(), booking.getEnd(), booking.getNombreNuit(), booking.getNombreRoom(), booking.isSmoking() ? "Oui" : "Non"});
        }

        panel.add(stayDetailsPanel, BorderLayout.SOUTH);
        panel.add(searchInputPanel, BorderLayout.NORTH);
        panel.add(resultsScrollPane, BorderLayout.CENTER);
        panel.add(allBookingsPanel, BorderLayout.EAST);

        return panel;
    }
}