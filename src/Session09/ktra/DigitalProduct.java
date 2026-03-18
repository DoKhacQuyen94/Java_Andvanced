package Session09.ktra;

public class DigitalProduct extends Product {
    private double size;
    public DigitalProduct(String id, double price, String name, double size) {
        super(id, price, name);
        this.size = size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    void displayInfo(){
        System.out.println("==Thông tin sản phẩm==");
        System.out.println("ID: "+this.getId());
        System.out.println("Name: "+this.getName());
        System.out.println("Price: "+this.getPrice());
    }
}

