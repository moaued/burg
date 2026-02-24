package pages.transactions;

import com.shaft.driver.SHAFT;
import components.HorizontalMenusComponent;
import components.SystemAdminComponent;
import components.TransactionsNavigationPanelComponent;
import components.TransactionsOperationsComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;

public class TransactionsCopiesPage extends TransactionsNavigationPanelComponent {

  private By transactionCopyCard = By.cssSelector(".box-grid");
  private By firtTransactionCopyCard = By.xpath(
      "(//div[contains(@class,'box-grid')]//div[contains(@class,'pr-1') and contains(@id,'Number_')])[1]");
  private By firstDisplayDetailsCopyButton = By.xpath("(//a[@id='link'])[1]");
  private By numberOfAttachmentsAtFirstCard = By.xpath("(//a[@id='link' and contains(@class,'color-gray')]/preceding-sibling::span)[1]");


  @Getter
  private TransactionsOperationsComponent transactionsOperationsComponent;
  @Getter
  private SystemAdminComponent systemAdminComponent;

  public TransactionsCopiesPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
    transactionsOperationsComponent = new TransactionsOperationsComponent(driver);
    systemAdminComponent = new SystemAdminComponent(driver);
  }

  @Step("الحصول على رقم نسخة المعاملة الأولى")
  public String getFirstTransactionNumber() {
    return driver.element().getText(firtTransactionCopyCard);
  }

  @Step("عرض تفاصيل صورة المعاملة")
  public TransactionCopiesDetailsPage goToTransactionCopyDetails() {
    driver.element().scrollToElement(firstDisplayDetailsCopyButton);
    driver.element().click(firstDisplayDetailsCopyButton);
    return new TransactionCopiesDetailsPage(driver);
  }

  public int getNumberOfAttachmentOnFirstCard() {
    String attachmentsCount = driver.element().getText(numberOfAttachmentsAtFirstCard);
    return Integer.parseInt(attachmentsCount.trim());
  }

}
