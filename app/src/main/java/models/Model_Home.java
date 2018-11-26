package models;

public class Model_Home {

    private String image;
    private String ten;
    private String gia;
    private String giakm;
    private String phantram;
    private String thongtin;
    private String id;
    private String theloai;
    private String tag;

    public Model_Home() {
    }

    public Model_Home(String image, String ten, String gia, String giakm, String phantram, String thongtin, String theloai, String tag) {
        this.image = image;
        this.ten = ten;
        this.gia = gia;
        this.giakm = giakm;
        this.phantram = phantram;
        this.thongtin = thongtin;
        this.theloai = theloai;
        this.tag = tag;
    }
    public Model_Home(String image, String ten, String gia, String id) {
        this.image = image;
        this.ten = ten;
        this.gia = gia;
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTheloai() {
        return theloai;
    }

    public void setTheloai(String theloai) {
        this.theloai = theloai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThongtin() {
        return thongtin;
    }

    public void setThongtin(String thongtin) {
        this.thongtin = thongtin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getGiakm() {
        return giakm;
    }

    public void setGiakm(String giakm) {
        this.giakm = giakm;
    }

    public String getPhantram() {
        return phantram;
    }

    public void setPhantram(String phantram) {
        this.phantram = phantram;
    }
}
