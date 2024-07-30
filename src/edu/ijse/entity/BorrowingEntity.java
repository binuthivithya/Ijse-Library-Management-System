package edu.ijse.entity;

import java.util.Date;

public class BorrowingEntity {
    private String borrowId;
    private Date borrowDate;
    private Date dueDate;
    private String memberId;
    private String bookId;
    private boolean isReturned;

    public BorrowingEntity() {
    }

    public BorrowingEntity(String borrowId, Date borrowDate, Date dueDate, String memberId, String bookId, Boolean isReturned) {
        this.borrowId = borrowId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.memberId = memberId;
        this.bookId = bookId;
        this.isReturned = isReturned;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    @Override
    public String toString() {
        return "BorrowingEntity{" +
                "borrowId='" + borrowId + '\'' +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", memberId='" + memberId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", isReturned=" + isReturned +
                '}';
    }
}
