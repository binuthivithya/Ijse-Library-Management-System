package edu.ijse.entity;

import java.util.Date;

public class MemberEntity {
    private String memberID;
    private String name;
    private String address;
    private String contact;
    private String email;
    private Date membershipDate;

    public MemberEntity() {
    }

    public MemberEntity(String memberID, String name, String address, String contact, String email, Date membershipDate) {
        this.memberID = memberID;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.membershipDate = membershipDate;
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

    @Override
    public String toString() {
        return "MemberEntity{" +
                "memberID='" + memberID + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", membershipDate=" + membershipDate +
                '}';
    }
}
