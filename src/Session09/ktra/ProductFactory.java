package Session09.ktra;

public class ProductFactory {
    public static Product createProduct(int type, String id, String name, double price, double spec) {
        if(type==1){
            return new PhysicalProduct(id,price,name,spec);
        }else if(type==2){
            return new DigitalProduct(id,price,name,spec);
        }else {
            return null;
        }
    }

}
