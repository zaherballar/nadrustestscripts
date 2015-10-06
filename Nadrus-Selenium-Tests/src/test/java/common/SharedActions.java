package common;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.webdriven.commands.WaitForPopup;

public class SharedActions {
	private WebDriver wd;
	
	public static final int doLogin_HOME=10;
	public static final int doLogin_HOME_FB=11;
	public static final int doLogin_DROPDOWN = 20;
	public static final int doRegister_DROPDOWN = 20;
	public static final int doLogin_DROPDOWN_FB=21;
	public static final int doLogin_LOGINPAGE=30;
	public static final int doLogin_LOGINPAGE_FB=31;
	
	public SharedActions(WebDriver wd) {
		this.wd = wd;
	}
	
	public void doLogin(String username,String password, int method){
				
			if(method==doLogin_HOME){
				wd.get(Config.startURL+"/theme/1");
				WebElement myDynamicElement = 
					(new WebDriverWait(wd, Config.TIMEOUT)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body[class*='pace-done']")));
		        wd.findElement(By.xpath("//header[@id='hero']//a[.='دخول']")).click();
		        
				(new WebDriverWait(wd, Config.TIMEOUT)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='login']")));
		        wd.findElement(By.cssSelector("#UserLoginForm > div.form-group > div.input-group > #UserUsername")).click();
		        wd.findElement(By.cssSelector("#UserLoginForm > div.form-group > div.input-group > #UserUsername")).clear();
		        wd.findElement(By.cssSelector("#UserLoginForm > div.form-group > div.input-group > #UserUsername")).sendKeys(username);
		        wd.findElement(By.cssSelector("#UserLoginForm > div.form-group > div.input-group > #UserPassword")).click();
		        wd.findElement(By.cssSelector("#UserLoginForm > div.form-group > div.input-group > #UserPassword")).clear();
		        wd.findElement(By.cssSelector("#UserLoginForm > div.form-group > div.input-group > #UserPassword")).sendKeys(password);
		        wd.findElement(By.xpath("//form[@id='UserLoginForm']/input")).click();
 
			}
 
		
	}
	
	public String doRegisterOnFly(int method){
		if(method==doRegister_DROPDOWN){
	        String newUsername ="Talib"+System.currentTimeMillis();
	        wd.findElement(By.id("UserdetailFirstName")).click();
	        wd.findElement(By.id("UserdetailFirstName")).clear();
	        wd.findElement(By.id("UserdetailFirstName")).sendKeys(newUsername);
	        wd.findElement(By.id("UserUsername")).click();
	        wd.findElement(By.id("UserUsername")).clear();
	        wd.findElement(By.id("UserUsername")).sendKeys(newUsername + "@freeletter.com");
	        wd.findElement(By.id("UserPassword")).click();
	        wd.findElement(By.id("UserPassword")).clear();
	        wd.findElement(By.id("UserPassword")).sendKeys("123456");
	        wd.findElement(By.xpath("//form[@id='UserSignupForm']/input[2]")).click();
	        wd.findElement(By.id("skip")).click();
	        return newUsername + "@freeletter.com";
		}
		return null;
	}
}
