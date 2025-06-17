package webScraper

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.util.logging.Level
import java.util.logging.Logger

object ScraperUtils {
    private fun getChromeDriverPath(): String? {
        val os = System.getProperty("os.name").lowercase()
        return when {
            os.contains("win") -> "chrome-drivers/win/chromedriver.exe"
            os.contains("mac") -> "chrome-drivers/mac/chromedriver"   //Apple silicon
            os.contains("nux") -> "chrome-drivers/linux/chromedriver"
            else -> null
        }
    }
    fun getContent(path : String, delay : Long) : String? {
        val html : String
        Logger.getLogger("org.openqa.selenium").level = Level.SEVERE

        val driverPath = getChromeDriverPath()
        if (driverPath == null){
            println("Error loading drivers (Unsupported OS).")
            return null
        }
        System.setProperty("webdriver.chrome.driver", driverPath)

        val options = ChromeOptions().apply {
            addArguments("--headless=new")
            addArguments("--disable-gpu")
            addArguments("--no-sandbox")
        }

        val driver: WebDriver = ChromeDriver(options)

        try {
            driver.get(path)

            Thread.sleep(delay)

            html = driver.pageSource

        }
        catch (e : Exception){
            println("Error getting data: ${e.message}")
            return null
        }
        finally {
            driver.quit()
        }
        return html
    }

}