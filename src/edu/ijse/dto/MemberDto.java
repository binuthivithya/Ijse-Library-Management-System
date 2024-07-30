package edu.ijse.dto;

import javafx.scene.control.Button;

import java.util.Date;

public class MemberDto {
    private String memberID;
    private String name;
    private String address;
    private String contact;
    private String email;
    private Date membershipDate;
    private Button button;

    public MemberDto() {
    }

    public MemberDto(String memberID, String name, String address, String contact, String email, Date membershipDate, Button button) {
        this.memberID = memberID;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.membershipDate = membershipDate;
        this.button = button;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(Date membershipDate) {
        this.membershipDate = membershipDate;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "memberID='" + memberID + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", membershipDate=" + membershipDate +
                ", button=" + button +
                '}';
    }
}
