package Session09.ktra;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {
    private static ProductDatabase instance;
    private List<Product> products;
    private ProductDatabase(){
        products = new ArrayList<>();
    }
    public static ProductDatabase getInstance(){
        if(instance == null){
            instance = new ProductDatabase();
        }
        return instance;
    }
    void addProduct(Product product){
        products.add(product);
        System.out.println("Thêm sản phẩm thành công");
    }
    boolean removeProduct(String id){
       return products.removeIf(p->p.getId().equals(id));
    }
    boolean updateProduct(String id){
        Product p = getProductById(id);
        if(p != null){
            products.remove(p);
            return true;
        }
        return false;
    }
    Product getProductById(String id){
        for(Product p : products){
            if(p.getId().equalsIgnoreCase(id)){
                return p;
            }
        }
        return null;

    }
    List<Product> getAllProducts(){
        return products;
    }
}
