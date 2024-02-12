package model;

import java.util.Date;

public class Account {
    String accountNumber;
    String userNameOfAccountHolder;
    String accountType;
    Date dateOfOpening;

    public Account(String accountNumber, String userNameOfAccountHolder, String accountType, Date dateOfOpening) {
        this.accountNumber = accountNumber;
        this.userNameOfAccountHolder = userNameOfAccountHolder;
        this.accountType = accountType;
        this.dateOfOpening = dateOfOpening;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setUserNameOfAccountHolder(String userNameOfAccountHolder) {
        this.userNameOfAccountHolder = userNameOfAccountHolder;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setDateOfOpening(Date dateOfOpening) {
        this.dateOfOpening = dateOfOpening;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getUserNameOfAccountHolder() {
        return userNameOfAccountHolder;
    }

    public String getAccountType() {
        return accountType;
    }

    public Date getDateOfOpening() {
        return dateOfOpening;
    }

    public String toString(){
        return this.accountNumber +", " +this.userNameOfAccountHolder +", " +this.accountType +", " +this.dateOfOpening;
    }
}
