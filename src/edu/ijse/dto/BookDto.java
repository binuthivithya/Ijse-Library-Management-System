package edu.ijse.dto;

import javafx.scene.control.Button;

public class BookDto {
    private String bookID;
    private String title;
    private String author;
    private String isbn;
    private int qoh;
    private double unitPrice;
    private String categoryID;
    private Button button;

    public BookDto() {
    }

    public BookDto(String bookID, String title, String author, String isbn, int qoh, double unitPrice, String categoryID
            , Button button) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.qoh = qoh;
        this.unitPrice = unitPrice;
        this.categoryID = categoryID;
        this.button = button;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQoh() {
        return qoh;
    }

    public void setQoh(int qoh) {
        this.qoh = qoh;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "bookID='" + bookID + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", qoh=" + qoh +
                ", unitPrice=" + unitPrice +
                ", categoryID='" + categoryID + '\'' +
                ", button=" + button +
                '}';
    }
}
