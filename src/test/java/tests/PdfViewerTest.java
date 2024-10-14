package tests;

import annotations.AnnotationData;
import annotations.AnnotationType;
import api.AnnotationApi;
import com.fasterxml.jackson.databind.JsonNode;
import data.TestDataLoader;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PdfViewerPage;
import pages.ProjectPage;
import pages.ProjectsExplorerPage;
import scripts.ScriptExecutor;

import java.io.IOException;

public class PdfViewerTest extends BaseTest{

    private LoginPage loginPage;
    private PdfViewerPage pdfViewerPage;
    private ProjectsExplorerPage projectsExplorerPage;
    private ProjectPage projectPage;
    private AnnotationApi annotationApi;
    private ScriptExecutor scriptExecutor;
    private TestDataLoader testDataLoader;
    private JsonNode testData;

    @BeforeMethod
    @Parameters("browser")
    private void beforeTestSetup(String browser) throws IOException, InterruptedException {
        super.setup(browser);
        loginPage = new LoginPage(driver);
        pdfViewerPage = new PdfViewerPage(driver);
        projectsExplorerPage = new ProjectsExplorerPage(driver);
        projectPage = new ProjectPage(driver);
        annotationApi = new AnnotationApi();
        scriptExecutor = new ScriptExecutor(driver);
        testDataLoader = new TestDataLoader();
        testData = testDataLoader.loadTestData("src/main/java/data/testData.json");
        AnnotationApi.deleteAllComments(String.valueOf(testData.get("projectId").asText()));
    }

    @AfterMethod
    private void afterTestTeardown(){
        super.tearDown();
    }
    @Test
    public void shouldAdd_HighlightAnnotation_OnPDFViewer() throws Exception {
        var projectName = testData.get("pdfAnnotationsTestData").get("projectName").asText();
        var fileId = testData.get("pdfAnnotationsTestData").get("fileId").asText();
        var zoomPercentage = testData.get("pdfAnnotationsTestData").get("zoomPercentage").asText();
        var highlightAnnotationData = testData.get("pdfAnnotationsTestData").get("annotationType").get("highlight");
        var startXOffset = highlightAnnotationData.get("startXOffset").asInt();
        var startYOffset = highlightAnnotationData.get("startYOffset").asInt();
        var dragDistance = highlightAnnotationData.get("dragDistance").asInt();
        var annotatedText = highlightAnnotationData.get("annotatedText").asText();
        var annotationData = highlightAnnotationData.get("annotationData");
        var annotationX = annotationData.get("x").asDouble();
        var annotationY = annotationData.get("y").asDouble();
        var annotationWidth = annotationData.get("width").asDouble();
        var annotationHeight = annotationData.get("height").asDouble();

        loginPage.loginIntoApplication();
        projectsExplorerPage.selectProject(projectName);
        projectPage.clickOnReviewFile(fileId);
        pdfViewerPage.switchToWindowHandleByUrl(fileId);
        pdfViewerPage.switchToIFrame();
        pdfViewerPage.setZoomPercentage(zoomPercentage);
        pdfViewerPage.setAnnotationType(AnnotationType.HIGHLIGHT, true);
        pdfViewerPage.setAnnotation(startXOffset, startYOffset, dragDistance);
        var newlyAddedAnnotationHtmlId = pdfViewerPage.getNewlyAddedAnnotation();
        var annotationDetails = scriptExecutor.getAnnotationDetails(newlyAddedAnnotationHtmlId);
        Assert.assertEquals(annotationDetails.get("annotType"), AnnotationType.HIGHLIGHT.getValue());
        Assert.assertEquals(annotationDetails.get("textUnderAnnot"), annotatedText);
        AnnotationData actualData = annotationApi.getAnnotationData(annotationDetails.get("annotId").toString());
        AnnotationData expectedData = new AnnotationData(annotationX, annotationY, annotationWidth, annotationHeight);
        Assert.assertEquals(actualData, expectedData);
    }

    @Test
    public void shouldAdd_UnderlineAnnotation_OnPDFViewer() throws Exception {
        var projectName = testData.get("pdfAnnotationsTestData").get("projectName").asText();
        var fileId = testData.get("pdfAnnotationsTestData").get("fileId").asText();
        var zoomPercentage = testData.get("pdfAnnotationsTestData").get("zoomPercentage").asText();
        var underlineAnnotationData = testData.get("pdfAnnotationsTestData").get("annotationType").get("underline");
        var startXOffset = underlineAnnotationData.get("startXOffset").asInt();
        var startYOffset = underlineAnnotationData.get("startYOffset").asInt();
        var dragDistance = underlineAnnotationData.get("dragDistance").asInt();
        var annotatedText = underlineAnnotationData.get("annotatedText").asText();
        var annotationData = underlineAnnotationData.get("annotationData");
        var annotationX = annotationData.get("x").asDouble();
        var annotationY = annotationData.get("y").asDouble();
        var annotationWidth = annotationData.get("width").asDouble();
        var annotationHeight = annotationData.get("height").asDouble();

        loginPage.loginIntoApplication();
        projectsExplorerPage.selectProject(projectName);
        projectPage.clickOnReviewFile(fileId);
        pdfViewerPage.switchToWindowHandleByUrl(fileId);
        pdfViewerPage.switchToIFrame();
        pdfViewerPage.setZoomPercentage(zoomPercentage);
        pdfViewerPage.setAnnotationType(AnnotationType.UNDERLINE, true);
        pdfViewerPage.setAnnotation(startXOffset, startYOffset, dragDistance);
        var newlyAddedAnnotationHtmlId = pdfViewerPage.getNewlyAddedAnnotation();
        var annotationDetails = scriptExecutor.getAnnotationDetails(newlyAddedAnnotationHtmlId);
        Assert.assertEquals(annotationDetails.get("annotType"), AnnotationType.UNDERLINE.getValue());
        Assert.assertEquals(annotationDetails.get("textUnderAnnot"), annotatedText);
        AnnotationData actualData = annotationApi.getAnnotationData(annotationDetails.get("annotId").toString());
        AnnotationData expectedData = new AnnotationData(annotationX, annotationY, annotationWidth, annotationHeight);
        Assert.assertEquals(actualData, expectedData);
    }

    @Test
    public void shouldAdd_SquigglyAnnotation_OnPDFViewer() throws Exception {
        var projectName = testData.get("pdfAnnotationsTestData").get("projectName").asText();
        var fileId = testData.get("pdfAnnotationsTestData").get("fileId").asText();
        var zoomPercentage = testData.get("pdfAnnotationsTestData").get("zoomPercentage").asText();
        var squigglyAnnotationData = testData.get("pdfAnnotationsTestData").get("annotationType").get("squiggly");
        var startXOffset = squigglyAnnotationData.get("startXOffset").asInt();
        var startYOffset = squigglyAnnotationData.get("startYOffset").asInt();
        var dragDistance = squigglyAnnotationData.get("dragDistance").asInt();
        var annotatedText = squigglyAnnotationData.get("annotatedText").asText();
        var annotationData = squigglyAnnotationData.get("annotationData");
        var annotationX = annotationData.get("x").asDouble();
        var annotationY = annotationData.get("y").asDouble();
        var annotationWidth = annotationData.get("width").asDouble();
        var annotationHeight = annotationData.get("height").asDouble();

        loginPage.loginIntoApplication();
        projectsExplorerPage.selectProject(projectName);
        projectPage.clickOnReviewFile(fileId);
        pdfViewerPage.switchToWindowHandleByUrl(fileId);
        pdfViewerPage.switchToIFrame();
        pdfViewerPage.setZoomPercentage(zoomPercentage);
        pdfViewerPage.setAnnotationType(AnnotationType.SQUIGGLY, true);
        pdfViewerPage.setAnnotation(startXOffset, startYOffset, dragDistance);
        var newlyAddedAnnotationHtmlId = pdfViewerPage.getNewlyAddedAnnotation();
        var annotationDetails = scriptExecutor.getAnnotationDetails(newlyAddedAnnotationHtmlId);
        Assert.assertEquals(annotationDetails.get("annotType"), AnnotationType.SQUIGGLY.getValue());
        Assert.assertEquals(annotationDetails.get("textUnderAnnot"), annotatedText);
        AnnotationData actualData = annotationApi.getAnnotationData(annotationDetails.get("annotId").toString());
        AnnotationData expectedData = new AnnotationData(annotationX, annotationY, annotationWidth, annotationHeight);
        Assert.assertEquals(actualData, expectedData);
    }

    @Test
    public void shouldAdd_StrikeoutAnnotation_OnPDFViewer() throws Exception {
        var projectName = testData.get("pdfAnnotationsTestData").get("projectName").asText();
        var fileId = testData.get("pdfAnnotationsTestData").get("fileId").asText();
        var zoomPercentage = testData.get("pdfAnnotationsTestData").get("zoomPercentage").asText();
        var strikeoutAnnotationData = testData.get("pdfAnnotationsTestData").get("annotationType").get("strikeout");
        var startXOffset = strikeoutAnnotationData.get("startXOffset").asInt();
        var startYOffset = strikeoutAnnotationData.get("startYOffset").asInt();
        var dragDistance = strikeoutAnnotationData.get("dragDistance").asInt();
        var annotatedText = strikeoutAnnotationData.get("annotatedText").asText();
        var annotationData = strikeoutAnnotationData.get("annotationData");
        var annotationX = annotationData.get("x").asDouble();
        var annotationY = annotationData.get("y").asDouble();
        var annotationWidth = annotationData.get("width").asDouble();
        var annotationHeight = annotationData.get("height").asDouble();

        loginPage.loginIntoApplication();
        projectsExplorerPage.selectProject(projectName);
        projectPage.clickOnReviewFile(fileId);
        pdfViewerPage.switchToWindowHandleByUrl(fileId);
        pdfViewerPage.switchToIFrame();
        pdfViewerPage.setZoomPercentage(zoomPercentage);
        pdfViewerPage.setAnnotationType(AnnotationType.STRIKEOUT, true);
        pdfViewerPage.setAnnotation(startXOffset, startYOffset, dragDistance);
        var newlyAddedAnnotationHtmlId = pdfViewerPage.getNewlyAddedAnnotation();
        var annotationDetails = scriptExecutor.getAnnotationDetails(newlyAddedAnnotationHtmlId);
        Assert.assertEquals(annotationDetails.get("annotType"), AnnotationType.STRIKEOUT.getValue());
        Assert.assertEquals(annotationDetails.get("textUnderAnnot"), annotatedText);
        AnnotationData actualData = annotationApi.getAnnotationData(annotationDetails.get("annotId").toString());
        AnnotationData expectedData = new AnnotationData(annotationX, annotationY, annotationWidth, annotationHeight);
        Assert.assertEquals(actualData, expectedData);
    }
}
