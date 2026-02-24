package pages.Luggages;

import com.shaft.driver.SHAFT;
import components.HorizontalMenusComponent;
import components.SystemAdminComponent;
import components.TransactionsNavigationPanelComponent;
import components.TransactionsOperationsComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import utils.ElementsOperations;

public class LuggagePage extends TransactionsNavigationPanelComponent {

//  private final SHAFT.GUI.WebDriver driver;

  // ================= Locators =================
  // Row by Organization Name
//  private By orgUnitRow =
//      By.xpath("//tr[.//span[@id='EntityName' and contains(text(),'مكتب نائب الوزير')]]");

  private By orgUnitRow(String orgUnitName) {
    return By.xpath(
        "//tr[.//span[@id='EntityName' and contains(normalize-space(),'" + orgUnitName + "')]]"
    );
  }

  // Checkboxes
  private By originalCheckbox =
      By.id("AssignmentPaperMainSource_2");
  private By originalCheckbox2 =
      By.id("AssignmentPaperMainSource_11");
  private By copyCheckbox =
      By.id("MainPlaceHolderAssignmentPaperCopy_9");

  // Action dropdown
  private By actionInput =
      By.id("ddlActionId2");
  private By actionOption =
     By.cssSelector("li.ui-menu-item[data-value='214']");


  // View level (اطلاع / توقيع)
  private By viewLevelCheckbox =
      By.xpath("//input[@type='checkbox' and contains(@name,'ForView')]");

  // Save button
  private By saveButton =
      By.id("btnSaveOutboundInternal");
  private By successToast =
      By.xpath("//button[normalize-space(text())='نعم']");

  // Success message
  private By successMessage =
      By.xpath("//*[contains(text(),'تم الحفظ') or contains(text(),'بنجاح')]");
  private By closePopup =
      By.xpath("//button[normalize-space(text())='إغلاق']");




  // ================= Dynamic Locators =================

  // صف الإدارة
  private By rowByOrgUnit(String orgUnitName) {
    return By.xpath("//div[contains(@class,'box-grid')]//div[contains(text(),'" + orgUnitName + "')]/ancestor::div[contains(@class,'box-grid')]");
  }

  // صف المعاملة بناءً على رقم المعاملة
  private By rowByTransactionNumber(String transactionNumber) {
    return By.xpath("//div[contains(@class,'box-grid')]//div[normalize-space()='" + transactionNumber + "']/ancestor::div[contains(@class,'box-grid')]");
  }

  // Checkbox الأصل والنسخة داخل الصف
//  private By originalCheckbox(String orgUnitName) {
//    return By.xpath("//div[contains(@class,'box-grid')]//div[contains(text(),'" + orgUnitName + "')]//input[@title='أصل' and contains(@class,'repRadio')]");
//  }

  private By copyCheckbox(String orgUnitName) {
    return By.xpath("//div[contains(@class,'box-grid')]//div[contains(text(),'" + orgUnitName + "')]//input[@title='نسخة' and contains(@class,'repCheck')]");
  }

  // Action input داخل الصف
//  private By actionInput(String orgUnitName) {
//    return By.xpath("//div[contains(@class,'box-grid')]//div[contains(text(),'" + orgUnitName + "')]//input[contains(@id,'ddlActionId') and contains(@class,'ui-autocomplete-input')]");
//  }

  // View level checkbox داخل الصف
  private By viewLevelCheckbox(String orgUnitName) {
    return By.xpath("//div[contains(@class,'box-grid')]" +
        "[.//div[normalize-space()='" + orgUnitName + "']]" +
        "//input[@type='checkbox' and contains(@name,'ForView')]");
  }

  // Tabs
//  private By underProcessTab =
//      By.xpath("//span[normalize-space()='قيد الإجراء']");

  // Filter & Search
  private final By filterIcon =
      By.className("icon-filter");

  private final By transactionIdInput =
      By.cssSelector("#TransId.form-control");

  private final By searchButton =
      By.xpath("//input[@type='button' and @value='بحث']");

  // Grid (dynamic – based on transaction number)
  private By transactionRow(String transactionNumber) {
    return By.xpath("//*[contains(text(),'" + transactionNumber + "')]");
  }

  // Prepare button (based on transaction row)
  private By prepareButton(String transactionNumber) {
    return By.xpath(
        "//div[contains(@class,'box-grid')]" +
            "[.//div[normalize-space()='" + transactionNumber + "']]" +
            "//a[contains(@onclick,'SetupBriefcase')]"
    );
//        By.xpath(
//        "//*[contains(text(),'" + transactionNumber + "')]" +
//            "/ancestor::tr//i[contains(@class,'icon-prepare') or contains(@class,'la-cog')]"
//    );
  }

  // ================= Constructor =================

  @Getter
  private TransactionsOperationsComponent transactionsOperationsComponent;
  @Getter
  private SystemAdminComponent systemAdminComponent;

  public LuggagePage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
    transactionsOperationsComponent = new TransactionsOperationsComponent(driver);
    systemAdminComponent = new SystemAdminComponent(driver);
  }

  // ================= Actions =================

//  @Step("الانتقال إلى تبويب قيد الإجراء ")
//  public LuggagePage goToUnderProcessTab() {
//    driver.element().click(underProcessTab);
//    return this;
//  }

  @Step("البحث عن معاملة داخل الحقيبة ")
  public LuggagePage searchForTransaction(String transactionNumber) {
    driver.element().click(filterIcon);
    driver.element().type(transactionIdInput, transactionNumber);
    driver.element().click(searchButton);
    return this;
  }

  @Step("التحقق من وجود المعاملة في نتائج الحقيبة")
  public LuggagePage assertTransactionExists(String transactionNumber) {

    driver.assertThat()
        .element(transactionRow(transactionNumber))
        .exists()
        .perform();

    return this;
  }

  @Step("الضغط على زر الإعداد للمعاملة ")
  public LuggagePage clickPrepareButton(String transactionNumber) {
    driver.element().click(prepareButton(transactionNumber));
    return this;
  }

  @Step("إعداد أصل المعاملة لإدارة: {orgUnitName}")
  public LuggagePage setupOriginalTransaction() {
    driver.element().click(originalCheckbox);
    driver.element().click(copyCheckbox);
    return this;
  }

  public LuggagePage selectAction()  {
    driver.element().click(actionInput);
    driver.element().click(actionOption);
    return this;
  }

  public LuggagePage selectViewLevel(String viewLevel) {
    if ("للاطلاع".equalsIgnoreCase(viewLevel)) {
      driver.element().click(viewLevelCheckbox);
    }
    return this;
  }

  @Step("حفظ إعداد الحقيبة")
  public LuggagePage saveSetup() {
    driver.element().click(saveButton);
    driver.element().click(successToast);
    return this;
  }


// ===========================
//       Assertions
//  ===========================


  @Step("التحقق من حفظ الإعداد بنجاح")
  public LuggagePage assertSetupSavedSuccessfully() {
    driver.assertThat()
        .element(successMessage)
        .isVisible()
        .perform();
    driver.element().click(closePopup);
    return this;
  }

}
