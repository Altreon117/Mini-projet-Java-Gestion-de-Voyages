

public class Stay {
    private Date Start;
    private Date End;
    protected PlaneTicket transport;
    protected HotelBooking reservedStayHotel;
    protected String transportRef;
    protected String chambreHotel;

    public Stay(Date start, Date end, PlaneTicket transport, HotelBooking hotel, String transportRef, String chambreHotel) {
        this.Start = start;
        this.End = end;
        this.transport = transport;
        this.reservedStayHotel = hotel;
        this.transportRef = transportRef;
        this.chambreHotel = chambreHotel;
    }
    

    public void setStay(int i) {
    }

    public Date getStart() {
        return Start;   
    }

    public void setStart(Date start) {
        this.Start = start;
    }

    public Date getEnd() {
        return End;   
    }
    public void setEnd(Date end) {
        this.End = end;
    }

    public String getTransport() {
        return transportRef;
    }

    public String getReservedStayHotel() {
        return chambreHotel;
    }

    public int calculatePrice() {
        return transport.getPrice()+ reservedStayHotel.getPrice();
    }
    public int calculatePrice(int i) {
        return transport.getPrice()+ reservedStayHotel.getPrice();
    }

    public String toString() {
        return "Stay{" +
                "Start=" + Start +
                ", End=" + End +
                ", transport=" + transport +
                ", reservedStayHotel=" + reservedStayHotel +
                ", price=" + calculatePrice() +
                '}';
    }
}
