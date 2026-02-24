package pages.transactions;

import com.shaft.driver.SHAFT;
import com.shaft.driver.SHAFT.GUI.WebDriver;
import components.HorizontalMenusComponent;
import components.TransactionsNavigationPanelComponent;
import components.TransactionsOperationsComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;


public class SentTransactionsPage extends TransactionsNavigationPanelComponent{

  private By revertButton = By.cssSelector("a[data-type='Revert']");

  @Getter
  private TransactionsOperationsComponent transactionsOperationsComponent;

  public SentTransactionsPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
    transactionsOperationsComponent = new TransactionsOperationsComponent(driver);

  }

  @Step("سحب المعاملة المرسلة ")
  public SentTransactionsPage withdrawTransaction() {
    driver.element().click(revertButton);
    return this;
  }


}
