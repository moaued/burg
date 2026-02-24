package pages.dispatch;

import com.shaft.driver.SHAFT.GUI.WebDriver;
import components.DispatchReportTabsComponent;
import components.HorizontalMenusComponent;
import components.TransactionsNavigationPanelComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import utils.ElementsOperations;

public class PrintDispatchReportPage extends TransactionsNavigationPanelComponent {

  private By externalDepartmentNumber = By.cssSelector(".externalParties .txtDepartmentNumber");
  private By externalDepartmentName = By.cssSelector(".externalParties .txtDepartmentName");
  private By orgUnitAutoSuggestionMenu = By.id("divAutoComplateMenu");
  private By orgUnitFirstSuggestion = By.xpath(
      "//div[@id='divAutoComplateMenu']/div[position()=1]");
  //private By dispatchDeliveryCheckBox = By.id("isSpl");
  private By dispatchDeliveryCheckBox = By.xpath(
      "//input[@id='isSpl']/ancestor::div[@class='checkbox']");
  private By dispatchReportSearchButton = By.id("btnSearch");
  private By copiesArchivingRows = By.xpath(
      "//table[@id='grid-table-CopiesgrdArchiving']//tr[contains(@class,'grid-row')]");
  private By firstCheckboxInResultsGrid = By.xpath(
      "(//a[@class='edit_link']/input[@type='checkbox'])[1]");
  private By reporter = By.id("ddlBasicReporter");
  private By printSplReportButton = By.id("btnPrintTransactionsDeliveryReportSpl");

  @Getter
  private DispatchReportTabsComponent dispatchReportTabsComponent;

  public PrintDispatchReportPage(WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
    dispatchReportTabsComponent = new DispatchReportTabsComponent(driver);
  }

  @Step("الحصول على عدد النسخ فى جدول النتائج")
  private int getNumberOfRows() {
    return driver.element().getElementsCount(copiesArchivingRows);
  }

  @Step("البحث عن معاملات الادارة")
  public PrintDispatchReportPage searchForTransaction(String orgUnitName) {
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(externalDepartmentName))
        .type(externalDepartmentName, orgUnitName)
        .waitUntil(ElementsOperations.waitForElementToBeReady(orgUnitAutoSuggestionMenu))
        .click(orgUnitFirstSuggestion)
        .click(dispatchDeliveryCheckBox)
        .click(dispatchReportSearchButton)
        .waitUntilNumberOfElementsToBeMoreThan(copiesArchivingRows, 0);
    return this;
  }

  @Step("طباعة بيان سبل لمعاملة من جدول النتائج")
  public PrintDispatchReportPage printSplReport(String reporterName) {
    driver.element()
        //.waitToBeReady(firstCheckboxInResultsGrid)
        .click(firstCheckboxInResultsGrid)
        .select(reporter, reporterName)
        .click(reporter).click(printSplReportButton);
    driver.browser().waitUntilNumberOfWindowsToBe(2).waitUntilNumberOfWindowsToBe(1);
    return this;
  }

}
