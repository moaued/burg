package components;

import com.shaft.driver.SHAFT.GUI.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.dispatch.PrintDispatchReportPage;

public class DispatchReportTabsComponent {

  private By printDispatchReportTab = By.xpath("//a[@aria-controls='sub-tab-1']");
  private By approvalDispatchDeliveryReportTab = By.xpath("//a[@aria-controls='sub-tab-2']");

  protected WebDriver driver;

  public DispatchReportTabsComponent(WebDriver driver) {
    this.driver = driver;
  }

  @Step("الذهاب الى تبويب \"تسليم إرسالية لمراسل الإدارة\"")
  public PrintDispatchReportPage navigateToPrintDispatchReportPage() {
    driver.element().click(printDispatchReportTab);
    return new PrintDispatchReportPage(driver);
  }

}
