public class HotelBooking {
    private int nombreRoom;
    private Date Start;
    private Date End;
    private int nombreNuit;
    private String roomType;
    private boolean Smoking;
    private int price;

    public HotelBooking() {
        this.nombreRoom = 0;
        this.Start = new Date(0, 0, 0);
        this.End = new Date(0, 0, 0);
        this.nombreNuit = 0;
        this.roomType = "";
        this.Smoking = false;
    }

    public HotelBooking(int _nombreRoom, Date Start, Date End, int nombreNuit, String _roomType, boolean smoking, int price) {
        this.nombreRoom = _nombreRoom;
        this.Start = Start;
        this.End = End;
        this.nombreNuit = nombreNuit;
        this.roomType = _roomType;
        this.Smoking = smoking;
        this.price = price;
    }

    public int getNombreRoom() {
        return nombreRoom;
    }

    public void setNombreRoom(int nombreRoom) {
        this.nombreRoom = nombreRoom;
    }

    public Date getStart() {
        return Start;
    }

    public void setStart(Date start) {
        Start = start;
    }

    public Date getEnd() {
        return End;
    }

    public void setEnd(Date end) {
        End = end;
    }

    public int getNombreNuit() {
        return nombreNuit;
    }

    public void setNombreNuit(int nombreNuit) {
        this.nombreNuit = nombreNuit;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean isSmoking() {
        return Smoking;
    }

    public void setSmoking(boolean smoking) {
        Smoking = smoking;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Booking " + nombreRoom + " from " + Start + " to " + End + ", Room Type: " + roomType + ", Smoking: " + Smoking;
    }
}
