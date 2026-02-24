package pages.orgUnitTransactions;

import com.shaft.driver.SHAFT;
import components.HorizontalMenusComponent;
import components.OrgUnitNavigationPanelComponent;
import components.SystemAdminComponent;
import components.TransactionsOperationsComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;

public class OrgUnitRecievalPage extends OrgUnitNavigationPanelComponent {

  private By firstTransactionNumber = By.xpath(
      "(//div[@class='box-grid']//div[contains(@class,'ml-auto')])[1]");
  private By firstTransactionDescription = By.xpath("(//div[@id='example'])[1]");

  @Getter
  private TransactionsOperationsComponent transactionsOperationsComponent;
  @Getter
  private SystemAdminComponent systemAdminComponent;

  public OrgUnitRecievalPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
    transactionsOperationsComponent = new TransactionsOperationsComponent(driver);
    systemAdminComponent = new SystemAdminComponent(driver);
  }


  @Step("الحصول على رقم المعاملة الأولى")
  public String getFirstTransactionNumber() {
    return driver.element().getText(firstTransactionNumber);
  }

  @Step("الحصول على موضوع/وصف المعاملة الأولى")
  public String getFirstTransactionDescription() {
    return driver.element().getText(firstTransactionDescription);
  }
}
