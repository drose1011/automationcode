import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys
import org.openqa.selenium.internal.FindsByXPath
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.text.DecimalFormat;

WebUI.openBrowser('') //Open the browser)

WebUI.maximizeWindow() //Maximize the browser to its full size)

WebUI.navigateToUrl('https://shopify.fello.com/') //Navigate to the URL

DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy")
LocalDateTime now = LocalDateTime.now()
startDate = dtf.format(now)
dayOfWeek = Date.parse("MM/dd/yyy", startDate).format("EEE")
println("This is not the weekend")

if(dayOfWeek == "Mon" || dayOfWeek == "Tue" || dayOfWeek == "Wed" || dayOfWeek == "Thu" || dayOfWeek == "Fri") {
	def weekDate = new Date().plus(1)
	startDate = weekDate.format("MM/dd/yyy")
	if(dayOfWeek == "Sun") {
		def sunDate = new Date().plus(1)
		startDate = sunDate.format("MM/dd/yyy")
	}
	if(dayOfWeek == "Sat") {
		def satDate = new Date().plus(2)
		startDate = satDate.format("MM/dd/yyy")
	}
}

WebUI.focus(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/startDate'))

WebUI.executeJavaScript('$(\':input\').removeAttr(\'readonly\')', [])

WebUI.delay(1)

WebUI.setText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/startDate'), startDate)

WebUI.sendKeys(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/startDate'), Keys.chord(Keys.ENTER))