package edu.ijse.entity;

import java.util.Date;

public class ReturnEntity {
    private String returnId;
    private Date returnDate;
    private Double fine;
    private String borrowId;
    private String memberId;
    private String bookId;
    private boolean isPaid;

    public ReturnEntity() {
    }

    public ReturnEntity(
            String returnId, Date returnDate, Double fine, String borrowId, String memberId, String bookId, Boolean isPaid
    ){
        this.returnId = returnId;
        this.returnDate = returnDate;
        this.fine = fine;
        this.borrowId = borrowId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.isPaid = isPaid;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Double getFine() {
        return fine;
    }

    public void setFine(Double fine) {
        this.fine = fine;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return "ReturnEntity{" +
                "returnId='" + returnId + '\'' +
                ", returnDate=" + returnDate +
                ", fine=" + fine +
                ", borrowId='" + borrowId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", isPaid=" + isPaid +
                '}';
    }
}
