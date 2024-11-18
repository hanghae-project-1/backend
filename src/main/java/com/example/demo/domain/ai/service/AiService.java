package com.example.demo.domain.ai.service;

import com.example.demo.domain.ai.dto.request.AiRequestDto;
import com.example.demo.domain.ai.dto.response.AiResponseDto;
import com.example.demo.domain.ai.entity.Ai;
import com.example.demo.domain.ai.exception.NotFoundAiException;
import com.example.demo.domain.ai.mapper.AiMapper;
import com.example.demo.domain.ai.repository.AiRepository;
import com.example.demo.domain.user.common.exception.OwnerMismatchException;
import com.example.demo.domain.user.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiService {

	@Value("${gemini.api.key}")
	private String geminiApikey;

	private final RestTemplate restTemplate;
	private final UserService userService;
	private final AiMapper aiMapper;
	private final AiRepository aiRepository;


	@Transactional
	public String createAi(AiRequestDto requestDto) throws JSONException {

		URI uri = uriBuilder();

		Map<String, Object> requestBody = buildRequestBody(requestDto);

		String responseText = getResponseFromAi(uri, requestBody);

		Ai ai = aiMapper.toAiEntity(requestDto, responseText);

		aiRepository.save(ai);

		return responseText;
	}

	private static Map<String, Object> buildRequestBody(AiRequestDto requestDto) {
		Map<String, Object> requestBody = new HashMap<>();

		Map<String, List<Map<String, Object>>> contents = new HashMap<>();
		List<Map<String, Object>> parts = new ArrayList<>();
		Map<String, Object> part = new HashMap<>();

		part.put("text", requestDto.requestText() + ", 답변은 간결하게 50자 이하로 작성해줘");
		parts.add(part);
		contents.put("parts", parts);
		requestBody.put("contents", contents);
		return requestBody;
	}

	private String getResponseFromAi(URI uri, Map<String, Object> requestBody) throws JSONException {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, requestBody, String.class);

		JSONObject jsonResponse = new JSONObject(responseEntity.getBody());
		JSONObject candidate = jsonResponse.getJSONArray("candidates").getJSONObject(0);
		JSONObject content = candidate.getJSONObject("content");
		return content.getJSONArray("parts").getJSONObject(0).getString("text");
	}

	private URI uriBuilder() {
		return UriComponentsBuilder.fromUriString("https://generativelanguage.googleapis.com")
				.path("/v1beta/models/gemini-1.5-flash-latest:generateContent")
				.queryParam("key", geminiApikey)
				.encode()
				.build()
				.toUri();
	}

	@Transactional(readOnly = true)
	public List<AiResponseDto> getAllAi(String ownerName) {

		List<Ai> aiList = aiRepository.findAllByOwnerName(ownerName);
		checkOwner(ownerName);
		return aiList.stream().map(aiMapper::toAiResponseDto).toList();

	}

	@Transactional
	public void deleteAi(UUID aiId, String ownerName) {

		Ai ai = aiRepository.findById(aiId).orElseThrow(NotFoundAiException::new);
		checkOwner(ownerName);
		ai.markAsDelete(userService.getCurrentUsername());
	}

	private void checkOwner(String ownerName) {
		if (!ownerName.equals(userService.getCurrentUsername())) {
			throw new OwnerMismatchException();
		}
	}
}
