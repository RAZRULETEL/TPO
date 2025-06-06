import QuestionPage.Companion.url
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions


class SpacesPage(driver: WebDriver): Page(driver, url) {
    private val spacesTitle = "//h1[contains(@class, '_Title_level1_1s6d0_1')]"
    private val spaces = "//div[contains(@class, '_Space_otkvn_1')]"

    private val spaceLogo = ".//div[1]/div[1]"
    private val hoverCard = "//div[contains(@class, '_Popper_iw652_1')]"
    private val hoverCardQuestionsCount = ".//div[1]/div[2]/span[1]/div/div[1]"
    private val hoverCardUsersCount = ".//div[1]/div[2]/span[1]/div/div[2]"

    fun isPageLoaded(): Boolean {
        waitLonger.until() { driver.findElementOrNull(By.ByXPath(spacesTitle))?.isDisplayed ?: false }
        return driver.findElementOrNull(By.ByXPath(spacesTitle))?.isDisplayed ?: false
    }

    fun hoverFirstSpace(): Boolean {
        waitLonger.until() { ExpectedConditions.visibilityOfElementLocated(By.ByXPath(spaces)) }
        val actions = Actions(driver)
        actions.moveToElement(driver.findElementOrNull(By.ByXPath(spaces))!!).perform()

        return driver.findElementOrNull(By.ByXPath(spaces))?.isDisplayed ?: false
    }

    fun isHoverCardShowed(): Boolean {
        waitLonger.until() { driver.findElementOrNull(By.ByXPath(hoverCard))?.isDisplayed ?: false }
        return driver.findElementOrNull(By.ByXPath(hoverCard))?.isDisplayed ?: false
    }

    fun hoverCardGetQuestionsCount(): Int? {
        return if (isHoverCardShowed())
            driver.findElementOrNull(By.ByXPath(hoverCard))?.findElementOrNull(By.ByXPath(hoverCardQuestionsCount))?.text?.toInt()
        else
            null
    }

    fun hoverCardGetUsersCount(): Int? {
        return if (isHoverCardShowed())
            driver.findElementOrNull(By.ByXPath(hoverCard))?.findElementOrNull(By.ByXPath(hoverCardUsersCount))?.text?.toInt()
        else
            null
    }

    companion object {
        val url = "https://otvet.mail.ru/spaces"
    }
}