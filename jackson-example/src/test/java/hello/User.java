package hello;

class User {
    public String firstName;
    private String lastName;
    public Address address;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
