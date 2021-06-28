package by.tms.homework_TMS_21_.entity;

public class User {
    private int id;
    private String name;
    private String userName;
    private String password;
    private Addresses address;
    private Telephones telephoneNumber;

    public User(int id, String name, String userName, String password, Addresses address, Telephones telephones) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.address = address;
        this.telephoneNumber = telephones;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                ", telephoneNumber=" + telephoneNumber +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Addresses getAddress() {
        return address;
    }

    public void setAddress(Addresses address) {
        this.address = address;
    }

    public Telephones getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(Telephones telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
}


