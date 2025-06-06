package driver

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions

object DriverFactory {
    val driverChromeOptions =
        ChromeOptions()
            .addArguments("--start-maximized")
            .addArguments("--disable-notifications")
            .addArguments("--headless")
    val driverFirefoxOptions = FirefoxOptions()
        .addArguments("--start-maximized")
        .addArguments("--disable-notifications")
        .addArguments("--headless")

    fun getAllDrivers(): List<WebDriver> = listOf(ChromeDriver(driverChromeOptions), FirefoxDriver(driverFirefoxOptions))
    fun tearDown(drivers: List<WebDriver>) = drivers.forEach { it.quit() }
}