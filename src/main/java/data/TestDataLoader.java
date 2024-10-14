package data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class TestDataLoader {

    public JsonNode loadTestData(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(new File(filePath));
    }
}
