package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void initPageObjects() {
        loginPage = new LoginPage(driver);
        loginPage.handleReloadIfPresent();
        loginPage.waitForPageLoad();
    }

    @Test(description = "Attempt login with blank fields and verify UI behavior")
    public void testLoginButtonDisabledWhenFieldsAreEmpty() {
        // This app's login button is always enabled, so you might want to check error instead!
        loginPage.clickLogin();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(!error.isEmpty() || !loginPage.isLoginButtonEnabled(), 
            "Login button should be disabled or error shown when fields are empty.");
    }

    @Test(description = "Enter random credentials and capture error message")
    public void testInvalidLoginShowErrorMsg() {
        loginPage.enterUserId("randomUser");
        loginPage.enterPassword("randomPass");
        loginPage.clickLogin();
        String error = loginPage.getErrorMessage();
        System.out.println("Captured error message: " + error);
        Assert.assertTrue(!error.isEmpty(), "Error message should appear for invalid login.");
    }

    @Test(description = "Validate password masking/unmasking toggle")
    public void testPasswordMaskedbutton() {
        loginPage.enterPassword("mySecret");
        Assert.assertEquals(loginPage.getPasswordFieldType(), "password", "Password should be masked.");
        loginPage.togglePasswordVisibility();
        Assert.assertEquals(loginPage.getPasswordFieldType(), "text", "Password should be visible after toggle.");
    }

    @Test(description = "Validate presence of all login page elements")
    public void testValidatePresenceOfPageElements() {
        Assert.assertTrue(loginPage.areElementsPresent(), "All login page elements should be present.");
    }
}
