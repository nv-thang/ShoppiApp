package models;

public class Model_Cart {
   String ten;
   String gia;
   String image;
   String id;

    public Model_Cart() {
    }

    public Model_Cart(String ten, String gia, String image, String id) {
        this.ten = ten;
        this.gia = gia;
        this.image = image;
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
