package tests;

import model.Account;
import model.User;

import java.util.Date;

public class AccountTest {
    public static void testAccountConstructor(){
        // 1 - Setup
        String testAccountNumber = "1234567890";
        String testUserNameOfAccountHolder = "mike";
        String testAccountType = "Savings";
        Date testDateOfOpening = new Date();

        // 2 - Execution
        Account testAccount = new Account(testAccountNumber, testUserNameOfAccountHolder, testAccountType, testDateOfOpening);

        // 3 - Assertion
        System.out.println("Starting the assertions of the test method: testUserConstructor");

        assert testAccount.getAccountNumber() == testAccountNumber : "TC1-getAccountNumber: Failed";
        assert testAccount.getUserNameOfAccountHolder() == testUserNameOfAccountHolder : "TC2-getUserNameOfAccountHolder: Failed";
        assert testAccount.getAccountType() == testAccountType : "TC3-getAccountType: Failed";
        assert testAccount.getDateOfOpening() == testDateOfOpening : "TC4-getDateOfOpening: Failed";
    }

    public static void testAccountSetters(){
        // 1 - Setup
        String testAccountNumber = "1234567890";
        String testUserNameOfAccountHolder = "mike";
        String testAccountType = "Savings";
        Date testDateOfOpening = new Date();

        // 2 - Execution
        Account testAccount = new Account(testAccountNumber, testUserNameOfAccountHolder, testAccountType, testDateOfOpening);

        // 3 - Assertion
        assert testAccount.getAccountNumber() == testAccountNumber : "TC1-getAccountNumber: Failed";
        assert testAccount.getUserNameOfAccountHolder() == testUserNameOfAccountHolder : "TC2-getUserNameOfAccountHolder: Failed";
        assert testAccount.getAccountType() == testAccountType : "TC3-getAccountType: Failed";
        assert testAccount.getDateOfOpening() == testDateOfOpening : "TC4-getDateOfOpening: Failed";
    }

    public static void main(String[] args) {
        testAccountConstructor();
        testAccountSetters();
    }
}
