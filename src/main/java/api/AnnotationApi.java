package api;

import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import annotations.AnnotationData;
import data.TestDataLoader;

import java.io.IOException;

public class AnnotationApi {

    private JsonNode testData;
    private static String baseApiUrl;
    private static String singleCommentApiUrl;
    private static String commentsApiUrl;
    private static String authToken;

    public AnnotationApi() throws IOException {
        testData = new TestDataLoader().loadTestData("src/main/java/data/testData.json");
        baseApiUrl = testData.get("baseApiUrl").asText();
        singleCommentApiUrl = baseApiUrl +  "issues/comments/{commentId}";
        commentsApiUrl = baseApiUrl + "issues/comments";
        authToken = testData.get("authToken").asText();
    }

    public AnnotationData getAnnotationData(String commentId) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String url = singleCommentApiUrl.replace("{commentId}", commentId);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + authToken)
                .addHeader("accept", "application/json")
                .build();

        int maxRetries = 10;
        int retryCount = 0;
        int waitTime = 3000;

        while (retryCount < maxRetries) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(response.body().string());
                    JsonNode pdfPrintDescriptor = jsonNode.get("data").get(0).get("pdfPrintDescriptor");
                    double x = pdfPrintDescriptor.get("x").asDouble();
                    double y = pdfPrintDescriptor.get("y").asDouble();
                    double width = pdfPrintDescriptor.get("width").asDouble();
                    double height = pdfPrintDescriptor.get("height").asDouble();
                    return new AnnotationData(x, y, width, height);
                } else if (response.code() == 404) {
                    retryCount++;
                    System.out.println("Received 404. Retrying... Attempt: " + retryCount);
                    Thread.sleep(waitTime);
                } else {
                    throw new Exception("Unexpected response code: " + response.code());
                }
            } catch (IOException e) {
                throw new Exception("Failed to make the request: " + e.getMessage());
            }
        }
        throw new Exception("Failed to retrieve annotation data after " + maxRetries + " retries.");
    }


    public static void deleteAllComments(String projectId) throws IOException, InterruptedException {
        JsonNode comments = getAllComments(projectId);
        if (comments != null) {
            for (JsonNode comment : comments) {
                String commentId = comment.get("commentId").asText();

                deleteCommentById(commentId);
            }
        } else {
            System.out.println("No comments found.");
        }
    }

    public static JsonNode getAllComments(String projectId) throws IOException, InterruptedException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(commentsApiUrl) {
                @Override
                public String getMethod() {
                    return "GET";
                }
            };

            request.setHeader("Authorization", "Bearer " + authToken);
            request.setHeader("Content-Type", "application/json");

            String jsonBody = String.format("{\"projectid\": \"%s\"}", projectId);
            request.setEntity(new StringEntity(jsonBody));

            try (CloseableHttpResponse response = client.execute(request)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(responseBody.toString());
                return jsonNode.get("data");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static void deleteCommentById(String commentId) throws IOException {
        String deleteUrl = singleCommentApiUrl.replace("{commentId}", commentId);

        Request request = new Request.Builder()
                .url(deleteUrl)
                .delete()
                .addHeader("Authorization", "Bearer " + authToken)
                .addHeader("accept", "application/json")
                .build();

        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to delete comment with ID " + commentId + ": " + response);
            }
            System.out.println("Successfully deleted comment with ID: " + commentId);
        }
    }
}
