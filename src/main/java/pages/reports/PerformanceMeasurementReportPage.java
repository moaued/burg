package pages.reports;

import com.shaft.driver.SHAFT;
import components.HorizontalMenusComponent;
import components.ReportsNavigationPanelComponent;
import io.qameta.allure.Step;
import java.io.File;
import org.openqa.selenium.By;

public class PerformanceMeasurementReportPage extends ReportsNavigationPanelComponent {

  private By reportTypeMenu = By.xpath("//input[@id='ReportTypeId']");
  private By transactionFromDate = By.id("fromDateCal");
  private By transactionToDate = By.id("toDateCal");
  private By generateReportButton = By.id("btnSearch");
  private By loadingSpinner = By.id("loadingModal");
  private By reportGrid = By.xpath("//table[@id='grid-table-GridPerformanceMeasurementReport']");
  private By btnExport = By.id("TransactionPrint");
  private By btnExportPdf = By.xpath("//button[contains(text(),'تصدير Pdf')]");



  public PerformanceMeasurementReportPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));

  }


  @Step("عرض التقرير")
  public PerformanceMeasurementReportPage generateReportForTransactionType(
      String fromDate, String toDate, String selectedReportType) {
    driver.element().select(reportTypeMenu, selectedReportType);
    driver.element().type(transactionFromDate, fromDate)
        .type(transactionToDate, toDate)
        .click(generateReportButton)
        .verifyThat(loadingSpinner).isVisible();
    return this;
  }

  public boolean isResultsGridDisplayed() {
    return driver.element().isElementDisplayed(reportGrid);
  }

  @Step(" طباعة PDF في تقرير مؤشر قياس الأداء-قياس أداء الإدارات")
  public PerformanceMeasurementReportPage clickExport() {
    driver.element().click(btnExport);
    return this;
  }
  @Step(" تصدير PDF في تقرير مؤشر قياس الأداء-قياس أداء الموظفين")
  public PerformanceMeasurementReportPage clickExportPdf() {
    driver.element().click(btnExportPdf);
    return this;
  }
  @Step("التحقق من  تصدير ملفات الPDF  في تقرير مؤشر قياس الأداء")
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
  }

}
