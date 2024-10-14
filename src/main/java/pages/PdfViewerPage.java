package pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import annotations.AnnotationType;
import annotations.AnnotationActions;

public class PdfViewerPage extends BasePage {
    public PdfViewerPage(WebDriver driver) {
        super(driver);
    }

    public void setAnnotationType(@NotNull AnnotationType type, boolean state){
        waitForElementToBePresent(By.id("pageWidgetContainer1"));
        switch (type){
            case HIGHLIGHT -> setAnnotationTypeAction("highlightToolGroupButton", state);
            case UNDERLINE -> setAnnotationTypeAction("underlineToolGroupButton", state);
            case SQUIGGLY -> setAnnotationTypeAction("squigglyToolGroupButton", state);
            case STRIKEOUT -> setAnnotationTypeAction("strikeoutToolGroupButton", state);
        }
    }

    private void setAnnotationTypeAction(String toolGroupType, boolean state){
        var highlightAnnotButton = waitForElementToBePresent(By.cssSelector("button[data-element='" + toolGroupType + "']"));
        if(highlightAnnotButton != null){
            var classAttribute = highlightAnnotButton.getAttribute("class");
            if(!classAttribute.contains("active") && state){
                highlightAnnotButton.click();
            }
            if(classAttribute.contains("active") && !state){
                highlightAnnotButton.click();
            }
        }
    }

    public WebElement getIFrame(){
        return waitForElementToBePresent(By.id("webviewer-1"));
    }

    public void switchToIFrame(){
        var iframe = getIFrame();
        driver.switchTo().frame(iframe);
    }

    public void setZoomPercentage(String percentage){
        waitForElementToBePresent(By.className("ToggleZoomOverlay")).click();
        waitForElementToBePresent(By.cssSelector("button[aria-label='" + percentage +"%']")).click();
    }

    public void setAnnotation(int startXOffset, int startYOffset, int dragDistance) throws InterruptedException {
        var container = waitForElementToBePresent(By.id("pageWidgetContainer1"));
        AnnotationActions.setAnnotation(driver, container, startXOffset, startYOffset, dragDistance);
    }

    public String getNewlyAddedAnnotation(){
        var newlyAddedAnnotationCard = waitForElementToBePresent(By.cssSelector("div[data-element='notesPanel'] .Note"));
        if(newlyAddedAnnotationCard != null){
            return newlyAddedAnnotationCard.getAttribute("id").split("note_")[1];
        }
        throw new RuntimeException("Annotation card not found");
    }
}
