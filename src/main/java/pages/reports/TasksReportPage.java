package pages.reports;

import com.shaft.driver.SHAFT;
import components.HorizontalMenusComponent;
import components.ReportsNavigationPanelComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TasksReportPage extends ReportsNavigationPanelComponent {

  private By transactionTypeMenu = By.id("TransactionType");
  private By transactionFromDate = By.id("fromDateCal");
  private By transactionToDate = By.id("toDateCal");
  private By generateReportButton = By.id("btnSearch");
  private By loadingSpinner = By.id("loadingModal");
  private By reportResultsCount = By.id("TotalCount");
  private By transactionTypeInReport = By.id("lblTransactionType");
  private By transactionDateInReport = By.id("lblFromTo");
  private By reportResultsBody = By.className("tbody");
  private By reportResultsGridRows = By.xpath("//tr[contains(@class,'datarow')]");

  public TasksReportPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
  }

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
  }

  @Step("التأكد  من وجود مساحة عرض نتائج التقرير")
  public boolean isResultsGridDisplayed() {
    return driver.element().getText(transactionTypeMenu).equals("الكل")
        ? driver.element().getElementsCount(reportResultsGridRows) > 0
        : driver.element().isElementDisplayed(reportResultsBody);
  }

  @Step("عرض التقرير")
  public TasksReportPage generateReportForTransactionType(
      String fromDate, String toDate, String selectedTransactionType) {
    driver.element().select(transactionTypeMenu, selectedTransactionType);
    driver.element().type(transactionFromDate, fromDate)
        .type(transactionToDate, toDate)
        .click(generateReportButton)
        .verifyThat(loadingSpinner).isVisible();
    return this;
  }

}
