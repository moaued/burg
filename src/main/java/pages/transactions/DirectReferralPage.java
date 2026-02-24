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
}
