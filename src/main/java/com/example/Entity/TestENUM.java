package com.example.Entity;

/**
 * Created by User on 24/04/2017.
 */
public class TestENUM {
    public enum admins{
        USER,ADMIN,SUPER_ADMIN;
    }
    admins superA;
    admins user;
    admins admin;

    TestENUM(){}

    public TestENUM(admins superA, admins user, admins admin) {
        this.superA = superA;
        this.user = user;
        this.admin = admin;
    }

    public admins getSuperA() {
        return superA;
    }

    public void setSuperA(admins superA) {
        this.superA = superA;
    }

    public admins getUser() {
        return user;
    }

    public void setUser(admins user) {
        this.user = user;
    }

    public admins getAdmin() {
        return admin;
    }

    public void setAdmin(admins admin) {
        this.admin = admin;
    }
}
