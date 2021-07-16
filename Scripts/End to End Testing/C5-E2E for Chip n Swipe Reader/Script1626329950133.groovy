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
import org.openqa.selenium.Keys as Keys
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.text.DecimalFormat;

WebUI.openBrowser('') //Open the browser)

WebUI.maximizeWindow() //Maximize the browser to its full size)

WebUI.navigateToUrl('https://shopify.fello.com/') //Navigate to the URL

//Logic to dynamically select a start date that is not the weekend date
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

// Start of Home Page
//Enter Start Date
WebUI.focus(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/startDate'))

WebUI.executeJavaScript('$(\':input\').removeAttr(\'readonly\')', [])

WebUI.delay(1)

WebUI.setText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/startDate'), startDate)

WebUI.sendKeys(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/startDate'), Keys.chord(Keys.ENTER))

itemName = WebUI.getText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/home_item5name')) //Get the item's name

int setItemAmount = 10 //Set up item amount to be processed later

int totalItemAmt = setItemAmount	//Add all the items

String selectedItems = '(' + totalItemAmt + ')'

WebUI.selectOptionByValue(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/home_selectitem5amount'), 
    '10', true) //Select item as 5

WebUI.verifyElementClickable(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/home_additem5tocart'))	//Verify if the Add to cart is clickable

WebUI.click(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/home_additem5tocart'))	//Click the Add to cart button

cartItem = WebUI.getText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/home_cartItemnumber'))

//Verify if the item number matches with the cart
WebUI.verifyMatch(cartItem, selectedItems, false)

WebUI.verifyElementClickable(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/home_viewcart'))

WebUI.click(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/home_viewcart'))
// End of Home Page

// Start of Carts Page
//Verify if the user gets navigated to the carts page
cartUrl = WebUI.getUrl()

WebUI.verifyMatch('https://shopify.fello.com/cart', cartUrl, false)

cartItem1 = WebUI.getText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/cart_item1name'))

if (cartItem1.contains(itemName)) {
    println('Success')
} else {
    KeywordUtil.markFailedAndStop(itemName + ' is not present')
}

String webActualPrice = WebUI.getText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/cart_item1price')).substring(1)

String itemCartTrim = webActualPrice.replace(",", "")

double setItemPrice = Double.parseDouble(itemCartTrim)

itemActualPrice = setItemPrice * setItemAmount

webExpectedPrice = WebUI.getText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/cart_item1totalprice')).substring(1)

itemExpectedPrice = webExpectedPrice.replace(",", "")

WebUI.verifyEqual(itemActualPrice, itemExpectedPrice)

double itemCartTotal = itemActualPrice

webExpectedCartTotal = WebUI.getText(findTestObject('Object Repository/Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/cart_subtotal')).substring(1)

String itemWebCartTrim = webExpectedCartTotal.replace(",", "")

double itemCartExpected = Double.parseDouble(itemWebCartTrim)

WebUI.verifyEqual(itemCartTotal, itemCartExpected)

WebUI.click(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/cart_checkout'))
// End of Carts Page

//Start of Shipping Page
WebUI.navigateToUrl('https://shopify.fello.com/shipping_address')

shipUrl = WebUI.getUrl()

WebUI.verifyMatch('https://shopify.fello.com/shipping_address', shipUrl, false)

//Verify the item name is the same
shipmentItemName = WebUI.getText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipping_item1name'))

WebUI.verifyMatch(shipmentItemName, cartItem1, false)

//Verify the price for the item is the same
shipmentPrice = WebUI.getText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipment_item1totalprice')).substring(1)

String itemWebShippingTrim = shipmentPrice.replace(",", "")

double shipmentItemPrice = Double.parseDouble(itemWebShippingTrim)

WebUI.verifyEqual(itemActualPrice, shipmentItemPrice)

//Verify that the item number matches
webShipmentItemAmount = WebUI.getText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipping_item1amount'))

WebUI.verifyEqual(setItemAmount, webShipmentItemAmount)

//Verify the shipment subtotal
webShipmentSubTotal = WebUI.getText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipment_subtotal')).substring(1)

String itemWebShippingSubTotalTrim = webShipmentSubTotal.replace(",", "")

double shipmentSubtotalPrice = Double.parseDouble(itemWebShippingSubTotalTrim)

WebUI.verifyEqual(itemCartTotal, shipmentSubtotalPrice)

String emailAddress = 'mshrestha@gorinsystems.com'

String fname = 'Matish'

String lname = 'Shrestha'

WebUI.setText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipment_email'), emailAddress)

WebUI.setText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipping_firstname'), fname)

WebUI.setText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipping_lastname'), lname)

WebUI.setText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipment_address'), '876 Lincoln Pl')

WebUI.setText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipment_cityname'), 'Brooklyn')

WebUI.selectOptionByValue(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipment_state'), 
    'NY', true)

WebUI.setText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipment_zipcode'), '11213')

WebUI.setText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipment_phonenumber'), '0908776468')

WebUI.setText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipment_entercoupon'), 'DEV770')

WebUI.click(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipment_applycoupon'))

WebUI.verifyElementClickable(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipment_continuepayment'))

WebUI.click(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipment_continuepayment'))

//Verify the total
webShipmentTotalPrice = WebUI.getText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/shipment_totalprice')).substring(1)

String itemWebShippingTotalTrim = webShipmentTotalPrice.replace(",", "")

double shipmentItemTotalPrice = Double.parseDouble(itemWebShippingTotalTrim)

WebUI.verifyEqual('0.10', shipmentItemTotalPrice)
//End of Shipping Page

//Start of Payment page
paymentUrl = WebUI.getUrl()

WebUI.verifyMatch('https://shopify.fello.com/payment', paymentUrl, false)

//Verify the email
String email = WebUI.getText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/payment_email'))

WebUI.verifyMatch(email, emailAddress, false)

//Verify that the coupon is present
WebUI.verifyElementPresent(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/payment_coupon'),
	0)

//Verify the item1 total price
String webPaymentItemPrice = WebUI.getText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/payment_item1price')).substring(1)

String itemPaymentItemPriceTrim = webPaymentItemPrice.replace(",", "")

double paymentItemPrice = Double.parseDouble(itemPaymentItemPriceTrim)

WebUI.verifyEqual(shipmentItemPrice, paymentItemPrice)

//Verify the total is 0.10
String webPaymentTotalPrice = WebUI.getText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/payment_totalprice')).substring(1)

String itemPaymentTotalTrim = webPaymentTotalPrice.replace(",", "")

double paymentItemTotalPrice = Double.parseDouble(itemPaymentTotalTrim)

WebUI.verifyEqual(shipmentItemTotalPrice, paymentItemTotalPrice)

//Set the card number
WebUI.setText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/payment_inputcardnumber'),
	'3727 420011 14476')

//Set the name on the card
WebUI.setText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/payment_inputcardholdername'),
	'FELLO DEVELOPERS GORIN')

//Set expiration date
WebUI.setText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/payment_inputcardexpiration'),
	'12/24')

//Set CVV number
WebUI.setText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/payment_inputcvv'),
	'3737')

//Click on the I have read, acknowledged the agreement
WebUI.click(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/payment_clickagreement'))

WebUI.click(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/payment_complete'))
//End of Payment Page

WebUI.delay(10)

//Start of Order Complete Page
completeUrl = WebUI.getUrl()

WebUI.verifyMatch('https://shopify.fello.com/order_completed', completeUrl, false)

WebUI.verifyElementPresent(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/complete_ordernumber'),
	0)

String confirmationMessage = WebUI.getText(findTestObject('Single Item/Page_Shopify Hardware Rentals fulfilled by Fello/complete_confirmationmessage'))

WebUI.verifyMatch(confirmationMessage, 'Your order is confirmed', false)
//End of Order Complete Page

WebUI.closeBrowser()