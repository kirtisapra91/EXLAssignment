import Common.ReadProperty;
import Pages.RouteCalculationLib;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestClass {
    ReadProperty readObj = new ReadProperty();
    RouteCalculationLib obj = new RouteCalculationLib(readObj.getPropertyValue("data.properties", "chromeBrowser"));
    public List<String> coordinates;
    public int routesCount;
    public List<String> listOfRoutes;
    public String xCoordnate = readObj.getPropertyValue("data.properties", "xCoordnate");
    public String yCoordinate = readObj.getPropertyValue("data.properties", "yCoordinate");
    public String text = readObj.getPropertyValue("data.properties", "textToBeVerified");
    public String firstCity= readObj.getPropertyValue("data.properties", "firstCity");
    public String secondCity= readObj.getPropertyValue("data.properties", "secondCity");
    public String chicagoSearchText = readObj.getPropertyValue("data.properties", "chicagoSearchText");
    File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"+File.separator+"java" + File.separator + "routes.txt");
    SoftAssert softAssertion = new SoftAssert();

    @Test(priority = 1)
    public void verifyNavigationToApplication(){
        String status = obj.navigateToApplication(readObj.getPropertyValue("data.properties", "url"));
        Assert.assertEquals(status, readObj.getPropertyValue("data.properties", "googleMapsTitle"));
    }

    @Test(priority = 2)
    public void verifyCoorinatesOfCity() throws InterruptedException {
        coordinates = obj.getCoordinatesOfCity(firstCity, text);
        softAssertion.assertEquals(coordinates.get(0), xCoordnate);
        softAssertion.assertEquals(coordinates.get(1), yCoordinate);
        softAssertion.assertAll();
    }

    @Test (priority = 3)
    public void verifyCountOfRoutes(){
        routesCount = obj.searchForDirectionsAndGetListOfRoutes(secondCity, chicagoSearchText);
        Assert.assertTrue(routesCount >= 2, routesCount + " is found");
    }

    @Test(priority = 4)
    public void verifyDetailsAreWrittenInTextFile() throws IOException {
        obj.writeToFile(file);
        listOfRoutes = obj.getRouteDetails();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        List<String> listOfLines = new ArrayList<String>();
        while((line = br.readLine()) != null){
            listOfLines.add(line);
        }
        Assert.assertTrue(listOfRoutes.equals(listOfLines), "Routes Are Not Written To File");
    }

//    @AfterClass
//    public void tearDown(){
//        obj.driver.close();
//        obj.driver.quit();
//    }

}
