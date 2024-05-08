package org.example.model;


public class Person {
    private String personId;
    private String firstname;
    private String lastname;
    private String mobile;
    private String email;
    private String pesel;

    public Person() {
    }

    public Person(String personId, String firstname, String lastname, String mobile, String email, String pesel) {
        this.personId = personId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobile = mobile;
        this.email = email;
        this.pesel = pesel;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public static class Builder{
        private String personId;
        private String firstname;
        private String lastname;
        private String mobile;
        private String email;
        private String pesel;

        public Builder(){}


        public Builder personId(String personId) {
            this.personId = personId;
            return this;
        }

        public Builder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }
        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }
        public Builder mobile(String mobile) {
            this.mobile = mobile;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder pesel(String pesel) {
            this.pesel = pesel;
            return this;
        }

        public Person build() {
            return new Person(personId, firstname, lastname, mobile, email, pesel);
        }
    }
}