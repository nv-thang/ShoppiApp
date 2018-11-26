package models;

public class Order{
    String hoTen,sdt,diachi,tongtien, sanpham,uID;
    String date;


    public Order() {
    }

    public Order(String hoTen, String sdt, String diachi, String tongtien, String sanpham, String uID,String date) {
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.diachi = diachi;
        this.tongtien = tongtien;
        this.sanpham = sanpham;
        this.uID = uID;
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public String getSanpham() {
        return sanpham;
    }

    public void setSanpham(String sanpham) {
        this.sanpham = sanpham;
    }
}
