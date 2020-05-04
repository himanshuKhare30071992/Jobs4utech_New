package puneJobs;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class PuneTestingJobs 
{
	
	
	
	public static void main(String[] args) throws Exception
	{
		String completeData ="";
		
		WebDriver driver = new ChromeDriver();
		driver.get("https://jobs4utech.in/");
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		driver.findElement(By.xpath("//a[@href='#citiesdrpdwn']")).click();
		
		driver.findElement(By.xpath("//a[text()='Pune']")).click();
		
		driver.findElement(By.xpath("//a[@class='btn btn-info btn-sm m-1']//i")).click();
		
		WebElement domainFilter  = driver.findElement(By.id("category"));
		domainFilter.click();
		Select sc = new Select(domainFilter);
		sc.selectByValue("software testing");
		
		driver.findElement(By.xpath("//button[@id='applyfilter']")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for(int i=1; i<=100; i++)
		{
			//System.out.println(i+">"+js.executeScript("scroll(0,"+i*1000+")"));
			js.executeScript("scroll(0,"+i*1000+")");
			Thread.sleep(10);
		}

		WebElement jobFound = driver.findElement(By.xpath("//h5[text()='Found ']//span"));
		//String puneJobsCount = jobFound.getText();
		
		System.out.println("--------------------------------------------------------------");
		
		List<WebElement> listOfPune =	driver.findElements(By.xpath("//a[text()='Pune, India']//following::span[@class='pl-1'][1]"));
		int listSize = listOfPune.size();
		System.out.println("Total Software Testing Jobs available for Pune city = "+listSize);	
		
		File destinationFile = new File("../Jobs4U_New/OutputFile/PuneJobsData.txt");
		
		for(int  i=0; i<listSize; i++)
		{
			WebElement ele =	listOfPune.get(i);
			String eleText = ele.getText();
			System.out.println("#"+i+"--->>> "+eleText);
			System.out.println("--------------------------------------------------------------");
			completeData = completeData+"\n"+ eleText;
		}
		
		System.out.println("Temp = "+completeData);
		
		FileUtils.writeStringToFile(destinationFile, "Total Pune Jobs = "+listSize+"\n"+completeData);
		
		ProcessBuilder	pb = new ProcessBuilder("notepad.exe", "..\\Jobs4U_New\\\\OutputFile\\\\PuneJobsData.txt");
		pb.start();	
		
		driver.quit();

	}

}
