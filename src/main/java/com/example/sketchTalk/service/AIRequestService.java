package com.example.sketchTalk.service;

import com.example.sketchTalk.dto.chat.in.ReplyReq;
import com.example.sketchTalk.dto.chat.out.ReplyRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AIRequestService {

    private final WebClient webClient;
    @Value("${fastapi.endpoint}")
    private String baseURL;

    public ReplyRes sendChat(long userId, String dialog) {
        String uri = baseURL + "/chat";
        ReplyReq replyReq = new ReplyReq(userId, dialog);
        Mono<ReplyRes> result = webClient.post()
                .uri(uri)
                .bodyValue(replyReq)
                .retrieve()
                // 4XX/5XX 응답이 올 경우, 본문을 읽어 로그로 출력
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(body -> {
                                System.err.println("FastAPI 422 Error Body: " + body);
                                return Mono.error(new RuntimeException("FastAPI 요청 실패: " + response.statusCode() + " - " + body));
                            });
                })
                .bodyToMono(ReplyRes.class);
        return result.block();
    }
}
