package Session7.Baitap01;

public class Customer {
    private String customerName;
    private String  email;
//    private String adress;

    public Customer(String customerName, String email) {
        this.customerName = customerName;
        this.email = email;
//        this.adress = adress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getAdress() {
//        return adress;
//    }

//    public void setAdress(String adress) {
//        this.adress = adress;
//    }
}
