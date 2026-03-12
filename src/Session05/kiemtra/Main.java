package Session05.kiemtra;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    static List<Product> products = new ArrayList<>();
    public static void main(String[] args) throws InvalidProductException {
        int choice;
        do {
            displayMenu();
            choice = sc.nextInt();
            try {
                switch (choice) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        displayAllProducts();
                        break;
                    case 3:
                        System.out.print("Nhập Id cần update số lượng: ");
                        int idUpdate =  sc.nextInt();
                        System.out.print("Số lượng cần thêm: ");
                        int quantityUpdate = sc.nextInt();
                        updateQuantity(idUpdate, quantityUpdate);
                        break;
                    case 4:
//                    System.out.print("Nhập id cần xóa: ");
//                    int idDelete = sc.nextInt();
                        deleteProduct();
                        break;
                    case 5:
                        System.out.println("Bạn đã thoát chương trình!");
                        break;
                    default:
                        System.out.println("Lựa chọn sai vui lòng chọn lại!");
                        break;
                }
            }catch (InvalidProductException e) {
                System.out.println(e.getMessage());
            }


        }while (choice !=5);
    }
    static void displayMenu(){
        System.out.println("========= PRODUCT MANAGEMENT SYSTEM =========");
        System.out.println("1. Thêm sản phẩm mới");
        System.out.println("2. Hiển thị danh sách sản phẩm");
        System.out.println("3. Cập nhật số lượng theo ID");
        System.out.println("4. Xóa sản phẩm đã hết hàng");
        System.out.println("5. Thoát chương trình");
        System.out.println("=============================================");
        System.out.print("Lựa chọn của bạn:");
    }
    static void addProduct() throws InvalidProductException {
        int n;
        System.out.print("Nhập số lượng sản phẩm muốn thêm: ");
        n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.print("Nhập Id sản phầm muốn thêm: ");
            int id = sc.nextInt();
            boolean check = products.stream().anyMatch(p -> p.getId() == id);
            if (check) {
                throw new InvalidProductException("Id đã tồn tại");
            }
            System.out.print("Nhập tên sản phẩm: ");
            String name = sc.next();
            sc.nextLine();
            System.out.print("Nhập giá sản phẩm: ");
            double price = sc.nextDouble();
            System.out.print("Nhập số lượng sản phẩm: ");
            int quantity = sc.nextInt();
            System.out.print("Nhập tên danh mục: ");
            sc.nextLine();
            String category = sc.nextLine();
            Product newProduct = new Product(id,name,price,quantity,category);
            products.add(newProduct);
        }
    }
    static void displayAllProducts() {
        System.out.println("Danh sách sản phẩm");
        System.out.println("ID\tName\tPrice\tQuantity\tCategory");
        products.forEach(products->System.out.println(products.getId() + "\t" + products.getName() + "\t" + products.getPrice() +  "\t" + products.getQuantity() + "\t" + products.getCategory()));
    }
    static void deleteProduct() throws InvalidProductException {
        if (products.isEmpty()) {
            System.out.println("Danh sách rỗng");
            return;
        }
        products.forEach(product->{
            if (product.getQuantity() == 0) {
                products.remove(product);
            }
        });

//        boolean check = products.stream().anyMatch(p -> p.getId() == id);
//        if (check) {
//            products.remove(id);
//            System.out.println("Đã xóa sản phẩm có ID: "+id);
//        }else {
//            throw new InvalidProductException("ID không tồn tại");
//        }
    }
    static void updateQuantity(int id, int quantity) throws InvalidProductException {
        if (products.isEmpty()) {
            System.out.println("Danh sách trống");
            return;
        }
        boolean check = products.stream().anyMatch(p -> p.getId() == id);
        if (check) {
            int Quantity = products.get(id).getQuantity();
            products.get(id).setQuantity(quantity+Quantity);
            System.out.println("Đã cập nhật số lượng sản phẩm");
        }else{
            throw new InvalidProductException("ID sản phẩm không tồn tại");
        }
    }
}
