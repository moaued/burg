package pages.unifiedNumber;

import com.shaft.driver.SHAFT.GUI.WebDriver;
import components.HorizontalMenusComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.TransactionsCopiesPage;

public class UnifiedNumberDetailsPage extends HorizontalMenusComponent {

  WebDriver driver;

  private By imageCheckBoxForOrgUnit(String orgUnitName) {
    return By.xpath(String.format(
        "(//div[label[normalize-space()='%s']]/following::div[@class='checkbox']/label/span[@class='cr'])[1]",
        orgUnitName));
  }

  private By transactionCopyAttachmentOverlay = By.cssSelector(".jconfirm.white");
  //First one after the original copy of the transaction
  private By firstCopyAttachmentCheckBox = By.xpath("(//input[contains(@class,' inp')])[2]");
  private By saveAttachmentButton = By.id("btnShowCopiesAttDialog");
  private By sendCopyButton = By.xpath("//a[@onclick='SendAssignmentPaper()']");

  public UnifiedNumberDetailsPage(WebDriver driver) {
    super(driver);
    this.driver = driver;
  }

  @Step("اختيار ادارة للارسال من خلال ورقة الاحالة")
  public UnifiedNumberDetailsPage selectOrgUnitForTransactionCopy(String orgUnitName) {
    By orgUnitCheckBox = imageCheckBoxForOrgUnit(orgUnitName);
    driver.element().scrollToElement(orgUnitCheckBox).click(orgUnitCheckBox)
        .waitUntilNumberOfElementsToBe(transactionCopyAttachmentOverlay, 1);
    driver.element().click(firstCopyAttachmentCheckBox).click(saveAttachmentButton)
        .waitUntilNumberOfElementsToBeLessThan(transactionCopyAttachmentOverlay,1);
    return this;
  }

  @Step("ارسال نسخة المعاملة")
  public TransactionsCopiesPage sendTransactionCopy() {
    driver.element().scrollToElement(sendCopyButton)
        .click(sendCopyButton)
        .verifyThat(sendCopyButton).doesNotExist();
    return new TransactionsCopiesPage(driver);
  }

}
