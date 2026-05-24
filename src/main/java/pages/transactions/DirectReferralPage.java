package pages.transactions;

import com.shaft.driver.SHAFT;
import components.HorizontalMenusComponent;
import components.TransactionsNavigationPanelComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import utils.ElementsOperations;

public class DirectReferralPage extends TransactionsNavigationPanelComponent {

  private By departmentNumber = By.xpath("//input[contains(@class,'txtDepartmentNumber')]");
  private By orgUnitAutoCompleteMenu = By.cssSelector("#divAutoComplateMenu");
  private By firstOrgChartAutoSuggestion = By.cssSelector(
      "#divAutoComplateMenu div:nth-of-type(1)");
  private By referralAction = By.id("ddlEditorAssginmentIndividualActionId");
  private By referralDeliveryMethod = By.id("ddlDeliveryMethod");
  private By referralSendButton = By.id("GetUserDelegationsById");
  private By referralSaveConfirmationModal = By.xpath(
      "//div[contains(@class, 'jconfirm') and contains(@class, 'white')]");
  private By confirmAddReferral = By.xpath("//div[contains(@class,'actions-buttons')]/button[1]");
  private By referralFollowUpCheckBox = By.id("followUpCheck");

  private By printDeliveryStatementCheckbox =
      By.cssSelector("#printDeliveryReports.form-check-input");
  private By referralEmployee = By.cssSelector("#ddlEditorAssginmentIndividualToUserId.form-control");




  public DirectReferralPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
  }

  @Step("اضافة طلب احالة مباشرة مع المتابعة")
  public MyTransactionsPage addNewReferral(String orgUnitNumber, String actionType,
      String deliveryMethod) {
    driver.element().click(departmentNumber).clear(departmentNumber)
        .type(departmentNumber, orgUnitNumber)
        .waitUntil(ElementsOperations.waitForElementToBeReady(orgUnitAutoCompleteMenu))
        .click(firstOrgChartAutoSuggestion);
    driver.element().select(referralAction, actionType)
        .select(referralDeliveryMethod, deliveryMethod);
    driver.element().click(referralFollowUpCheckBox);
    driver.element().click(referralSendButton)
        .waitUntil(ElementsOperations.waitForElementToBeReady(referralSaveConfirmationModal))
        .click(confirmAddReferral)
        .verifyThat(referralSaveConfirmationModal).doesNotExist();
    return new MyTransactionsPage(driver);
  }
  @Step("اضافة طلب احالة مباشرة مع المتابعة وبيان التسليم")
  public MyTransactionsPage addNewReferral2(String orgUnitNumber, String actionType,
      String deliveryMethod) {
    driver.element().click(departmentNumber).clear(departmentNumber)
        .type(departmentNumber, orgUnitNumber)
        .waitUntil(ElementsOperations.waitForElementToBeReady(orgUnitAutoCompleteMenu))
        .click(firstOrgChartAutoSuggestion);
    driver.element().select(referralAction, actionType)
        .select(referralDeliveryMethod, deliveryMethod);
    driver.element().click(referralFollowUpCheckBox);
    driver.element().click(printDeliveryStatementCheckbox);
    driver.element().click(referralSendButton)
        .waitUntil(ElementsOperations.waitForElementToBeReady(referralSaveConfirmationModal))
        .click(confirmAddReferral)
        .verifyThat(referralSaveConfirmationModal).doesNotExist();
    return new MyTransactionsPage(driver);
  }


  @Step("اضافة طلب احالة مباشرة مع المتابعة مع اختيار الموظف المرسل له الإحالة")
  public MyTransactionsPage addNewReferral3(String orgUnitNumber, String employeeName,
      String actionType, String deliveryMethod) {
    driver.element().click(departmentNumber).clear(departmentNumber)
        .type(departmentNumber, orgUnitNumber)
        .waitUntil(ElementsOperations.waitForElementToBeReady(orgUnitAutoCompleteMenu))
        .click(firstOrgChartAutoSuggestion);
    driver.element().select(referralEmployee, employeeName);

    driver.element().select(referralAction, actionType)
        .select(referralDeliveryMethod, deliveryMethod);
    driver.element().click(referralFollowUpCheckBox);
    driver.element().click(referralSendButton)
        .waitUntil(ElementsOperations.waitForElementToBeReady(referralSaveConfirmationModal))
        .click(confirmAddReferral)
        .verifyThat(referralSaveConfirmationModal).doesNotExist();
    return new MyTransactionsPage(driver);
  }
}
