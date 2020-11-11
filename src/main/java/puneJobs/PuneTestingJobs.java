package puneJobs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		boolean siteStatus = false;
		
		
		WebDriver driver = new ChromeDriver();
		driver.get("https://jobs4utech.in/");
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		
		try 
		{
			WebElement ele = driver.findElement(By.xpath("//span[text()='No internet']"));
			System.out.println("Internet issue. . . . . -:( Status ---> "+ele.isDisplayed());
			driver.quit();
					
		} 
		catch (Exception e) 
		{
				//WebElement ele =	driver.findElement(By.xpath("//span[text()='This site can’t be reached']"));

				System.out.println("Running my code. . . .");
				
				Thread.sleep(2000);
				driver.findElement(By.xpath("//a[@href='#citiesdrpdwn']")).click();
				
				Thread.sleep(2000);
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
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-YYYY hh:mm:ss a");
				Date date = new Date();
				String currentDate = dateFormat.format(date);
				System.out.println(currentDate);
				
				
				FileUtils.writeStringToFile(destinationFile, "#Date: "+currentDate+ "\n\n#Total Testing Jobs in Pune = "+listSize+"\n"+completeData);
				
				ProcessBuilder	pb = new ProcessBuilder("notepad.exe", "..\\Jobs4U_New\\OutputFile\\PuneJobsData.txt");
				pb.start();	

			}
			
		//driver.quit();

	}

}
