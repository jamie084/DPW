package project.dto.user;

import project.dto.Id;

/**
 * Created by janos on 31/12/2017.
 */

public class UserInfo {
    private Id _id;
    private String firstName;
    private String secondName;
    private String company;
    private String phoneNumber;
    private String houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;

    public Id get_id() {
        return _id;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getFullName() {
        return this.getFirstName() + " " + this.getSecondName();
    }

    @Override
    public String toString(){
        String result = "";
        result += "ID: " + this.get_id();
        result += "\nFirstname: " + this.getFirstName();
        result += "\nSecondName: " + this.getSecondName();
        result += "\nComapny: " + this.getCompany();
        result += "\nPhone: " + this.getPhoneNumber();
        //result += "\nHouse: " + this.getHouseNumber();
        return result;
    }
}
