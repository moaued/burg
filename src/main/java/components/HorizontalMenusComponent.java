package components;

import com.shaft.driver.SHAFT;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.dispatch.PrintDispatchReportPage;
import pages.unifiedNumber.UnifiedNumberPage;
import pages.orgUnitTransactions.OrgUnitRecievalPage;
import pages.reports.TransactionsReportPage;
import pages.transactions.MyTransactionsPage;

public class HorizontalMenusComponent {

  private SHAFT.GUI.WebDriver driver;

  private By reportsTab = By.id("TransactionReport");
  private By tansactionsTab = By.id("MyTransactions");
  private By orgUnitTransactions = By.xpath("//a[@href='/MCS.UI.Tenants/User/File/OrgUnit']");
  private By receivingimages=   By.id("selected-copy");
  private By unifiedNumber = By.id("SearchInboundOutboundLinkedNumber");
  private By dispatchReport = By.id("DispatchReport");

  public HorizontalMenusComponent(SHAFT.GUI.WebDriver driver) {
    this.driver = driver;
  }

  @Step("الذهاب الى تبويب التقارير")
  public TransactionsReportPage navigateToReportsTab() {
    driver.element().click(reportsTab);
    return new TransactionsReportPage(driver);
  }

  @Step("الذهاب الى تبويب المعاملات")
  public MyTransactionsPage navigateToTransactionsTab() {
    driver.element().click(tansactionsTab);
    return new MyTransactionsPage(driver);
  }

  @Step("الذهاب الى تبويب معاملات الادارة")
  public OrgUnitRecievalPage navigateToOrgUnitTransactionsTab() {
    driver.element().click(orgUnitTransactions);
    return new OrgUnitRecievalPage(driver);
  }
  @Step("استقبال الصور والتعميم")
  public OrgUnitRecievalPage receivinganddistributingimages() {
    driver.element().click(receivingimages);
    return new OrgUnitRecievalPage(driver);
  }

  @Step("الذهاب الى تبويب الرقم الموحد")
  public UnifiedNumberPage navigateToUnifiedNumberTab() {
    driver.element().click(unifiedNumber);
    return new UnifiedNumberPage(driver);
  }

  @Step("الذهاب الى تبويب الارساليات")
  public PrintDispatchReportPage navigateToDispatchReportTab() {
    driver.element().click(dispatchReport);
    return new PrintDispatchReportPage(driver);
  }

  @Step("الذهاب الى تبويب الرقم الموحد")
  public UnifiedNumberPage navigateToUnifiedNumberTab2() {
    driver.element().click(By.cssSelector("#SearchInboundOutboundLinkedNumber a"));
    return new UnifiedNumberPage(driver);
  }
}
