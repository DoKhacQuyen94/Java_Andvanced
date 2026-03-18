package Session09.ktra;

import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static final ProductDatabase db = ProductDatabase.getInstance();
    public static void main(String[] args) {
        int choice;
        do {
            displayMenu();
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Chọn loại (1-Physical, 2- Digital)");
                    int type = sc.nextInt();
                    System.out.println("Nhập ID: ");
                    String id = sc.next();
                    System.out.println("Nhập Tên sản phẩm: ");
                    String name = sc.next();
                    System.out.println("Nhập giá sản phẩm: ");
                    double price = sc.nextDouble();
                    double spec;
                    if(type == 1){
                        System.out.println("Nhập số lượng kg: ");
                        spec = sc.nextDouble();
                    }else {
                        System.out.println("Nhập dung lượng (MB): ");
                        spec = sc.nextDouble();
                    }
                    Product p = ProductFactory.createProduct(type,id,name,price,spec);
                    if(p != null){
                        db.addProduct(p);
                    }
                    break;
                case 2:
                    for (Product pr : db.getAllProducts()){
                        pr.displayInfo();
                    }
                    break;
                case 3:
                    System.out.println("Nhập id cần cập nhật: ");
                    String idUpdate =  sc.next();
                    Product prUpdate = db.getProductById(idUpdate);
                    if(prUpdate != null){
                        System.out.println("Tên mới: ");
                        prUpdate.setName(sc.next());
                        System.out.println("Giá mới: ");
                        prUpdate.setPrice(sc.nextDouble());
                        if(prUpdate instanceof PhysicalProduct){
                            System.out.println("Nhập lợng mới: ");
                            ((PhysicalProduct) prUpdate).setWeight(sc.nextDouble());
                        }else {
                            System.out.println("Nhập lượng mới: ");
                            ((DigitalProduct) prUpdate).setSize(sc.nextDouble());
                        }
                    }else {
                        System.out.println("Không tìm thấy sản phẩm");
                    }
                    break;
                case 4:
                    System.out.println("Nhập id cần xóa: ");
                    String deleId = sc.next();
                    db.removeProduct(deleId);
                    break;
                case 5:
                    System.out.println("Bạn đã thoát chương trình");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
                    break;
            }
        }while (choice!=5);
    }
    static void displayMenu(){
        System.out.println("---------------------- QUẢN LÝ SẢN PHẨM ----------------------");
        System.out.println("1. Thêm mới sản phẩm");
        System.out.println("2. Xem danh sách sản phẩm");
        System.out.println("3. Cập nhật thông tin sản phẩm");
        System.out.println("4. Xoá sản phẩm");
        System.out.println("5. Thoát");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Lựa chọn của bạn: ");
    }


}
