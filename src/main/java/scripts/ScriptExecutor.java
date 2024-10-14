package scripts;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class ScriptExecutor {

    private WebDriver driver;

    public ScriptExecutor(WebDriver driver){
        this.driver = driver;
    }

    public Map<String, Object> getAnnotationDetails(String annotationHtmlId){
        var js = (JavascriptExecutor) driver;
        String getAnnotationDetailsScript =
                        "var annotationManager = window.documentViewer.getAnnotationManager();" +
                        "var annotation = annotationManager.getAnnotationById('"+ annotationHtmlId + "');" +
                        "if (annotation) {" +
                        "   return {" +
                        "       annotId: annotation.Dj.annotId," +
                        "       textUnderAnnot: annotation.Dj['trn-annot-preview']," +
                        "       annotType: annotation['_xsi:type']" +
                        "   };" +
                        "} else {" +
                        "   return null;" +
                        "}";
        var annotationDetails = js.executeScript(getAnnotationDetailsScript);
        if(annotationDetails != null){
            return (Map<String, Object>) annotationDetails;
        }

        throw new RuntimeException("Unable to retrieve annotation details");
    }
}
