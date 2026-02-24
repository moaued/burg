package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class GeneralOperations {

  @Getter
  private static String absolutePath;


  @Step("Get the current Date/Time")
  public static String getCurrentDateTime(String format) {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    return now.format(formatter);
  }

  @Step("Delete folder at a specified path")
  public static void deleteFolderContents(Path folderPath) {
    try {
      Files.walkFileTree(folderPath, new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          Files.delete(file);
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
          if (!dir.equals(folderPath)) {
            Files.delete(dir);
          }
          return FileVisitResult.CONTINUE;
        }
      });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Step("Create a new folder")
  public static void createNewFolder(String folderRelativePath) {
    log.info("RelativePath: {}", folderRelativePath);
    // Create a File object
    File folder = new File(folderRelativePath);
    // Get the absolute path
    absolutePath = folder.getAbsolutePath() + "/";
    log.info("AbsolutePath: {}", absolutePath);
    // Create the folder
    if (folder.mkdir()) {
      System.out.println("Folder created successfully: " + folderRelativePath);
    } else {
      System.out.println(
          "Failed to create folder. It may already exist or the parent directory doesn't exist.");
    }
  }

  @Step("Create a new folder & name it over the current date/time")
  public static void createNewFolderWithDateTimeName(String propertyName) {
    String parentDirectory = System.getProperty(propertyName);
    System.out.println(parentDirectory);
    if (Files.notExists(Path.of(parentDirectory))) {
      createNewFolder(parentDirectory);
    }
    deleteFolderContents(Path.of(parentDirectory));
    createNewFolder(parentDirectory + getCurrentDateTime("yyyyMMddHHmmss") + "/");
  }

  @Step("Get the file size")
  public static double getFileSize(String filePath) {
    File file = new File(filePath);
    long fileSizeInBytes = file.length();
    long fileSizeInKb = fileSizeInBytes / 1024;
    Allure.step("File Size is: " + fileSizeInKb);
    return fileSizeInKb;
  }

  @Step("Get current Hijri Date")
  public static String getCurrentHijriDate() {
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return HijrahDate.from(currentDate).format(formatter);
  }

  @Step("Get Hijri Date Days Before")
  public static String getHijriDateDaysBefore(int numberOfDays) {
    LocalDate currentDate = LocalDate.now().minusDays(numberOfDays);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return HijrahDate.from(currentDate).format(formatter);
  }

  @Step("Get Hijri Date Days After")
  public static String getHijriDateDaysAfter(int numberOfDays) {
    LocalDate currentDate = LocalDate.now().plusDays(numberOfDays);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return HijrahDate.from(currentDate).format(formatter);
  }

  @Step("Get Hijri Date Week Before")
  public static String getHijriDateWeeksBefore(int numberOfWeeks) {
    LocalDate currentDate = LocalDate.now().minusWeeks(numberOfWeeks);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return HijrahDate.from(currentDate).format(formatter);
  }

  @Step("Get Hijri Date Month Before")
  public static String getHijriDateMonthsBefore(int numberOfMonths) {
    LocalDate currentDate = LocalDate.now().minusMonths(numberOfMonths);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return HijrahDate.from(currentDate).format(formatter);
  }

  public static void main(String[] args) {
    System.out.println(getHijriDateWeeksBefore(1));
    System.out.println(getCurrentHijriDate());
  }

}
