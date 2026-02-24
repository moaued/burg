package pages.reports;

import com.shaft.driver.SHAFT;
import components.HorizontalMenusComponent;
import components.ReportsNavigationPanelComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TransactionsGeneralReportPage extends ReportsNavigationPanelComponent {

  private By transactionTypeMenu = By.id("TransactionType");
  private By transactionFromDate = By.id("fromDateCal");
  private By transactionToDate = By.id("toDateCal");
  private By generateReportButton = By.id("btnSearch");
  private By loadingSpinner = By.id("loadingModal");
  private By reportRowsCount = By.id("TotalCount");
  private By transactionTypeInReport = By.id("lblTransactionType");
  private By transactionDateInReport = By.id("lblFromTo");
  private By reportResultsBody = By.className("tbody");
  private By reportResultsGridRows = By.xpath("//tr[contains(@class,'datarow')]");

  public TransactionsGeneralReportPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
  }

/*  @Step("التأكد  من وجود مساحة عرض نتائج التقرير")
  public boolean isResultsGridDisplayed() {
    return driver.element().getText(transactionTypeMenu).equals("الكل")
        ? driver.element().getElementsCount(reportResultsGridRows) > 0
        : driver.element().isElementDisplayed(reportResultsBody);
  }*/

  @Step("التأكد  من وجود مساحة عرض نتائج التقرير")
  public boolean isResultsGridDisplayed() {
    return driver.element().getElementsCount(reportResultsGridRows) > 0
        || driver.element().isElementDisplayed(reportResultsBody);
  }


  @Step("الحصول على عدد صفوف التقرير")
  public int getRowsCount() {
    String rowsCountValue = driver.element().getAttribute(reportRowsCount, "value");
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

  /**
   * ToDo
   * Currently the report is not working & there is an issue got resolved
   * The page & test classes were added as the work on the reports is in progress
   * To be modified to use the TransactionType when the report issue gets resolved
   */
  @Step("عرض التقرير")
  public TransactionsGeneralReportPage generateReportForTransactionType(
      String fromDate, String toDate, String transactionType) {
    driver.element().type(transactionTypeMenu, transactionType);
    driver.element().type(transactionFromDate, fromDate)
        .type(transactionToDate, toDate)
        .click(generateReportButton)
        .verifyThat(loadingSpinner).isVisible();
    return this;
  }

}
