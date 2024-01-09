package InterViewR.services.aiResponses;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AiResponseServiceImpl implements AiResponseService{
    private static final String OPENAI_API_ENDPOINT = "https://api.openai.com/v1/engines/davinci-codex/completions";
    private static final String OPENAI_API_KEY = "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"; // Replace with your actual API key

    public String getChatGptResponse(String message) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            // Set up the request headers
            Map<Object, Object> data = new HashMap<>();
            data.put("prompt", message);
            data.put("max_tokens", 100);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OPENAI_API_ENDPOINT))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + OPENAI_API_KEY)
                    .POST(buildJsonFromMap(data))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the request was successful
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.err.println("Error: " + response.statusCode() + " - " + response.body());
                return "Error in processing the request";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error in processing the request";
        }
    }

    private HttpRequest.BodyPublisher buildJsonFromMap(Map<Object, Object> data) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            builder.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\",");
        }
        builder.setCharAt(builder.length() - 1, '}');
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    private String getProfileRequest(List<String> keywords){
        StringBuilder profile = new StringBuilder("You are a person's profile based on keywords generator, "
                + "for a job interview simulation.\nThe response format must have:\n"
                + "Introduction: name, provenience, age, studies\nSocial Characteristics: 4 to 7\n"
                + "Skills and Capabilities: 3 to 5\nBackground Information: work experience: can vary between "
                + "zero experience(only finished studies) and multiple previous jobs, extra curricular "
                + "implication (can exist or not)\nPositive Traits: this field must not be too long\nAreas for Improvement:\n\n"
                + "You will receive some of the keywords about that persons background information/abilities/characteristics."
                + "The keywords given will not always cover the necessities presented before, you will need to complete the profile with your choices."
                + "When generating information about the person, dont make all of them positive, please generate, at random"
                + ", positive and negative features about this person.\nBased on those keywords, you will need to generate a"
                + "profile, with a summary about this person's characteristics, abilities and background information and experience"
                + "\nThe keywords for this story are: ");

        for (int i = 0; i < keywords.size(); i++) {
            profile.append(keywords.get(i));
            if (i < keywords.size() - 1) {
                profile.append(", ");
            }
        }

        return profile.toString();
    }

    public String getGptResponse(List<String> keywords){
        String GPTRequest = getProfileRequest(keywords);
        return getChatGptResponse(GPTRequest);
    }
}
