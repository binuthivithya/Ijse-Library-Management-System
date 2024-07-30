package edu.ijse.dto;

import java.util.Date;

public class ReturnDto {
    private String returnId;
    private Date returnDate;
    private Double fine;
    private String borrowId;
    private String memberId;
    private String memberName;
    private String bookId;
    private String bookTitle;
    private Date dueDate;
    private boolean isPaid;

    public ReturnDto() {
    }

    public ReturnDto(String returnId, Date returnDate, Double fine, String borrowId, String memberId,
                     String memberName, String bookId, String bookTitle, Date dueDate, Boolean isPaid) {
        this.returnId = returnId;
        this.returnDate = returnDate;
        this.fine = fine;
        this.borrowId = borrowId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.dueDate = dueDate;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return "ReturnDto{" +
                "returnId='" + returnId + '\'' +
                ", returnDate=" + returnDate +
                ", fine=" + fine +
                ", borrowId='" + borrowId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", memberName='" + memberName + '\'' +
                ", bookId='" + bookId + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", dueDate=" + dueDate +
                ", isPaid=" + isPaid +
                '}';
    }
}
