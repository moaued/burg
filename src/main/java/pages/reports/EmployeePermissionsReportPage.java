package pages.reports;

import com.shaft.driver.SHAFT;
import components.HorizontalMenusComponent;
import components.ReportsNavigationPanelComponent;
import io.qameta.allure.Step;
import java.io.File;
import org.openqa.selenium.By;

public class EmployeePermissionsReportPage extends ReportsNavigationPanelComponent {

  private By reportGrid = By.id("grid-table-GridTransactionReport");
//      By.xpath("//table//thead"); // عدلها حسب الجدول الفعلي

  private By btnExportExcel = By.xpath("//button[contains(text(),' تصدير Excel')]");


  public EmployeePermissionsReportPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
  }

  public boolean isResultsGridDisplayed() {
    return driver.element().isElementDisplayed(reportGrid);
  }

  @Step(" تصدير Excel في تقرير صلاحيات الموظفين")
  public EmployeePermissionsReportPage clickExportExcel() {
    driver.element().click(btnExportExcel);
    return this;
  }
  @Step("التحقق من  تصدير ملفات الExcel في تقرير صلاحيات الموظفين")
  public boolean waitUntilFileDownloaded(String extension) {

    File downloadFolder =
        new File(System.getProperty("user.home") + "/Downloads");

    long timeout = System.currentTimeMillis() + 30000;

    while (System.currentTimeMillis() < timeout) {

      File[] files = downloadFolder.listFiles(
          file -> file.getName().toLowerCase().endsWith(extension)
      );

      if (files != null && files.length > 0) {
        return true;
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        return false;
      }
    }

    return false;
  }


}
