import driver.DriverFactory
import driver.DriverFactory.driverChromeOptions
import driver.DriverFactory.driverFirefoxOptions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import java.lang.Integer.min
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


class MyTest {

    val questionId = 267080546
    val questionTitle =
        "Когда дирекция Mail удалит неработающий сайт ответов и освободит от работы творцов демо-версии?"
    val questionAnswersAtLeast = 10
    val initialQuestionsCount = 5
    val perLoadQuestionsCount = 15

    val failSearchQuery = "qazxswedcvfrtgbnhyujm,kiol."
    val successSearchQuery = "ЕГЭ"

    @Test
    fun mainPageLoadTest() {
        val drivers = DriverFactory.getAllDrivers()
        drivers.forEach { driver ->
            val main = MainPage(driver)
            main.open()

            assertTrue(main.isLogoVisible())
        }
        DriverFactory.tearDown(drivers)
    }

    @Test
    fun loginDialogCloseTest() {
        val drivers = DriverFactory.getAllDrivers()
        drivers.forEach { driver ->

            val main = MainPage(driver)
            main.open()

            assertTrue(main.clickLoginButton())
            assertTrue(main.isLoginPopupShowed())
            assertTrue(main.closeLoginPopup())
            assertTrue(main.isLoginPopupClosed())
        }
        DriverFactory.tearDown(drivers)
    }

    @Test
    fun trendingTabTest() {
        val drivers = DriverFactory.getAllDrivers()
        drivers.forEach { driver ->

            val main = MainPage(driver)
            main.open()

            assertTrue(main.clickTrendingTab())
            assertTrue(main.isTrendingTabSelected())
        }
        DriverFactory.tearDown(drivers)
    }

    @Test
    fun debatedTabTest() {
        val drivers = DriverFactory.getAllDrivers()
        drivers.forEach { driver ->

            val main = MainPage(driver)
            main.open()

            assertTrue(main.clickDebatedTab())
            assertTrue(main.isDebatedTabSelected())
        }
        DriverFactory.tearDown(drivers)
    }

    @Test
    fun searchFailedTest() {
        val drivers = DriverFactory.getAllDrivers()
        drivers.forEach { driver ->
            val searchQuery = failSearchQuery

            val main = MainPage(driver)
            main.open()

            assertTrue(main.typeSearchQuery(searchQuery))
            assertTrue(main.clickSearchButton())
            assertTrue(main.isSearchFailedCorrectly(searchQuery))
        }
        DriverFactory.tearDown(drivers)
    }

    @Test
    fun searchSuccessTest() {
        val drivers = DriverFactory.getAllDrivers()
        drivers.forEach { driver ->
            val searchQuery = successSearchQuery

            val main = MainPage(driver)
            main.open()

            assertTrue(main.typeSearchQuery(searchQuery))
            assertTrue(main.clickSearchButton())
            assertTrue(main.isSearchSuccess(searchQuery))
        }
        DriverFactory.tearDown(drivers)
    }

    @Test
    fun questionPageLoadTest() {
        val drivers = DriverFactory.getAllDrivers()
        drivers.forEach { driver ->

            val quest = QuestionPage(driver, questionId)
            quest.open()

            assertTrue(quest.isPageLoaded())
        }
        DriverFactory.tearDown(drivers)
    }

    @Test
    fun questionPageQuestionTitleTest() {
        val drivers = DriverFactory.getAllDrivers()
        drivers.forEach { driver ->

            val quest = QuestionPage(driver, questionId)
            quest.open()

            assertEquals(quest.getQuestionTitle(), questionTitle)
        }
        DriverFactory.tearDown(drivers)
    }

    @Test
    @Disabled
    fun questionPageAnswersToggleTest() {
        val drivers = DriverFactory.getAllDrivers()
        drivers.forEach { driver ->

            val quest = QuestionPage(driver, questionId)
            quest.open()

            assertTrue(quest.isAnswersShowed())
            assertTrue(quest.toggleAnswers())
            assertFalse(quest.isAnswersShowed())
            assertTrue(quest.toggleAnswers())
            assertTrue(quest.isAnswersShowed())
            assertTrue(quest.toggleAnswers())
            assertFalse(quest.isAnswersShowed())
        }
        DriverFactory.tearDown(drivers)
    }

    @Test
    fun questionPageShowMoreAnswersTest() {
        val drivers = DriverFactory.getAllDrivers()
        drivers.forEach { driver ->

            val quest = QuestionPage(driver, questionId)
            quest.open()

            assertTrue(questionAnswersAtLeast > 5, "Question must have at least 6 answers")

            val totalAnswersCount = quest.getTotalAnswersCount()
            assertNotNull(totalAnswersCount)
            assertTrue(totalAnswersCount >= questionAnswersAtLeast)
            assertTrue(totalAnswersCount > quest.getVisibleAnswersCount())
            assertTrue(quest.clickShowMoreButton())
            assertEquals(
                min(totalAnswersCount, initialQuestionsCount + perLoadQuestionsCount),
                quest.getVisibleAnswersCount(questionAnswersAtLeast)
            )
        }
        DriverFactory.tearDown(drivers)
    }

    @Test
    fun questionPagePopularitySortTest() {
        val drivers = DriverFactory.getAllDrivers()
        drivers.forEach { driver ->
            val quest = QuestionPage(driver, questionId)
            quest.open()

            assertTrue(quest.isPopularitySortButtonActive())
            assertTrue(quest.clickShowMoreButton())
            quest.getVisibleAnswersCount(questionAnswersAtLeast)
            quest.awaitSortingComplete()
            assertTrue(quest.clickPopularityAnswersSort())
            assertTrue(quest.isPopularitySortButtonActive())
            quest.awaitSortingComplete()
            assertTrue(quest.isAnswersSortedByPopularity(true))

            assertTrue(quest.clickPopularityAnswersSort())
            assertTrue(quest.isPopularitySortButtonActive())
            quest.awaitSortingComplete()
            assertTrue(quest.isAnswersSortedByPopularity(false))
        }
        DriverFactory.tearDown(drivers)
    }

    @Test
    fun questionPageDateSortTest() {
        val drivers = DriverFactory.getAllDrivers()
        drivers.forEach { driver ->
            val quest = QuestionPage(driver, questionId)
            quest.open()

            assertTrue(quest.isPopularitySortButtonActive())
            assertTrue(quest.clickShowMoreButton())
            quest.getVisibleAnswersCount(questionAnswersAtLeast)
            quest.awaitSortingComplete()
            assertTrue(quest.clickDateAnswersSort())
            assertTrue(quest.isDateSortButtonActive())
            quest.awaitSortingComplete()
            assertTrue(quest.isAnswersSortedByDate(false))

            assertTrue(quest.clickDateAnswersSort())
            assertTrue(quest.isDateSortButtonActive())
            quest.awaitSortingComplete()
            assertTrue(quest.isAnswersSortedByDate(true))
        }
        DriverFactory.tearDown(drivers)
    }

    @Test
    fun spacesPageLoadTest() {
        val drivers = DriverFactory.getAllDrivers()
        drivers.forEach { driver ->
            val spaces = SpacesPage(driver)
            spaces.open()

            assertTrue(spaces.isPageLoaded())
        }
        DriverFactory.tearDown(drivers)
    }

    @Test
    fun hoverCardTest() {
        val drivers = DriverFactory.getAllDrivers()
        drivers.forEach { driver ->
            val spaces = SpacesPage(driver)
            spaces.open()

            assertTrue(spaces.isPageLoaded())
            spaces.hoverFirstSpace()
            assertTrue(spaces.isHoverCardShowed())
        }
        DriverFactory.tearDown(drivers)
    }

    @Test
    fun spacesHoverCardBrokenCountersTest() {
        val drivers = DriverFactory.getAllDrivers()
        drivers.forEach { driver ->
            val spaces = SpacesPage(driver)
            spaces.open()

            assertTrue(spaces.isPageLoaded())
            spaces.hoverFirstSpace()
            assertTrue(spaces.isHoverCardShowed())

            val users = spaces.hoverCardGetUsersCount()
            val questions = spaces.hoverCardGetQuestionsCount()

            assertEquals(users, 0)
            assertEquals(questions, 0)
        }
        DriverFactory.tearDown(drivers)
    }
}