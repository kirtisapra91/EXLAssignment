package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RouteCalculationLib {

    public WebDriver driver;
    WebDriverWait waitObj;

    @FindBy(xpath=".//input[@name='q']")
    WebElement searchBox;

    @FindBy(xpath=".//button[@id='searchbox-searchbutton']")
    WebElement searchBtn;

    @FindBy(xpath=".//img[@alt='Nearby']")
    WebElement nearbyImg;

    @FindBy(xpath=".//img[@alt='Directions']")
    WebElement directionsImg;

    @FindBy(xpath=".//div[@id='sb_ifc51']/input")
    WebElement fromInput;

    @FindBy(xpath=".//div[@data-tooltip=\"Driving\"]")
    WebElement carElement;

    @FindBy(xpath=".//button[text()='Send directions to your phone']")
    WebElement text;


    public RouteCalculationLib(String browser){
        driver = getDriver(browser);
        PageFactory.initElements(driver, this);
        waitObj = new WebDriverWait(driver, 50);
    }

    public WebDriver getDriver(String browser){
        if(browser.equals("chrome")){
            System.setProperty("webdriver.chrome.driver", "D:\\selenium\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--Disable-notifications");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }else {
            System.setProperty("webdriver.firefox.driver", "D:\\selenium\\geckodriver.exe");
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        return driver;
    }

    public String navigateToApplication(String url){
        driver.get(url);
        String text = driver.getTitle();
        return text;
    }

    public List<String> getCoordinatesOfCity(String city, String text) throws InterruptedException {
        searchBox.sendKeys(city, Keys.ENTER);
        waitObj.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//h1[contains(@class, section-hero-header-title-title) and text()='" + text + "']"))));
        String currentUrl = driver.getCurrentUrl();
        Pattern p = Pattern.compile("@(\\w.*),(-\\w.*),\\w.*\\/data");
        Matcher match = p.matcher(currentUrl);
        List<String> list = new ArrayList<String>();
        if(match.find()){
            Pattern p1 = Pattern.compile("(.*\\d{1,}\\.\\d{1,2})");
            Matcher new_match1 = p1.matcher(match.group(1));
            Matcher new_match2 = p1.matcher(match.group(2));
            if(new_match1.find()) {
                list.add(new_match1.group(1));
            }
            if(new_match2.find()) {
                list.add(new_match2.group(1));
            }
        }
        return list;
    }

    public int searchForDirectionsAndGetListOfRoutes(String from, String title){
        directionsImg.click();
        waitObj.until(ExpectedConditions.visibilityOf(fromInput));
        carElement.click();
        fromInput.sendKeys(from);
        fromInput.sendKeys(Keys.ENTER);
        waitObj.until(ExpectedConditions.titleIs(title));
        List<WebElement> list = driver.findElements(By.xpath(".//div[@class='section-directions-trip-description']"));
        return (list.size());
    }

    public List<String> getRouteDetails(){
        List<String> list= new LinkedList<String>();
        List<WebElement> elements = driver.findElements(By.xpath(".//div[@class='section-directions-trip-description']"));
        for(int i=0; i<elements.size(); i++){
            WebElement titleElement = elements.get(i).findElement(By.xpath(".//h1[1]"));
            String title = titleElement.getText();
            WebElement hoursElement =  elements.get(i).findElement(By.xpath(".//div[contains(@class, 'section-directions-trip-duration delay')]"));
            String hours = hoursElement.getText();
            WebElement distanceElement =  elements.get(i).findElement(By.xpath(".//div[@class='section-directions-trip-distance section-directions-trip-secondary-text']"));
            String distance = distanceElement.getText();
            list.add(title + ", " + distance + ", " + hours);
        }
        return list;
    }

    public void writeToFile(File file) throws IOException {
        FileWriter fos = null;
        BufferedWriter out = null;
        try {
            fos = new FileWriter(file);
            out = new BufferedWriter(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<String> list= new LinkedList<String>();
        list = getRouteDetails();
        Iterator itr = list.iterator();
        while(itr.hasNext()){
            out.write(itr.next() + "\n");
        }
        out.close();
    }

}

