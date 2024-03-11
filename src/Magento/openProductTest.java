package Magento;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class openProductTest {

	String url = "https://magento.softwaretestingboard.com/";

	WebDriver driver = new ChromeDriver();

	@BeforeTest

	public void beforeTest() {
		driver.manage().window().maximize();
	}

	@Test(invocationCount = 3)

	public void openProduct() throws InterruptedException {

		driver.get(url);
		Thread.sleep(2000);

		WebElement productItemsContainer = driver.findElement(By.className("product-items"));
		List<WebElement> productItems = productItemsContainer.findElements(By.className("product-item"));

		System.out.println(productItems.size());

		Random random = new Random();

		int randomNumber = random.nextInt(productItems.size());

		WebElement item = productItems.get(randomNumber);

		if (item.findElements(By.className("color")).size() != 0) {
			System.out.println("COLOR EXIST");
			System.out.println(randomNumber);

			// Size List Declaration
			Thread.sleep(2000);
			WebElement sizeContainer = item.findElement(By.className("size"));
			WebElement sizeContainerChild = sizeContainer.findElement(By.className("swatch-attribute-options"));
			List<WebElement> sizeList = sizeContainerChild.findElements(By.className("swatch-option"));

			// Color List Declaration
			WebElement colorContainer = item.findElement(By.className("color"));
			WebElement colorContainerChild = colorContainer.findElement(By.className("swatch-attribute-options"));
			List<WebElement> colorList = colorContainerChild.findElements(By.className("swatch-option"));

			Thread.sleep(1000);
			int randomSize = random.nextInt(sizeList.size());
			sizeList.get(randomSize).click();

			Thread.sleep(1000);
			int randomColor = random.nextInt(colorList.size());
			colorList.get(randomColor).click();

			Thread.sleep(1000);
			WebElement addToCartButton = item.findElement(By.tagName("Button"));
			addToCartButton.click();
		} else {
			System.out.println("COLOR NOT EXIST");
			System.out.println(randomNumber);

			// Mouse hover On Item
			Actions action = new Actions(driver);
			action.moveToElement(item).perform();

			Thread.sleep(1000);
			WebElement addToCartButton = item.findElement(By.tagName("Button"));
			addToCartButton.click();
		}

	}

}
