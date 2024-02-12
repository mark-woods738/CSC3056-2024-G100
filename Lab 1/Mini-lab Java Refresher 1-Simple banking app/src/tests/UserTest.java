package tests;

import model.User;
import utils.TestUtils;

public class UserTest {

    public static void testUserConstructor(boolean usingIfStatements){
        // 1 - Setup
        String testUsername = "mike";
        String testPassword = "my_password";
        String testFirstName = "Mike";
        String testLastName = "Smith";
        String testMobileNumber = "07771234567";

        // 2 - Execution
        User testUser = new User(testUsername, testPassword, testFirstName, testLastName, testMobileNumber);

        // 3 - Assertion
        System.out.println("Starting the assertions of the test method: testUserConstructor");

        // using if statements
        if(usingIfStatements) {
            if (testUser.getUsername() == testUsername)
                System.out.println(TestUtils.TEXT_COLOR_GREEN + "TC1-getUsername:\t Passed");
            else System.out.println(TestUtils.TEXT_COLOR_RED + "TC1-getUsername:\t Failed");

            if (testUser.getPassword() == testPassword)
                System.out.println(TestUtils.TEXT_COLOR_GREEN + "TC2-getPassword:\t Passed");
            else System.out.println(TestUtils.TEXT_COLOR_RED + "TC2-getPassword:\t Failed");

            if (testUser.getFirstName() == testFirstName)
                System.out.println(TestUtils.TEXT_COLOR_GREEN + "TC3-getFirstName:\t Passed");
            else System.out.println(TestUtils.TEXT_COLOR_RED + "TC3-getFirstName:\t Failed");

            if (testUser.getLastName() == testLastName)
                System.out.println(TestUtils.TEXT_COLOR_GREEN + "TC4-getLastName:\t Passed");
            else System.out.println(TestUtils.TEXT_COLOR_RED + "TC4-getLastName:\t Failed");

            if (testUser.getMobileNumber() == testMobileNumber)
                System.out.println(TestUtils.TEXT_COLOR_GREEN + "TC5-getMobileNumber: Passed");
            else System.out.println(TestUtils.TEXT_COLOR_RED + "TC5-getMobileNumber:\t Failed");
        }

        // using assert statements
        assert testUser.getUsername() == testUsername : "TC1-getUsername: Failed";
        assert testUser.getPassword() == testPassword : "TC2-getPassword: Failed";
        assert testUser.getFirstName() == testFirstName : "TC3-getFirstName: Failed";
        assert testUser.getLastName() == testLastName : "TC4-getLastName: Failed";
        assert testUser.getMobileNumber() == testMobileNumber : "TC5-getMobileNumber: Failed";
    }

    public static void main(String[] args) {
        testUserConstructor(false);
    }
}
