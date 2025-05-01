package model;

public class Address {
    private String country;
    private String city;
    private String street;
    private String postalCode;
    private int id;

    public Address(String country, String city, String street, String postalCode, int id) {
       this.country = country;
       this.city = city;
       this.street = street;
       this.postalCode = postalCode;
       this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
