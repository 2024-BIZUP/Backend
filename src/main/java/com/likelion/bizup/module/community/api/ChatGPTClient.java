package com.likelion.bizup.module.community.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ChatGPTClient {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getModifiedContent(String content) {
        try {
            // 요청 본문 구성
            Map<String, Object> requestBody = buildRequestBody(content);

            // HTTP Header 설정
            HttpHeaders headers = buildHeaders();

            // API 호출
            ResponseEntity<String> response = sendRequest(requestBody, headers);

            // 응답 상태 코드 확인
            if (response.getStatusCode() == HttpStatus.OK) {
                // 응답에서 수정된 content 추출
                String modifiedContent = extractModifiedContent(response.getBody());
                if (modifiedContent != null) {
                    return modifiedContent;
                }
            } else {
                // 오류 응답 처리
                logErrorResponse(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    private Map<String, Object> buildRequestBody(String content) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");

        Map<String, String> messageContent = new HashMap<>();
        messageContent.put("role", "user");
        messageContent.put("content", "다음 내용을 더 세련되게 다듬어 주세요: " + content);

        requestBody.put("messages", List.of(messageContent));
        return requestBody;
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        return headers;
    }

    private ResponseEntity<String> sendRequest(Map<String, Object> requestBody, HttpHeaders headers) {
        String jsonRequestBody = null;
        try {
            jsonRequestBody = objectMapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<>(jsonRequestBody, headers);
        return restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);
    }

    private String extractModifiedContent(String responseBody) {
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode choicesNode = rootNode.get("choices");

            if (choicesNode != null && choicesNode.isArray() && choicesNode.size() > 0) {
                JsonNode choiceNode = choicesNode.get(0);
                JsonNode messageNode = choiceNode.get("message");
                if (messageNode != null) {
                    return messageNode.get("content").asText();
                }
            } else {
                // 'choices' 필드가 없는 경우 또는 잘못된 응답
                System.out.println("No choices or invalid response format.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void logErrorResponse(ResponseEntity<String> response) {
        System.out.println("Error: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());
    }
}
