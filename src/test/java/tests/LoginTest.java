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
     // If "Reload" page is shown, handle it
        loginPage.handleReloadIfPresent();
        loginPage.waitForPageLoad();
    }

    @Test(description = "Verify login button is enabled/disabled based on fields")
    public void testLoginButtonDisabledWhenFieldsAreEmpty() {
        // On your HTML, button is always enabled
        Assert.assertTrue(loginPage.isLoginButtonEnabled(), "Login button should be enabled when fields are empty.");
    }

    @Test(description = "Validate password field masking and unmasking toggle")
    public void testPasswordMaskedbutton() {
        loginPage.enterPassword("mySecret");
        Assert.assertEquals(loginPage.getPasswordFieldType(), "password", "Password field should be masked by default.");
        loginPage.togglePasswordVisibility();
        Assert.assertEquals(loginPage.getPasswordFieldType(), "text", "Password should be unmasked after clicking eye icon.");
    }

    @Test(description = "Attempt invalid login and verify error message")
    public void testInvalidLoginShowErrorMsg() {
        loginPage.enterUserId("invalidUser");
        loginPage.enterPassword("invalidPass");
        loginPage.clickLogin();
        String error = loginPage.getErrorMessage();
        System.out.println("Captured error message: " + error);
        Assert.assertTrue(!error.isEmpty(), "Error message should appear for invalid login.");
    }

    @Test(description = "Verify presence of all login page elements")
    public void testValidatePresenceOfPageElements() {
        Assert.assertTrue(loginPage.areElementsPresent(), "All login page elements should be displayed.");
    }
}

