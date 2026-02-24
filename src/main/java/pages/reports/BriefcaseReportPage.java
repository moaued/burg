package pages.reports;

import com.shaft.driver.SHAFT;
import components.HorizontalMenusComponent;
import components.ReportsNavigationPanelComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class BriefcaseReportPage extends ReportsNavigationPanelComponent {

  private By transactionFromDate = By.id("fromDateCal");
  private By transactionToDate = By.id("toDateCal");
  private By generateReportButton = By.id("btnSearch");
  private By loadingSpinner = By.id("loadingModal");
  private By reportRowsCount = By.id("TotalCount");
  private By transactionDateInReport = By.id("lblFromTo");
  private By reportResultsBody = By.className("tbody");
  private By resultsTransactionNumber = By.xpath("//td[@data-name='Number']");
  private String resultTransactionNumberText = "(//td[@data-name='Number'])[%s]";

  public BriefcaseReportPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
  }


  @Step("التأكد  من وجود مساحة عرض نتائج التقرير")
  public boolean isResultsGridDisplayed() {
    return driver.element().isElementDisplayed(reportResultsBody);
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


  @Step("عرض التقرير")
  public BriefcaseReportPage generateReportForTransactionType(String fromDate, String toDate) {
    driver.element().type(transactionFromDate, fromDate)
        .type(transactionToDate, toDate)
        .click(generateReportButton)
        .verifyThat(loadingSpinner).isVisible();
    return this;
  }

  @Step("الحصول على رقم المعاملة الأولى من نتائج التقرير")
  public String getFirstResultTransactionNumber() {
    return driver.element().getText(By.xpath(String.format(resultTransactionNumberText, 1)));
  }

  @Step("الحصول على رقم معاملة عشوائية من نتائج التقرير")
  public String getRandomResultTransactionNumber() {
    int numberOfResults = driver.element().getElementsCount(resultsTransactionNumber);
    int randomIndex = (int) (Math.random() * numberOfResults) + 1;
    return driver.element()
        .getText(By.xpath(String.format(resultTransactionNumberText, randomIndex)));
  }

}
