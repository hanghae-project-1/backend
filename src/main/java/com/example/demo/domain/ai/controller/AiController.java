package com.example.demo.domain.ai.controller;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.ai.dto.request.AiRequestDto;
import com.example.demo.domain.ai.dto.response.AiResponseDto;
import com.example.demo.domain.ai.service.AiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai")
public class AiController {

	private final AiService aiService;

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_OWNER')")
	public Response<String> createAi(@Valid @RequestBody AiRequestDto requestDto) throws JSONException {

		String responseText = aiService.createAi(requestDto);

		return Response.<String>builder()
				.data(responseText)
				.build();

	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_OWNER')")
	public Response<List<AiResponseDto>> getAllAi(@RequestParam("ownerName") String ownerName) {
		return Response.<List<AiResponseDto>>builder()
				.data(aiService.getAllAi(ownerName))
				.build();
	}

	@DeleteMapping("/{aiId}")
	@PreAuthorize("hasRole('ROLE_OWNER')")
	public Response<Void> deleteAi(@PathVariable UUID aiId, @RequestParam(required = true) String ownerName) {

		aiService.deleteAi(aiId, ownerName);

		return Response.<Void>builder()
				.build();
	}


}
