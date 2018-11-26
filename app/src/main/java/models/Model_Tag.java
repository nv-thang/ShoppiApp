package models;

public class Model_Tag {
    String text;

    public Model_Tag(){}

    public Model_Tag(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}