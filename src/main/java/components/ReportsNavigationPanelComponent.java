package components;

import com.shaft.driver.SHAFT;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import pages.reports.BriefcaseReportPage;
import pages.reports.FollowUpDetailsReportPage;
import pages.reports.FollowUpReportPage;
import pages.reports.SentTransactionsReportPage;
import pages.reports.TasksReportPage;
import pages.reports.TransactionsFollowUpReportPage;
import pages.reports.TransactionsGeneralReportPage;

public class ReportsNavigationPanelComponent {

  protected SHAFT.GUI.WebDriver driver;
  @Getter
  protected HorizontalMenusComponent hMComponent;

  private By transactionGeneralReportReportEntery = By.xpath(
      "//a[contains(@href,'/TransactionGeneralReport')]");
  private By transactionFollowUpReportEntery = By.xpath(
      "//a[contains(@href,'/TransactionFollowUpReport')]");
  private By tasksReportEntery = By.xpath("//a[contains(@href,'/TasksReport')]");
  private By followUpReportEntery = By.xpath("//a[contains(@href,'/FollowupReport')]");
  private By followUpDetailsReportEntery = By.xpath(
      "//a[contains(@href,'/FollowupDetailsReport')]");
  private By sentTransactionsReportEntery = By.xpath(
      "//a[contains(@href,'/SentTransactionReport')]");
  private By briefcaseDisplayReportEntery = By.xpath(
      "//a[contains(@href,'/BriefcaseDisplayReport')]");


  public ReportsNavigationPanelComponent(SHAFT.GUI.WebDriver driver,
      HorizontalMenusComponent hMComponent) {
    this.driver = driver;
    this.hMComponent = hMComponent;
  }

  @Step("الذهاب الى صفحة ’نسب الانجاز على طلبات المتابعة’")
  public TransactionsGeneralReportPage navigateToTransactionsGeneralReport() {
    driver.element().click(transactionGeneralReportReportEntery);
    return new TransactionsGeneralReportPage(driver);
  }

  @Step("الذهاب الى صفحة ’نسب الانجاز على طلبات المتابعة’")
  public TransactionsFollowUpReportPage navigateToTransactionFollowUpReport() {
    driver.element().click(transactionFollowUpReportEntery);
    return new TransactionsFollowUpReportPage(driver);
  }

  @Step("الذهاب الى صفحة ’تقرير المهام’")
  public TasksReportPage navigateTasksReport() {
    driver.element().click(tasksReportEntery);
    return new TasksReportPage(driver);
  }

  @Step("الذهاب الى صفحة ’تقرير المتابعة’")
  public FollowUpReportPage navigateToFollowUpReport() {
    driver.element().click(followUpReportEntery);
    return new FollowUpReportPage(driver);
  }

  @Step("الذهاب الى صفحة ’تقرير المتابعة التفصيلى’")
  public FollowUpDetailsReportPage navigateToFollowUpDetailsReport() {
    driver.element().click(followUpDetailsReportEntery);
    return new FollowUpDetailsReportPage(driver);
  }

  @Step("الذهاب الى صفحة ’تقرير المعاملات المرسلة’")
  public SentTransactionsReportPage navigateToSentTransactionReport() {
    driver.element().click(sentTransactionsReportEntery);
    return new SentTransactionsReportPage(driver);
  }

  @Step("الذهاب الى صفحة ’تقرير حقيبة العرض’")
  public BriefcaseReportPage navigateToBriefcaseReport() {
    driver.element().click(briefcaseDisplayReportEntery);
    return new BriefcaseReportPage(driver);
  }
}
