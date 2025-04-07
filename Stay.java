import java.util.Arrays;

public class Stay {
    private Date Start;
    private Date End;
    protected final PlaneTicket[] transport = new PlaneTicket[0];
    protected final HotelBooking[] reservedStayHotel = new HotelBooking[0];

    public Stay(Date start, Date end) {
        this.Start = start;
        this.End = end;
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

    public PlaneTicket[] getTransport() {
        return transport;
    }public void addTransport(PlaneTicket pt) {
        transport[transport.length] = pt;
    }

    public HotelBooking[] getReservedStayHotel() {
        return reservedStayHotel;
    }
    public void addReservedStayHotel(HotelBooking hb) {
        reservedStayHotel[reservedStayHotel.length] = hb;
    }

    public void calculatePrice() {}
    public void calculatePrice(int i) {}

    public String toString() {
        return "Stay{" +
                "Start=" + Start +
                ", End=" + End +
                ", transport=" + Arrays.toString(transport) +
                ", reservedStayHotel=" + Arrays.toString(reservedStayHotel) +
                '}';
    }
}
