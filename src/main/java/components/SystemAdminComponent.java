package components;

import com.shaft.driver.SHAFT;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.transactions.MyTransactionsPage;

public class SystemAdminComponent {

  private SHAFT.GUI.WebDriver driver;

  private By departmentDownArrow = By.xpath(
      "//a[contains(@class,'department-name')]//i[contains(@class,'la-angle-down')]");
  private By currentDepartmentName = By.cssSelector(".d-inline.manager");



  private HorizontalMenusComponent horizontalMenusComponent;


  public SystemAdminComponent(SHAFT.GUI.WebDriver driver) {
    this.driver = driver;
    horizontalMenusComponent = new HorizontalMenusComponent(driver);
  }

  @Step("تغيير القسم")
  public MyTransactionsPage changeDepartment(String department) {
    horizontalMenusComponent.navigateToTransactionsTab();
    By departmentName = By.xpath("//span[text()='" + department + "']");
    driver.element().clickUsingJavascript(departmentDownArrow)
        .scrollToElement(departmentName)
        .clickUsingJavascript(departmentName);
    // driver.element().select(departmentMenu, department);
//    driver.browser().refreshCurrentPage();
    driver.element().waitUntilElementTextToBe(currentDepartmentName, department);
    driver.browser().captureScreenshot();
    return new MyTransactionsPage(driver);
  }

  @Step("تغيير القسم")
  public MyTransactionsPage changeDepartment2(String department) {
    horizontalMenusComponent.navigateToTransactionsTab();
//    By departmentName = By.xpath("//span[normalize-space()='" + department + "']");
    By departmentName = By.xpath("//a[.//span[normalize-space()='" +department +"']]");

    //a[.//span[normalize-space()='مكتب نائب الوزير']]

//    By departmentName = By.xpath("//span[text()='" + department + "']");
    driver.element().click(departmentDownArrow)
        .scrollToElement(departmentName)
        .click(departmentName);

    driver.element().waitUntil(ExpectedConditions.textToBePresentInElementLocated(
        currentDepartmentName, department));

//    driver.browser().refreshCurrentPage();
//    driver.element().waitUntilElementTextToBe(currentDepartmentName, department);
    driver.browser().captureScreenshot();
    return new MyTransactionsPage(driver);
  }
}
