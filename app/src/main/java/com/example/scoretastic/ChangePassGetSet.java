package com.example.scoretastic;

public class ChangePassGetSet {

    char oldPassword = 0;
    char newPassword = 0;
    char confirmPassword = 0;


   /*
    public char getOldPassword() {
        return oldPassword;
    }
    autoo generated getter
    */

    public char getoldPassword() {

        return oldPassword;
    }

    public char getnewPassword() {

        return newPassword;
    }


    public char getconfirmPassword() {

        return confirmPassword;
    }


    public void setOldPassword(char oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setConfirmPassword(char confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setNewPassword(char newPassword) {
        this.newPassword = newPassword;
    }
}