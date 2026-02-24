package pages.reports;

import com.shaft.driver.SHAFT;
import components.HorizontalMenusComponent;
import components.ReportsNavigationPanelComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TransactionsFollowUpReportPage extends ReportsNavigationPanelComponent {


  private By transactionFromDate = By.id("fromDateCal");
  private By transactionToDate = By.id("toDateCal");
  private By generateReportButton = By.id("btnSearch");
  private By loadingSpinner = By.id("loadingModal");
  private By numberOfReportRows = By.xpath("//tr[contains(@class,'grid-row')]");

  public TransactionsFollowUpReportPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
  }

  @Step("الحصول على عدد صفوف التقرير")
  public int getRowsCount() {
    return driver.element().getElementsCount(numberOfReportRows);
  }

  @Step("عرض التقرير")
  public TransactionsFollowUpReportPage generateReportForTransactionFollowUp(
      String fromDate, String toDate) {
    driver.element().type(transactionFromDate, fromDate)
        .type(transactionToDate, toDate)
        .click(generateReportButton)
        .verifyThat(loadingSpinner).isVisible();
    return this;
  }

}
