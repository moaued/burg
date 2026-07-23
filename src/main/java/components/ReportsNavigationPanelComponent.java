package components;

import com.helger.commons.mutable.AbstractMutableNumeric;
import com.shaft.driver.SHAFT;
import io.qameta.allure.Step;
import java.io.File;
import lombok.Getter;
import org.openqa.selenium.By;
import pages.reports.BriefcaseReportPage;
import pages.reports.EmployeePermissionsReportPage;
import pages.reports.FollowUpDetailsReportPage;
import pages.reports.FollowUpEmployeePerformanceReportPage;
import pages.reports.FollowUpReportPage;
import pages.reports.PerformanceMeasurementReportPage;
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
  private By employeePermissionsReportEntery  = By.xpath(
      "//a[contains(@href,'UsersPermissionsReport')]");
  private By followUpEmployeePerformanceReportEntery  = By.xpath(
      "//a[contains(@href,'PerformanceMeasurementReportFollowUpEmp')]");
  private By performanceMeasurementReportEntery  =
      By.xpath("//a[.//span[text()='مؤشر قياس الاداء']]");


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

  @Step("الذهاب الى صفحة ’تقرير صلاحيات الموظفين’")
  public EmployeePermissionsReportPage navigateToEmployeePermissionsReport() {
    driver.element().click(employeePermissionsReportEntery);
    return new EmployeePermissionsReportPage(driver);
  }

  @Step("الذهاب الى صفحة ’تقرير قياس أداء موظفي المتابعة’")
  public FollowUpEmployeePerformanceReportPage navigateToFollowUpEmployeePerformanceReport() {
    driver.element().click(followUpEmployeePerformanceReportEntery);
    return new FollowUpEmployeePerformanceReportPage(driver);
  }

  @Step("الذهاب الى صفحة ’تقرير مؤشر قياس الاداء’")
  public PerformanceMeasurementReportPage navigateToPerformanceMeasurementReport() {
    driver.element().click(performanceMeasurementReportEntery);
    return new PerformanceMeasurementReportPage(driver);
  }


/*  protected By pdfButton;
  protected By excelButton;

  @Step(" تصدير PDF في التقارير")
  public void clickExportPdf() {
    driver.element().click(pdfButton);
  }

  @Step(" تصدير Excel في التقارير")
  public void clickExportExcel() {
    driver.element().click(excelButton);
  }
  @Step("التحقق من  تصدير ملفات الPDF و الExcel في التقارير")
  public boolean waitUntilFileDownloaded(String extension) {
    File downloadFolder =
        new File(System.getProperty("user.home") + "/Downloads");

    long timeout = System.currentTimeMillis() + 30000;

    while (System.currentTimeMillis() < timeout) {

      File[] files = downloadFolder.listFiles(
          file -> file.getName().toLowerCase().endsWith(extension)
      );

      if (files != null && files.length > 0) {
        return true;
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        return false;
      }
    }

    return false;
  }*/

}
