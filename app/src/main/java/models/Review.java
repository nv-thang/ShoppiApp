package models;

public class Review {
    private String status;
    private String nameReviewer;
    private int smile;
    private String imgReviewer;

    public Review() {
    }

    public Review(String status, String nameReviewer, int smile,String imgReviewer) {
        this.status = status;
        this.nameReviewer = nameReviewer;
        this.smile = smile;
        this.imgReviewer = imgReviewer;
    }

    public String getImgReviewer() {
        return imgReviewer;
    }

    public void setImgReviewer(String imgReviewer) {
        this.imgReviewer = imgReviewer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNameReviewer() {
        return nameReviewer;
    }

    public void setNameReviewer(String nameReviewer) {
        this.nameReviewer = nameReviewer;
    }

    public int getSmile() {
        return smile;
    }

    public void setSmile(int smile) {
        this.smile = smile;
    }
}
