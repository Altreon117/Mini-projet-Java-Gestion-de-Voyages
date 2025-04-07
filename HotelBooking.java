public class HotelBooking {
    private int nombreRoom;
    private Date Start;
    private Date End;
    private int nombreNuit;
    private String roomType;
    private boolean Smoking;

    public HotelBooking() {
        this.nombreRoom = 0;
        this.Start = new Date();
        this.End = new Date();
        this.nombreNuit = 0;
        this.roomType = "";
        this.Smoking = false;
    }

    public HotelBooking(int _nombreRoom, Date Start, Date End, String _roomType, boolean smoking) {
        this.nombreRoom = _nombreRoom;
        this.Start = Start;
        this.End = End;
        this.nombreNuit = 0;
        this.roomType = _roomType;
        this.Smoking = smoking;
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

    public String toString() {
        return "Booking " + nombreRoom + " from " + Start + " to " + End + ", Room Type: " + roomType + ", Smoking: " + Smoking;
    }
}
