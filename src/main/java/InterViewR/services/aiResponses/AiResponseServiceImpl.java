package InterViewR.services.aiResponses;

import InterViewR.data.aiConversation.ChatRepository;
import InterViewR.data.security.UserRepository;
import InterViewR.domain.aiConversation.Chat;
import InterViewR.domain.aiConversation.Message;
import InterViewR.requests.SendMessageRequest;
import InterViewR.responses.SendMessageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AiResponseServiceImpl implements AiResponseService{
    private static final String OPENAI_API_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    @Value("${ai.key}")
    private String OPENAI_API_KEY; // Replace with your actual API key
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

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

    private String getProfilePrompt(List<String> keywords){
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

    public String getGptGeneratedProfile(List<String> keywords){
        String GPTRequest = getProfilePrompt(keywords);
        return getChatGptResponse(GPTRequest);
    }

    public String extractGptResonse(String jsonResponse)
    {
        JsonElement jsonElement = JsonParser.parseString(jsonResponse);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return jsonObject
                .getAsJsonArray("choices").get(0)
                .getAsJsonObject().getAsJsonObject("message")
                .get("content").getAsString();
    }


    public String getChatGptResponse(List<Map<String, String>> conversationHistory) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            // Set up the request headers
            Map<String, Object> data = new HashMap<>();
            data.put("model", "gpt-3.5-turbo-0613");
            data.put("messages", conversationHistory);
            data.put("max_tokens", 100);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OPENAI_API_ENDPOINT))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + OPENAI_API_KEY)
                    .POST(buildJsonFromObject(data))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the request was successful
            if (response.statusCode() == 200) {
                return extractGptResonse(response.body());
            } else {
                System.err.println("Error: " + response.statusCode() + " - " + response.body());
                return "Error in processing the request";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error in processing the request";
        }
    }

    private HttpRequest.BodyPublisher buildJsonFromObject(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(object);
            return HttpRequest.BodyPublishers.ofString(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return HttpRequest.BodyPublishers.noBody();
        }
    }

    private String getSystemMessage(){
        ///TODO: fa generarea de profil aci
        return "You are a simulation of a person with certaing characteristics that will be given to you. For the rest of this\n"
                + "conversation or untill you receive the message \"end of simulation\", you must act and hold a conversation as you are this person. \n"
                + "You must take into consideration the positive and negative characteristics when responding. This person is beeing interviewed\n"
                + "for a job. Messages you will receive will be from the interviewer, act according to the persons characteristics, as if it is\n"
                + "beeing interviewed. You dont have to always respond in a long or elevated way. If the person is impatient or if it is young\n"
                + "and inexperiened or maybe it has emotions this can affect the response. GIVE SIMPLE ANSWERS. If the message you receive is rude,\n"
                + "respond accordingly, or in the same manner.\n"
                + "This is the profile of the person you must simulate:" ///TODO: de aici se taie
                + "Introduction:\n" +
                "Hello, my name is Alex Thompson. I'm a 23-year-old student currently in my third year of computer science at the University of TechHub. Originally from the vibrant city of San Francisco, I've been deeply passionate about technology since a young age.\n" +
                "\n" +
                "Social Characteristics:\n" +
                "\n" +
                "Adaptable: Having lived in different cities, I quickly adapt to new environments and thrive in diverse settings.\n" +
                "Collaborative: Enjoying group projects, I believe in the power of teamwork to achieve common goals efficiently.\n" +
                "Analytical: A natural problem-solver, I approach challenges with a logical mindset, breaking them down into manageable parts.\n" +
                "Effective Communicator: I understand the importance of clear communication in both technical and non-technical discussions.\n" +
                "Curious: A continuous learner, I always seek to expand my knowledge in emerging technologies and industry trends.\n" +
                "Detail-Oriented: In both coding and personal life, I pay meticulous attention to details to ensure quality outcomes.\n" +
                "Initiative-taker: I actively seek opportunities for growth and improvement, demonstrating a proactive attitude.\n" +
                "Skills and Capabilities:\n" +
                "\n" +
                "Backend Development: Proficient in backend technologies with hands-on experience gained during an internship at Evozon.\n" +
                "Programming Languages: Solid understanding and practical usage of languages like Python, Java, and C++.\n" +
                "Database Management: Skilled in designing and managing databases, ensuring efficient data storage and retrieval.\n" +
                "Problem Solving: Possess strong analytical skills to identify and resolve complex technical issues.\n" +
                "Version Control: Familiar with using Git for collaborative coding and version control.\n" +
                "Background Information:\n" +
                "I completed a rewarding internship at Evozon, where I focused on backend development. This experience allowed me to apply theoretical knowledge to real-world projects and work alongside experienced professionals. Additionally, I actively engage in coding competitions, contributing to open-source projects during my spare time.\n" +
                "\n" +
                "Positive Traits:\n" +
                "I am known for my commitment to quality work, my ability to learn quickly, and my friendly and approachable demeanor. I am always eager to contribute to a positive team dynamic, bringing enthusiasm and a fresh perspective to the table.\n" +
                "\n" +
                "Areas for Improvement:\n" +
                "While I am detail-oriented, I sometimes find myself spending too much time refining aspects that may not significantly impact the overall outcome. I am working on striking a balance between perfectionism and efficient task completion. Additionally, I am actively seeking opportunities to enhance my skills in frontend development to broaden my expertise in the field.\n";
    }

    private List<Map<String, String>> getConversationHistory(Chat chat){
        List<Map<String, String>> conversationHistory = new ArrayList<>();
        var previous_messages = chat.getMessageList()
                .stream()
                .sorted(Comparator.comparingInt(Message::getCount))
                .toList();

        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", chat.getIdentity());

        conversationHistory.add(systemMessage);

        for(int i = 0; i < previous_messages.size(); i++){
            ///TODO: after profile from above is cut, take into consideration that on position 0 is the profile and
            ///TODO: the parity reverses
//            if(i == 0){
//                continue;///TODO: add profile
//            }
            if(i % 2 == 0){
                Map<String, String> userMessage = new HashMap<>();
                userMessage.put("role", "user");
                userMessage.put("content", previous_messages.get(i).getMessage());
                conversationHistory.add(userMessage);
            }
            else{
                Map<String, String> assistantMessage = new HashMap<>();
                assistantMessage.put("role", "assistant");
                assistantMessage.put("content", previous_messages.get(i).getMessage());
                conversationHistory.add(assistantMessage);
            }
        }

        return conversationHistory;
    }

    public SendMessageResponse sendGptMessage(SendMessageRequest request){
        var chat = getOrCreateChat(request.getChatId());

        ///TODO: sa apelez creearea de profil si sa o adaug ca prim mesaj in conversatie
//        if(chat.getMessageList().isEmpty()) {
//            var initial_message = Message.builder()
//                    .message()
//                    .chat(chat)
//                    .count(chat.getMessageList().size())
//                    .build();
//            chat.getMessageList().add(initial_message);
//        }

        chat.insertMessage(request.getMessage());

        var text = getConversationHistory(chat);
        var chatResponse = getChatGptResponse(text);
        var chatResponseMessage = chat.insertMessage(chatResponse);

        var savedEntity = chatRepository.save(chat);

        return SendMessageResponse.builder()
                .count(chatResponseMessage.getCount())
                .message(chatResponseMessage.getMessage())
                .chatId(savedEntity.getId())
                .build();
    }

    private Chat getOrCreateChat(int chatId){
        if(chatId != -1) {
            var chat = chatRepository.findById(chatId);
            if(chat.isEmpty())
                throw new RuntimeException("chat not found");
            return chat.get();
        }

        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByEmail(email).get();
        var date = LocalDateTime.now();
        var chat = Chat.builder()
                .user(user)
                .date(date)
                .messageList(new ArrayList<>())
                .build();
        chat.setIdentity(getSystemMessage());
        return chat;
    }
}
