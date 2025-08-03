package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "formEmail")
    private WebElement userIdInput;

    @FindBy(id = "formPassword")
    private WebElement passwordInput;

    @FindBy(css = "button.login-button")
    private WebElement loginButton;

    @FindBy(css = "img.passowrd-visible")
    private WebElement passwordToggle;

    @FindBy(xpath = "//p[@class='normal-text' and contains(text(),'Invalid Credentials')]")
    private WebElement errorMessage;

    @FindBy(xpath = "//button[contains(text(),'Reload')]")
    private WebElement reloadButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void handleReloadIfPresent() {
        try {
            wait.until(ExpectedConditions.visibilityOf(reloadButton));
            reloadButton.click();
            PageFactory.initElements(driver, this);
            wait.until(ExpectedConditions.visibilityOf(userIdInput));
        } catch (TimeoutException ignored) {}
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOf(userIdInput));
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        wait.until(ExpectedConditions.visibilityOf(loginButton));
    }

    public void enterUserId(String userId) {
        wait.until(ExpectedConditions.visibilityOf(userIdInput)).clear();
        userIdInput.sendKeys(userId);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput)).clear();
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void togglePasswordVisibility() {
        wait.until(ExpectedConditions.elementToBeClickable(passwordToggle)).click();
    }

    public boolean isLoginButtonEnabled() {
        wait.until(ExpectedConditions.visibilityOf(loginButton));
        return loginButton.isEnabled();
    }

    public String getPasswordFieldType() {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        return passwordInput.getAttribute("type");
    }

    public String getErrorMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(errorMessage)).getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

    public boolean areElementsPresent() {
        try {
            wait.until(ExpectedConditions.visibilityOf(userIdInput));
            wait.until(ExpectedConditions.visibilityOf(passwordInput));
            wait.until(ExpectedConditions.visibilityOf(loginButton));
            wait.until(ExpectedConditions.visibilityOf(passwordToggle));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
