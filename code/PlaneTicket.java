public class PlaneTicket {
    private String Reference;
    private int price;

    public PlaneTicket(String Reference, int price) {
        this.Reference = Reference;
        this.price = price;
    }

    public String getReference() {
        return Reference;
    }
    public void setReference(String reference) {
        Reference = reference;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
