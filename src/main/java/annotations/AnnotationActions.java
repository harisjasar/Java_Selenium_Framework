package annotations;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AnnotationActions {
    public static void setAnnotation(WebDriver driver, WebElement container, int startXOffset, int startYOffset, int dragDistance) throws InterruptedException {
        String script =
                "var container = arguments[0];" +
                        "var rect = container.getBoundingClientRect();" +
                        "var startX = rect.left + " + startXOffset + ";" +
                        "var startY = rect.top + " + startYOffset + ";" +
                        "var endX = startX + " + dragDistance + ";" +
                        "var endY = startY;" +
                        "container.dispatchEvent(new MouseEvent('mousedown', {clientX: startX, clientY: startY, bubbles: true}));" +
                        "setTimeout(function() {" +
                        "console.log('Dispatching mousemove');" +
                        "container.dispatchEvent(new MouseEvent('mousemove', {clientX: endX, clientY: endY, bubbles: true}));" +
                        "}, 100);" +
                        "setTimeout(function() {" +
                        "container.dispatchEvent(new MouseEvent('mouseup', {clientX: endX, clientY: endY, bubbles: true}));" +
                        "}, 200);";
        ((JavascriptExecutor) driver).executeScript(script, container);
        Thread.sleep(1000);
        container.click();
    }
}