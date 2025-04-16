import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable {
    private String nom;
    private int id;
    private String adresse;
    private List<PlaneTicket> travelDocuments;
    private List<HotelBooking> reservedClientHotels;
    private List<Stay> clientStays;
    private List<Stay> guestStays;

    public Client() {
        this.travelDocuments = new ArrayList<>();
        this.reservedClientHotels = new ArrayList<>();
        this.clientStays = new ArrayList<>();
        this.guestStays = new ArrayList<>();
    }

    public Client(String nom, int id, String adresse) {
        this.nom = nom;
        this.id = id;
        this.adresse = adresse;
        this.travelDocuments = new ArrayList<>();
        this.reservedClientHotels = new ArrayList<>();
        this.clientStays = new ArrayList<>();
        this.guestStays = new ArrayList<>();
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    // Travel Documents
    public List<PlaneTicket> getTravelDocuments() {
        return travelDocuments;
    }

    public void addTravelDocument(PlaneTicket ticket) {
        this.travelDocuments.add(ticket);
    }

    // Hotels réservés
    public List<HotelBooking> getReservedClientHotels() {
        return reservedClientHotels;
    }

    public void addReservedClientHotel(HotelBooking hotel) {
        this.reservedClientHotels.add(hotel);
    }

    // Séjours comme client
    public List<Stay> getClientStays() {
        return clientStays;
    }

    public void addStayClient(Stay stay) {
        this.clientStays.add(stay);
    }

    // Séjours comme invité
    public List<Stay> getGuestStays() {
        return guestStays;
    }

    public void addStayGuest(Stay stay) {
        this.guestStays.add(stay);
    }

    @Override
    public String toString() {
        return "Client #" + id + " - " + nom + ", " + adresse;
    }
}
