import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBe
import org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeMoreThan
import org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated

class MainPage(driver: WebDriver) : Page(driver, url) {
    private val logo = "//img[@alt=\"Ответы\"]"

    private val loginOpenButton =
        "//header/div/div[3]/div[contains(@class, '_actions_1xud1_21')]/button[2]"
    private val loginPopup =
        "//div[contains(@class, \"_ModalPopout_p105a_1\")]//div[contains(@class, '_ModalCardInnerContainer_xuxri_45')]"
    private val loginCloseButton =
        "//div[contains(@class, \"_ModalDismissButtonInside_desktop_1t0pk_10\")]"

    private val trendingTabButton = "//a[contains(@class, '_Tab_1vqw1_1')][2]"
    private val debatedTabButton = "//a[contains(@class, '_Tab_1vqw1_1')][3]"

    private val searchBar = "//input[@type=\"search\"]"
    private val searchButton = "//div[contains(@class, \"_SearchField_hguoe_24\")]/button"
    private val searchFailedTitle =
        "//main[contains(@class, \"_main_163ak_1\")]/div/div[2]/div[1]/h1[1]"
    private val searchResults = "//main[contains(@class, \"_main_163ak_1\")]/div/div[3]/div"
    private val searchResultTitle = "//div/span/a/span"

    fun isLogoVisible(): Boolean {
        waitLonger.until() { ExpectedConditions.visibilityOfElementLocated(By.ByXPath(logo)) }
        return driver.findElementOrNull(By.ByXPath(logo))?.isDisplayed ?: false
    }

    fun isLoginPopupShowed(): Boolean {
        var popup: WebElement? = null
        popup = wait.until() { driver.findElement(By.ByXPath(loginPopup)) }
        return popup?.isDisplayed ?: false
    }

    fun closeLoginPopup(): Boolean {
        if (!isLoginPopupShowed()) return false

        val button = driver.findElementOrNull(By.ByXPath(loginCloseButton))
        if (button == null || !button.isDisplayed) return false
        button.click()
        return true
    }

    fun clickLoginButton(): Boolean {
        waitLonger.until() {
            ExpectedConditions.visibilityOfElementLocated(
                By.ByXPath(
                    loginOpenButton
                )
            )
        }
        val button = driver.findElementOrNull(By.ByXPath(loginOpenButton))
        if (button == null || !button.isDisplayed) return false
        button.click()
        return true
    }

    fun isLoginPopupClosed(): Boolean {
        wait.until(ExpectedConditions.numberOfElementsToBe(By.ByXPath(loginPopup), 0))
        return !(driver.findElementOrNull(By.ByXPath(loginPopup))?.isDisplayed ?: false)
    }

    fun clickTrendingTab(): Boolean {
        wait.until() {
            ExpectedConditions.and(
                elementToBeClickable(By.ByXPath(trendingTabButton)),
                visibilityOfElementLocated(By.ByXPath(trendingTabButton)),
            )
        }
        val button: WebElement = driver.findElement(By.ByXPath(trendingTabButton))
        button.click()
        return true
    }

    fun isTrendingTabSelected(): Boolean {
        wait.until() { ExpectedConditions.urlContains("/popular") }
        val button: WebElement = driver.findElement(By.ByXPath(trendingTabButton))
        return driver.currentUrl?.contains("/popular") ?: false && button.getDomAttribute("aria-current")
            ?.equals("page") ?: false
    }

    fun clickDebatedTab(): Boolean {
        wait.until() {
            ExpectedConditions.and(
                elementToBeClickable(By.ByXPath(debatedTabButton)),
                visibilityOfElementLocated(By.ByXPath(debatedTabButton)),
            )
        }
        val button: WebElement = driver.findElement(By.ByXPath(debatedTabButton))
        button.click()
        return true
    }

    fun isDebatedTabSelected(): Boolean {
        wait.until() { ExpectedConditions.urlContains("/debated") }
        val button: WebElement = driver.findElement(By.ByXPath(debatedTabButton))
        return driver.currentUrl?.contains("/debated") ?: false && button.getDomAttribute("aria-current")
            ?.equals("page") ?: false
    }

    fun typeSearchQuery(query: String): Boolean {
        waitLonger.until() { ExpectedConditions.visibilityOfElementLocated(By.ByXPath(searchBar)) }
        val search: WebElement? = driver.findElementOrNull(By.ByXPath(searchBar))
        if (search == null || !search.isDisplayed) return false
        search.sendKeys(query)
        return true
    }

    fun clickSearchButton(): Boolean {
        wait.until() { ExpectedConditions.visibilityOfElementLocated(By.ByXPath(searchButton)) }
        val button: WebElement? = driver.findElementOrNull(By.ByXPath(searchButton))
        if (button == null || !button.isDisplayed) return false
        button.click()
        return true
    }

    fun isSearchFailedCorrectly(query: String): Boolean {
        waitLonger.until() {
            driver.findElementOrNull(By.ByXPath(searchFailedTitle)) != null
        }
        val search: WebElement? = driver.findElementOrNull(By.ByXPath(searchFailedTitle))
        if (search == null || !search.isDisplayed) return false
        return driver.currentUrl?.contains("/search/relevant?query=" + query) ?: false && search.text.contains(
            "Ничего не найдено"
        )
    }

    fun isSearchSuccess(query: String): Boolean {
        waitLonger.until() { numberOfElementsToBeMoreThan(By.ByXPath(searchResults), 3) }
        val search: List<WebElement> = driver.findElements(By.ByXPath(searchResults))
        var containQuery = true
        for (element in search) {
            containQuery =
                containQuery && element.findElementOrNull(By.ByXPath(searchResultTitle))?.text?.lowercase()
                    ?.contains(query.lowercase()) ?: true
        }
        return (driver.currentUrl?.contains("/search/relevant?query=") ?: false) && containQuery
    }

    companion object {
        private val url = "https://otvet.mail.ru/"
    }
}
