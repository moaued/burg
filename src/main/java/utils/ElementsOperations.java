package utils;

import com.shaft.driver.SHAFT.GUI.WebDriver;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ElementsOperations {

  public static boolean confirmValueExistenceInTable(String searchString, By tableLocator, WebDriver driver) {
    driver.element().waitUntilPresenceOfAllElementsLocatedBy(tableLocator);
    List<Map<String, String>> tableRowsData = driver.element().getTableRowsData(tableLocator);
    //driver.browser().captureScreenshot();
    driver.element().captureScreenshot(tableLocator);
    return tableRowsData.stream()
        .flatMap(map -> map.values().stream())
        .anyMatch(searchString::equals);
  }


  public static ExpectedCondition<Boolean> waitForElementToBeReady(By locator) {
    return driver -> {
      try {
        assert driver != null;
        WebElement element = driver.findElement(locator);
        return element.isDisplayed() && element.isEnabled() && element.getSize().getHeight() > 0
            && element.getSize().getWidth() > 0;
      } catch (Exception e) {
        return false;
      }
    };
  }
  public static ExpectedCondition<Boolean> waitForElementToBePresentAndHasSize(By locator) {
    return driver -> {
      try {
        if (driver == null) return false;

        WebElement element = driver.findElement(locator);

        return element.getRect().getHeight() > 0
            && element.getRect().getWidth() > 0;
      } catch (Exception e) {
        return false;
      }
    };
  }


}
