package Magento;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CheckoutProccessTest {

	String url = "https://magento.softwaretestingboard.com/";

	WebDriver driver = new ChromeDriver();

	@BeforeTest
	public void beforeTest() {
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
	}

	@Test(priority = 1, description = "Open Product and Choose Color and Size and Add To Cart")
	public void openProductAndAddToCart() {

		// Choose Random Product
		WebElement productItemsContainer = driver.findElement(By.className("product-items"));
		List<WebElement> productItems = productItemsContainer.findElements(By.className("product-item"));

		Random random = new Random();

		int randomNumber = random.nextInt(productItems.size() - 2);

		productItems.get(randomNumber).click();

		// Check If Product Contain Color And Size Or Not
		if (driver.findElements(By.cssSelector("div[class='swatch-attribute size'] div[role='listbox']")).size() != 0) {

			WebElement sizeContainer = driver
					.findElement(By.cssSelector("div[class='swatch-attribute size'] div[role='listbox']"));
			List<WebElement> sizeList = sizeContainer.findElements(By.tagName("div"));

			WebElement colorContainer = driver
					.findElement(By.cssSelector("div[class='swatch-attribute color'] div[role='listbox']"));
			List<WebElement> colorList = colorContainer.findElements(By.tagName("div"));

			// Choose Random Color And Size
			int randomSize = random.nextInt(sizeList.size());
			int randomColor = random.nextInt(colorList.size());

			sizeList.get(randomSize).click();
			colorList.get(randomColor).click();

			WebElement addCartButton = driver.findElement(By.id("product-addtocart-button"));
			addCartButton.click();
		} else {
			WebElement addCartButton = driver.findElement(By.id("product-addtocart-button"));
			addCartButton.click();
		}
	}

	@Test(priority = 2, description = "Chechout Process...")
	public void checkoutProccess() throws InterruptedException {
		Thread.sleep(2000);
		String checkoutPageURL = "https://magento.softwaretestingboard.com/checkout/cart/";
		driver.get(checkoutPageURL);
		
		Random random = new Random();

		WebElement prooceedButton = driver.findElement(By.cssSelector("button[data-role='proceed-to-checkout']"));
		prooceedButton.click();

		Thread.sleep(2000);
		WebElement email = driver.findElement(By.id("customer-email"));
		WebElement firstName = driver.findElement(By.name("firstname"));
		WebElement lastName = driver.findElement(By.name("lastname"));
		WebElement street = driver.findElement(By.name("street[0]"));
		WebElement city = driver.findElement(By.name("city"));
		WebElement postalCode = driver.findElement(By.name("postcode"));
		WebElement country = driver.findElement(By.name("country_id"));
		WebElement phoneNumber = driver.findElement(By.name("telephone"));

		Thread.sleep(2000);
		
	    int randomEmailNumber = random.nextInt(999);

		email.sendKeys("omar".concat(String.valueOf(randomEmailNumber)).concat("@gmail.com"));
		firstName.sendKeys("omar");
		lastName.sendKeys("alsayyed");
		street.sendKeys("street one");
		city.sendKeys("zarqa");
		postalCode.sendKeys("12345");
		phoneNumber.sendKeys("07234325325");

		List<WebElement> countryList = country.findElements(By.tagName("option"));
		System.out.println("country count : " + countryList.size());

		int randomCountry = random.nextInt(1, countryList.size());

		Select countrySelect = new Select(country);
		countrySelect.selectByIndex(randomCountry);

		Thread.sleep(2000);

		WebElement stateContainer = driver
				.findElement(By.cssSelector("div[name='shippingAddress.region_id'] div[class='control']"));
		WebElement stateSelect = stateContainer.findElement(By.tagName("select"));
		List<WebElement> stateOptions = stateSelect.findElements(By.tagName("option"));

		// Check If Country Need Choose State Or Not
		if (stateOptions.size() > 1) {

			WebElement stateContainerOne = driver
					.findElement(By.cssSelector("div[name='shippingAddress.region_id'] div[class='control']"));
			WebElement stateOptionsContainer = stateContainerOne.findElement(By.className("select"));
			List<WebElement> optionsList = stateOptionsContainer.findElements(By.tagName("option"));

			System.out.println("options count : " + optionsList.size());
			System.out.println("options EXIST");

			int randomStateOption = random.nextInt(1, optionsList.size());
			Select selectState = new Select(stateOptionsContainer);
			selectState.selectByIndex(randomStateOption);

		} else {

			System.out.println("options NOT EXIST");

			WebElement stateContainerTwo = driver
					.findElement(By.cssSelector("div[name='shippingAddress.region'] div[class='control']"));
			WebElement stateInput = stateContainerTwo.findElement(By.tagName("input"));
			stateInput.sendKeys("Amman");
		}

		// Shipping Method Select Randomly
		Thread.sleep(5000);
		WebElement shippingMethod = driver.findElement(By.tagName("tbody"));
		List<WebElement> shippingMethodList = shippingMethod.findElements(By.tagName("tr"));

		int randomShippingMethod = random.nextInt(shippingMethodList.size());
		shippingMethodList.get(randomShippingMethod).click();
		System.out.println("shipping method : " + shippingMethodList.size());
		
		//Click On Next Button To Complete Checkout Process
		WebElement nextButton = driver.findElement(By.cssSelector(".button.action.continue.primary"));
		nextButton.click();
		
		Thread.sleep(4000);
		WebElement placeOrderButton = driver.findElement(By.cssSelector(".action.primary.checkout"));
		placeOrderButton.click();
	}
}
