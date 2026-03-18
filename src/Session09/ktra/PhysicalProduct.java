package Session09.ktra;

public class PhysicalProduct extends Product {
    private double weight;
    public PhysicalProduct(String id, double price, String name, double weight) {
        super(id, price, name);
        this.weight = weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    void displayInfo(){
        System.out.println("==Thông tin sản phẩm==");
        System.out.println("ID: "+this.getId());
        System.out.println("Name: "+this.getName());
        System.out.println("Price: "+this.getPrice());
    }
}
