package pages;

import com.shaft.driver.SHAFT;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pages.transactions.MyTransactionsPage;

public class LoginPage {

  SHAFT.TestData.JSON testData = new SHAFT.TestData.JSON("appData.json");
  SHAFT.TestData.JSON testData2 = new SHAFT.TestData.JSON("receiverUser.json");

  private SHAFT.GUI.WebDriver driver;
  private By usernameField = By.id("UserName");
  private By passwordField = By.id("Password");
  private By userTypeSelection = By.id("loginType");
  private By normalUserRadioButton = By.xpath("//input[@id='IsWindowsLogin'][1]");
  private By activeUserRadioButton = By.xpath("//input[@id='IsWindowsLogin'][2]");
  private By loginButton = By.xpath("//button[@type='submit']");
  private By logoutButton = By.cssSelector("a[href='/MCS.UI.Tenants/User/Login/SignOut']");

  public LoginPage(SHAFT.GUI.WebDriver driver) {
    this.driver = driver;
  }

  private void showUserTypes() {
    WebDriver wDriver = driver.getDriver();
    ((JavascriptExecutor) wDriver)
        .executeScript("arguments[0].style.display = 'block';",
            wDriver.findElement(userTypeSelection));
  }

  @Step("الدخول الى المنصة")
  public MyTransactionsPage loginToTheApp() {
    showUserTypes();
    driver.element().type(usernameField, testData.getTestData("username"))
        .type(passwordField, testData.getTestData("password"))
        .click(normalUserRadioButton)
        .click(loginButton);
    //driver.assertThat().element(loginButton).doesNotExist().perform();
    return new MyTransactionsPage(driver);
  }

  @Step("الخروج من المنصة")
  public MyTransactionsPage logoutToTheApp() {
    driver.element().click(logoutButton);
    return new MyTransactionsPage(driver);
  }

  @Step("الدخول الى المنصة بمستخدم مستلم للمعاملة")
  public MyTransactionsPage loginToTheApp2() {
    showUserTypes();
    driver.element().type(usernameField, testData2.getTestData("credentials.username"))
        .type(passwordField, testData2.getTestData("credentials.password"))
        .click(normalUserRadioButton)
        .click(loginButton);
    return new MyTransactionsPage(driver);
  }

}
