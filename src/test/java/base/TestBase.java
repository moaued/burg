package base;

import com.shaft.driver.DriverFactory;
import com.shaft.driver.SHAFT;
import io.qameta.allure.Step;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.logging.LoggingPreferences;

public class TestBase {

  protected SHAFT.GUI.WebDriver driver;
  protected SHAFT.TestData.JSON testData;

  @Step("فتح منصة براق")
  public void openBuragApp() {
    // 💡 Chrome preferences to disable password manager and unwanted prompts
    Map<String, Object> prefs = new HashMap<>();
    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);
    prefs.put("download.prompt_for_download", false);
    prefs.put("download.extensions_to_open", "");
    prefs.put("plugins.always_open_pdf_externally", true);
    prefs.put("safebrowsing.enabled", true);
    prefs.put("profile.default_content_settings.popups", 0);

    // 🧩 Chrome options
    ChromeOptions options = new ChromeOptions();
    options.setExperimentalOption("prefs", prefs);
    options.addArguments(
        "--disable-extensions",
        "--disable-application-cache",
        "--disk-cache-size=0",
        "--disable-password-generation",
        "--disable-save-password-bubble",
        "--disable-features=PasswordManager,PasswordImport,PasswordLeakDetection,PasswordStrengthIndicator,AutofillServerCommunication",
        "--enable-automation",
        "--disable-blink-features=AutomationControlled",
        "--no-first-run",
        "--no-default-browser-check",
        "--password-store=basic",
        "--disable-autofill-keyboard-accessory-view"
        // "--incognito" // optional: comment out if you want persistent cookies
    );

    // 🧪 Unique temporary user profile to isolate session and disable previous state
    String timestamp = String.valueOf(System.currentTimeMillis());
    File userDir = new File(System.getProperty("java.io.tmpdir"), "chrome_temp_profile_" + timestamp);
    userDir.mkdirs();
    options.addArguments("--user-data-dir=" + userDir.getAbsolutePath());

    // 🛑 Suppress "Chrome is being controlled" banner
    options.setExperimentalOption("useAutomationExtension", false);
    options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

    // 🚀 Start browser and navigate to app URL
    driver = new SHAFT.GUI.WebDriver(DriverFactory.DriverType.CHROME, options);
    driver.browser().navigateToURL(testData.getTestData("appURL"));
  }

  @Step("فتح منصة براق")
//  public void openBuragAppWithCustomCapabilities(String directory) {
//    Map<String, Object> prefs = new HashMap<>();
//    // Download preferences
//    prefs.put("download.default_directory", directory);
//    prefs.put("download.prompt_for_download", false);
//    prefs.put("download.directory_upgrade", true);
//    prefs.put("safebrowsing.enabled", true);
//
//    // Password manager suppression
//    prefs.put("credentials_enable_service", false);
//    prefs.put("profile.password_manager_enabled", false);
//    prefs.put("autofill.profile_enabled", false);
//    prefs.put("autofill.credit_card_enabled", false);
//    prefs.put("autofill.password_enabled", false);
//    prefs.put("password_manager.onboarding.enabled", false);
//
//    ChromeOptions options = new ChromeOptions();
//    options.setExperimentalOption("prefs", prefs);
//
//    // Enhanced arguments for password suppression
//    options.addArguments(
//        "--disable-extensions",
//        "--disable-browser-side-navigation",
//        "--disable-infobars",
//        "--disable-password-manager-reauthentication",
//        "--disable-password-manager-ui",
//        "--disable-save-password-bubble",
//        "--disable-single-click-autofill",
//        "--disable-domain-reliability",
//        "--disable-features=PasswordManager,PasswordImport,PasswordLeakDetection,PasswordStrengthIndicator,AutofillServerCommunication,AutofillShowTypePredictions,AutofillCreditCardAblationExperiment",
//        "--enable-automation",
//        "--disable-blink-features=AutomationControlled",
//        "--no-first-run",
//        "--no-default-browser-check",
//        "--password-store=basic",
//        "--disable-autofill-keyboard-accessory-view",
//        "--disable-popup-blocking",
//        "--disable-notifications",
//        "--disable-web-security",
//        "--allow-running-insecure-content",
//        "--disable-gpu",
//        "--disable-dev-shm-usage",
//        "--remote-allow-origins=*"
//    );
//
//    // Create fresh profile directory
//    String timestamp = String.valueOf(System.currentTimeMillis());
//    File userDir = new File(System.getProperty("java.io.tmpdir"), "chrome_temp_" + timestamp);
//    if (!userDir.exists()) {
//      userDir.mkdirs();
//    }
//    options.addArguments("--user-data-dir=" + userDir.getAbsolutePath());
//
//    // Remove automation indicators
//    options.setExperimentalOption("useAutomationExtension", false);
//    options.setExperimentalOption("excludeSwitches",
//        Arrays.asList("enable-automation", "enable-logging", "disable-component-update"));
//
//    // Launch browser
//    driver = new SHAFT.GUI.WebDriver(DriverFactory.DriverType.CHROME, options);
//    driver.browser().navigateToURL(testData.getTestData("appURL"));
//  }
  public void openBuragAppWithCustomCapabilities(String directory) {
    Map<String, Object> prefs = new HashMap<>();
    prefs.put("download.default_directory", directory);
    prefs.put("download.prompt_for_download", false);
    prefs.put("download.directory_upgrade", true);
    prefs.put("safebrowsing.enabled", true);
    prefs.put("download.extensions_to_open", "");
    prefs.put("profile.default_content_settings.popups", 0);
    prefs.put("plugins.always_open_pdf_externally", true);

    // 🔒 Disable password manager prompts
    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);

    ChromeOptions options = new ChromeOptions();
    options.setExperimentalOption("prefs", prefs);

    // 🧩 Chrome arguments to suppress password-related UI and automation detection
    options.addArguments(
        "--disable-extensions",
        "--disable-application-cache",
        "--disk-cache-size=0",
        "--disable-password-generation",
        "--disable-save-password-bubble",
        "--disable-features=PasswordManager,PasswordImport,PasswordLeakDetection,PasswordStrengthIndicator,AutofillServerCommunication",
        "--enable-automation",
        "--disable-blink-features=AutomationControlled",
        "--no-first-run",
        "--no-default-browser-check",
        "--password-store=basic",
        "--disable-autofill-keyboard-accessory-view",
        "--log-level=3",
        "--ignore-certificate-errors",
        "--silent",
        "--disable-logging",
        "--disable-dev-shm-usage",
        "--disable-browser-side-navigation",
        "--disable-features=AutomationControlled"
    );

    // 🧪 Isolate user profile (temp directory)
    String timestamp = String.valueOf(System.currentTimeMillis());
    File userDir = new File(System.getProperty("java.io.tmpdir"), "chrome_profile_" + timestamp);
    userDir.mkdirs();
    options.addArguments("--user-data-dir=" + userDir.getAbsolutePath());

    // 🛑 Remove "Chrome is being controlled..." message
    options.setExperimentalOption("useAutomationExtension", false);
    options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

    // 🚀 Launch the browser
    driver = new SHAFT.GUI.WebDriver(DriverFactory.DriverType.CHROME, options);
    driver.browser().navigateToURL(testData.getTestData("appURL"));
  }

}
