import org.openqa.selenium.By
import org.openqa.selenium.By.ByXPath
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.WebDriverWait
import java.awt.SystemColor.text
import java.time.Duration


abstract class Page(val driver: WebDriver, val url: String) {
    val wait = WebDriverWait(driver, Duration.ofSeconds(10))
    val waitLonger = WebDriverWait(driver, Duration.ofSeconds(30))
    val testWait = FluentWait<WebDriver>(driver)

    fun open(): Page {
        driver.get(url)
        waitLonger.until() { driver.currentUrl == url }
        WebDriverWait(driver, Duration.ofSeconds(60)).until<Boolean> { webDriver: WebDriver ->
            (webDriver as JavascriptExecutor).executeScript(
                "return document.readyState"
            ) == "complete"
        }

        return this
    }

    fun waitPageLoaded(){
        WebDriverWait(driver, Duration.ofSeconds(60)).until<Boolean> { webDriver: WebDriver ->
            (webDriver as JavascriptExecutor).executeScript(
                "return document.readyState"
            ) == "complete"
        }
    }

    fun clickByXpath(element: String): Boolean {
        wait.until() {
            ExpectedConditions.and(
                elementToBeClickable(By.ByXPath(element)),
                visibilityOfElementLocated(By.ByXPath(element)),
            )
        }
        val button = driver.findElementOrNull(By.ByXPath(element))
        if (button == null || !button.isDisplayed) return false

        val scrollElementIntoMiddle =
            ("var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                    + "var elementTop = arguments[0].getBoundingClientRect().top;"
                    + "window.scrollBy(0, elementTop-(viewPortHeight/2));")

        (driver as JavascriptExecutor).executeScript(scrollElementIntoMiddle, button)

        button.click()
        return true
    }

    protected fun WebDriver.findElementOrNull(byXPath: By.ByXPath): WebElement? {
        try {
            return this.findElement(byXPath)
        } catch (e: Exception) {
            return null
        }
    }

    protected fun WebElement.findElementOrNull(byXPath: By.ByXPath): WebElement? {
        try {
            return this.findElement(byXPath)
        } catch (e: Exception) {
            return null
        }
    }

    protected class CustomExpectedCondition(val cond: (Unit) -> Boolean) : ExpectedCondition<WebElement> {
        override fun apply(input: WebDriver?): WebElement? {
            return if (cond.invoke(Unit))
                input?.findElement(By.ByXPath("/html/body"))
            else null
        }
    }
}

