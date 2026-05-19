package trs.entity;

public class Owner {

    private String name;
    private String surname;
    private String phone;
    private String email;

    public Owner(String name, String surname, String phone, String email) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getFullName(){
        return this.name + this.surname;
    }
}
