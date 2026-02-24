package pages.transactions;

import com.shaft.driver.SHAFT;
import components.HorizontalMenusComponent;
import components.TransactionsNavigationPanelComponent;
import io.qameta.allure.Step;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import utils.ElementsOperations;

public class TransactionCopiesDetailsPage extends TransactionsNavigationPanelComponent {

  private By tableOfAttchments = By.xpath("(//table[@id='grid-table-CopiesgrdArchiving'])[1]");
  private By transactionNumber = By
      .xpath(
          "(//div[@class='panel-body'][1]//div[@class='col-sm-4']//fieldset/div//div[@class='BoldText'])[1]");

  public TransactionCopiesDetailsPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
  }

  @Step("التأكد من وجود جدول الملحقات")
  public boolean confirmAttachmentsTableIsNotEmpty() {
    List<Map<String, String>> tableRowsData = driver.element().getTableRowsData(tableOfAttchments);
    return !(tableRowsData.getFirst().isEmpty());
  }

  @Step("التأكد من وجود قيمة معينة داخل جدول الملحقات")
  public boolean confirmExistenceOfSpecificContentInTableOfCopies(String attachmentType) {
    return ElementsOperations.confirmValueExistenceInTable(attachmentType, tableOfAttchments, driver);
  }

  @Step("الحصول على رقم المعاملة من صفحة النسخة الداخلية")
  public String getTransactionNumber() {
    return driver.element().getText(transactionNumber);
  }
}
