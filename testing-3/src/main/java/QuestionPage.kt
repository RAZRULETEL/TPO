import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.ExpectedConditions.and
import org.openqa.selenium.support.ui.ExpectedConditions.attributeContains
import org.openqa.selenium.support.ui.ExpectedConditions.not
import org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements
import org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy
import org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated
import java.util.Arrays


class QuestionPage(driver: WebDriver, id: Int) : Page(driver, url + id.toString()) {

    private val logo = "//img[@alt=\"Ответы\"]"
    private val questionTitle = "//div[@id=\"$id\"]/div[2]/h1"
    private val toggleAnswers = "//div[@id=\"$id\"]/div[4]/div[1]/div[1]/button"
    private val toggleAnswersCounter = "//div[@id=\"$id\"]/div[4]/div[1]/div[1]/button/div/span[2]"

    private val answersBlock = "//div[@id=\"replies$id\"]"

    private val showMoreButton =
        "//div[@id=\"replies$id\"]/div/div[contains(@class, \"_ShowMoreButton_depth1_\")]/button"
    private val answers =
        "//div[@id=\"replies$id\"]/div/div[contains(@class, \"_Card_modeOutline_\")]"

    private val dateAnswersSort =
        "//div[@id=\"replies$id\"]/div/div[contains(@class, \"_Row_\")][1]/div[1]"
    private val popularityAnswersSort =
        "//div[@id=\"replies$id\"]/div/div[contains(@class, \"_Row_\")][1]/div[2]"
    private val sortButtonActiveClass = "_Button_active_"
    private val sortButtonDisabledClass = "_Button_disabled_"

    private val answerVotesCount = ".//span[contains(@class, \"_transition_vvo23_5\")]"
    private val answerDate = ".//span[contains(@class, \"_text_1o2qj_1\")]"
    private val deletedAnswer = ".//img[@alt=\"Удаленный ответ\"]"

    fun isLogoVisible(): Boolean {
        waitLonger.until() { ExpectedConditions.visibilityOfElementLocated(By.ByXPath(logo)) }
        return driver.findElementOrNull(By.ByXPath(logo))?.isDisplayed ?: false
    }

    fun isPageLoaded(): Boolean {
        waitLonger.until() { ExpectedConditions.visibilityOfElementLocated(By.ByXPath(questionTitle)) }
        return (driver.findElementOrNull(By.ByXPath(questionTitle))?.isDisplayed
            ?: false) && isLogoVisible()
    }

    fun getQuestionTitle(): String? {
        wait.until() { ExpectedConditions.visibilityOfElementLocated(By.ByXPath(questionTitle)) }
        return driver.findElementOrNull(By.ByXPath(questionTitle))?.text
    }

    fun toggleAnswers(): Boolean {
        return clickByXpath(toggleAnswers)
    }

    fun isAnswersShowed(): Boolean {
        val answers = driver.findElementOrNull(By.ByXPath(answersBlock))
        if (answers == null || !answers.isDisplayed) return false
        return true
    }

    fun getTotalAnswersCount(): Int? {
        wait.until() { ExpectedConditions.visibilityOfElementLocated(By.ByXPath(toggleAnswersCounter)) }
        return driver.findElementOrNull(By.ByXPath(toggleAnswersCounter))?.text?.trim()?.toInt()
    }

    fun getVisibleAnswersCount(waitingCount: Int = 1): Int {
        wait.until() { driver.findElements(By.ByXPath(answers)).size > (waitingCount - 1) }
        return driver.findElements(By.ByXPath(answers)).size
    }

    fun clickShowMoreButton(): Boolean {
        return clickByXpath(showMoreButton)
    }

    fun clickDateAnswersSort(): Boolean {
        return clickByXpath(dateAnswersSort)
    }

    fun clickPopularityAnswersSort(): Boolean {
        return clickByXpath(popularityAnswersSort)
    }

    fun isDateSortButtonActive(): Boolean {
        wait.until() { ExpectedConditions.visibilityOfElementLocated(By.ByXPath(dateAnswersSort)) }
        return driver.findElementOrNull(By.ByXPath(dateAnswersSort))?.getDomAttribute("class")
            ?.contains(sortButtonActiveClass) ?: false
    }

    fun isPopularitySortButtonActive(): Boolean {
        wait.until() {
            CustomExpectedCondition({
                val popSort = driver.findElementOrNull(By.ByXPath(popularityAnswersSort))
                popSort?.getDomAttribute("class")?.contains(sortButtonActiveClass) ?: false
            }).apply { driver }
        }
        return driver.findElementOrNull(By.ByXPath(popularityAnswersSort))?.getDomAttribute("class")
            ?.contains(sortButtonActiveClass) ?: false
    }

    fun awaitSortingComplete() {
        waitLonger.until() {
            ExpectedConditions.and(
                visibilityOfElementLocated(By.ByXPath(popularityAnswersSort)),
                not(
                    attributeContains(
                        By.ByXPath(popularityAnswersSort),
                        "class",
                        sortButtonDisabledClass
                    )
                ),
                CustomExpectedCondition({
                    val popSort = driver.findElementOrNull(By.ByXPath(popularityAnswersSort))
                    !(popSort?.getDomAttribute("class")?.contains(sortButtonDisabledClass) ?: true)
                }).apply { driver }
            )
        }
    }

    fun isAnswersSortedByDate(reverse: Boolean = false): Boolean {
        try {
            waitLonger.until() {
                CustomExpectedCondition({
                    try {
                        val answers = driver.findElements(By.ByXPath(answers))
                        println(Arrays.toString(answers.map {
                            it.findElementOrNull(
                                By.ByXPath(
                                    answerDate
                                )
                            )?.text ?: ""
                        }.toTypedArray()))
                        println(Arrays.toString(answers.map {
                            it.findElementOrNull(
                                By.ByXPath(
                                    deletedAnswer
                                )
                            ) == null
                        }.toTypedArray()))
                        println(
                            Arrays.toString(
                                answers
                                    .filter { it.findElementOrNull(By.ByXPath(deletedAnswer)) == null }
                                    .map {
                                        it.findElementOrNull(By.ByXPath(answerDate))?.text ?: "-1"
                                    }.toTypedArray()
                            )
                        )
                        val dates = answers
                            .filter { it.findElementOrNull(By.ByXPath(deletedAnswer)) == null }
                            .map { it.findElementOrNull(By.ByXPath(answerDate))?.text ?: "-1" }
                            .map {
                                (it.replace(
                                    "[^0-9]".toRegex(), ""
                                )).toInt() * timeScales.getOrDefault(
                                    it.replace(
                                        "[0-9]".toRegex(), ""
                                    ), 0
                                )
                            }
                            .toTypedArray()
                        println(Arrays.toString(dates))

                        return@CustomExpectedCondition dates.isSorted(reverse)
                    } catch (err: StaleElementReferenceException) {
                        return@CustomExpectedCondition false
                    }
                }).apply(driver)
            }
            return true
        } catch (ex: TimeoutException) {
            return false
        }
    }

    fun isAnswersSortedByPopularity(reverse: Boolean = false): Boolean {
        try {
            waitLonger.until() {
                CustomExpectedCondition({
                    try {
                        val answers = driver.findElements(By.ByXPath(answers))
                        var dates: List<String>? = null
                        try {
                            dates = answers
                                .filter { it.findElementOrNull(By.ByXPath(deletedAnswer)) == null }
                                .map { it.findElement(By.ByXPath(answerVotesCount)).text }
                        } catch (err: Exception) {
                            return@CustomExpectedCondition false
                        }

                        println(
                            Arrays.toString(dates.toTypedArray())
                        )

                        val stamps = dates
                            .map {
                                (it.replace(
                                    "[^0-9]".toRegex(), ""
                                )).toInt() * timeScales.getOrDefault(
                                    it.replace(
                                        "[0-9]".toRegex(), ""
                                    ), 0
                                )
                            }
                            .toTypedArray()
                        println(Arrays.toString(stamps))
                        println()

                        return@CustomExpectedCondition stamps.isSorted(reverse)
                    } catch (err: StaleElementReferenceException) {
                        return@CustomExpectedCondition false
                    }
                }).apply(driver)
            }
            return true
        } catch (ex: TimeoutException) {
            return false
        }
    }

    companion object {
        var url = "https://otvet.mail.ru/question/"

        val timeScales = mapOf(
            "только что" to 0,
            "мин" to 1,
            "ч" to 60,
            "д" to 60 * 24,
            "мес" to 60 * 24 * 30,
            "г" to 60 * 24 * 30 * 12
        )
    }
}

private fun Array<Int>.isSorted(reverse: Boolean = false): Boolean {
    for (i in 0 until size - 1) {
        if ((if (reverse) 1 else -1) * (this[i] - this[i + 1]) > 0) return false
    }
    return true
}
