package com.pyassasins.alarmic;

/**
 * Created by lakshmanaram on 2/10/15.
 */
public class Cont {
    String name;
    String phoneno;
    int authenticated;

    public Cont(){

    }
    public Cont(String name,String phoneno, int authenticated ){
        this.setName(name);
        this.setPhoneno(phoneno);
        this.setAuthenticated(authenticated);
    }

    public int getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(int authenticated) {
        this.authenticated = authenticated;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
