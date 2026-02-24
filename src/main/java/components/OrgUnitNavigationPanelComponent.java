package components;

import com.shaft.driver.SHAFT;
import com.shaft.driver.SHAFT.GUI.WebDriver;
import lombok.Getter;

public class OrgUnitNavigationPanelComponent {

  protected SHAFT.GUI.WebDriver driver;
  @Getter
  protected HorizontalMenusComponent hMComponent;

  public OrgUnitNavigationPanelComponent(WebDriver driver, HorizontalMenusComponent hMComponent) {
    this.driver = driver;
    this.hMComponent = hMComponent;
  }
}
