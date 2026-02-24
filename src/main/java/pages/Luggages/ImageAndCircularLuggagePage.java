package pages.Luggages;

import com.shaft.driver.SHAFT;
import components.HorizontalMenusComponent;
import components.SystemAdminComponent;
import components.TransactionsNavigationPanelComponent;
import components.TransactionsOperationsComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;

public class ImageAndCircularLuggagePage extends TransactionsNavigationPanelComponent {

  // ================= Locators =================

  private By orgUnitRow(String orgUnitName) {
    return By.xpath(
        "//tr[.//span[@id='EntityName' and contains(normalize-space(),'" + orgUnitName + "')]]"
    );
  }

  // Checkboxes
  private By originalCheckbox =
      By.id("AssignmentPaperMainSource_14");
  private By originalCheckbox2 =
      By.id("AssignmentPaperMainSource_11");
  private By copyCheckbox =
      By.id("MainPlaceHolderAssignmentPaperCopy_13");

  // Action dropdown
  private By actionInput =
      By.id("ddlActionId13");
  private By actionOption =
      By.cssSelector("li.ui-menu-item[data-value='214']");


  // View level (اطلاع / توقيع)
  private By viewLevelCheckbox =
      By.xpath("(//input[@type='checkbox' and contains(@onclick,'myFunctionForView')])[1]");

  private By signCheckbox =
      By.xpath("(//input[contains(@onclick,'myFunctionForSign')])[2]");

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

  private By copyCheckbox(String orgUnitName) {
    return By.xpath("//div[contains(@class,'box-grid')]//div[contains(text(),'" + orgUnitName + "')]//input[@title='نسخة' and contains(@class,'repCheck')]");
  }

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

  }

  // ================= Constructor =================

  @Getter
  private TransactionsOperationsComponent transactionsOperationsComponent;
  @Getter
  private SystemAdminComponent systemAdminComponent;

  public ImageAndCircularLuggagePage(SHAFT.GUI.WebDriver driver) {
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
  public ImageAndCircularLuggagePage searchForTransaction2(String transactionNumber) {
    driver.element().click(filterIcon);
    driver.element().type(transactionIdInput, transactionNumber);
    driver.element().click(searchButton);
    return this;
  }

  @Step("التحقق من وجود المعاملة في نتائج الحقيبة")
  public ImageAndCircularLuggagePage assertTransactionExists(String transactionNumber) {

    driver.assertThat()
        .element(transactionRow(transactionNumber))
        .exists()
        .perform();

    return this;
  }

  @Step("الضغط على زر الإعداد للمعاملة ")
  public ImageAndCircularLuggagePage clickPrepareButton(String transactionNumber) {
    driver.element().click(prepareButton(transactionNumber));
    return this;
  }

  @Step("إعداد صورة المعاملة لإدارة: {orgUnitName}")
  public ImageAndCircularLuggagePage setupCopyTransaction() {
    driver.element().click(originalCheckbox);
    driver.element().click(copyCheckbox);
    return this;
  }

  public ImageAndCircularLuggagePage selectAction()  {
    driver.element().click(actionInput);
    driver.element().click(actionOption);
    return this;
  }

  public ImageAndCircularLuggagePage selectViewLevel() {
    //String viewLevel
//    if ("للاطلاع".equalsIgnoreCase(viewLevel)) {
//      driver.element().click(viewLevelCheckbox);
//    }
    driver.element().click(viewLevelCheckbox);
    driver.element().click(signCheckbox);
    return this;
  }


  @Step("حفظ إعداد الحقيبة")
  public ImageAndCircularLuggagePage saveSetup() {
    driver.element().click(saveButton);
    driver.element().click(successToast);
    return this;
  }


// ===========================
//       Assertions
//  ===========================


  @Step("التحقق من حفظ الإعداد بنجاح")
  public ImageAndCircularLuggagePage assertSetupSavedSuccessfully() {
    driver.assertThat()
        .element(successMessage)
        .isVisible()
        .perform();
    driver.element().click(closePopup);
    return this;
  }

}
