package pages.reports;

import com.shaft.driver.SHAFT;
import components.HorizontalMenusComponent;
import components.ReportsNavigationPanelComponent;
import io.qameta.allure.Step;
import java.io.File;
import org.openqa.selenium.By;

public class FollowUpEmployeePerformanceReportPage extends ReportsNavigationPanelComponent {

  private By transactionTypeMenu = By.id("TransactionType");
  private By transactionFromDate = By.xpath("//input[@id='fromDateCal']");
  private By transactionToDate = By.xpath("//input[@id='toDateCal']");
  private By generateReportButton = By.id("btnSearch");
  private By loadingSpinner = By.xpath("//div[@id='loadingModal']");
  private By reportResultsBody = By.className("tbody");
  private By reportResultsGridRows = By.xpath("//tr[contains(@class,'datarow')]");

  private By btnExportPdf = By.xpath(
      "//button[contains(text(),'تصدير Pdf')]");

  public FollowUpEmployeePerformanceReportPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
  }

/*
  @Step("الحصول على عدد صفوف التقرير")
  public int getResultsCount() {
    driver.element().waitUntilNumberOfElementsToBeMoreThan(reportResultsCount, 0);
    String rowsCountValue = driver.element().getAttribute(reportResultsCount, "value");
    return Integer.parseInt(rowsCountValue);
  }

  @Step("الحصول على تاريخ التقرير")
  public String getDateRangeInReport() {
    return driver.element().getText(transactionDateInReport);
  }

  @Step("الحصول على نوع المعاملة داخل التقرير")
  public String getTransactionTypeInReport() {
    return driver.element().getText(transactionTypeInReport);
  }*/

  @Step("التأكد  من وجود مساحة عرض نتائج التقرير")
  public boolean isResultsGridDisplayed() {
    return driver.element().getText(transactionTypeMenu).equals("الكل")
        ? driver.element().getElementsCount(reportResultsGridRows) > 0
        : driver.element().isElementDisplayed(reportResultsBody);
  }

  @Step("عرض التقرير")
  public FollowUpEmployeePerformanceReportPage generateReportForTransactionType(
      String fromDate, String toDate, String selectedTransactionType) {
//    driver.element().select(transactionTypeMenu, selectedTransactionType);
    driver.element().type(transactionFromDate, fromDate)
        .type(transactionToDate, toDate)
        .click(generateReportButton)
        .verifyThat(loadingSpinner).isVisible();
    return this;
  }


  @Step(" تصدير PDF في تقرير قياس أداء موظفي المتابعة")
  public FollowUpEmployeePerformanceReportPage clickExportPdf() {
    driver.element().click(btnExportPdf);
    return this;
  }
  @Step("التحقق من  تصدير ملفات الPDF  في تقرير قياس أداء موظفي المتابعة")
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
