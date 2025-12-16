package com.example.sketchTalk.service;

import com.example.sketchTalk.dto.chat.in.DrawReq;
import com.example.sketchTalk.dto.chat.out.CommentRes;
import com.example.sketchTalk.dto.chat.out.ImageRes;
import com.example.sketchTalk.dto.webClient.out.*;
import com.example.sketchTalk.dto.chat.out.DiaryRes;
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
    @Value("${fastapi.image-endpoint}")
    private String imageURL;

    public ReplyRes sendChat(Long userId, String dialog) {
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

    public DiaryRes requestDiary(Long userId) {
        String uri = baseURL + "/diary";
        DiaryReq diaryReq = new DiaryReq(userId);
        Mono<DiaryRes> result = webClient.post()
                .uri(uri)
                .bodyValue(diaryReq)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(body -> {
                                System.err.println("FastAPI 422 Error Body: " + body);
                                return Mono.error(new RuntimeException("FastAPI 요청 실패: " + response.statusCode() + " - " + body));
                            });
                })
                .bodyToMono(DiaryRes.class);
        return result.block();
    }

    public DrawReq requestEnglish(Long userId, String koreanText, DrawReq drawReq) {
        String uri = baseURL + "/diary/english";
        System.out.println("uri : "+uri);
        EnglishReq englishReq = new EnglishReq(userId, koreanText);
        Mono<String> result = webClient.post()
                .uri(uri)
                .bodyValue(englishReq)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(body -> {
                                System.err.println("FastAPI 422 Error Body: " + body);
                                return Mono.error(new RuntimeException("FastAPI 요청 실패: " + response.statusCode() + " - " + body));
                            });
                })
                .bodyToMono(String.class);
        String englishResult;
        try {
            englishResult = result.block();
        } catch (Exception e) {
            // 통신 또는 처리 중 에러 발생 시 적절한 예외 처리
            System.err.println("AI 서버 통신 중 치명적인 오류 발생: " + e.getMessage());
            throw new RuntimeException("AI 콘텐츠 생성 실패", e);
        }
        return new DrawReq(drawReq.diaryId(), englishResult, drawReq.style());
    }

    public ImageRes requestImage(Long userId, DrawReq req) {
        String uri = imageURL + "/image";
        System.out.println("uri : "+uri);
        ImageReq imageReq = new ImageReq(userId, req.content(), req.style());
        Mono<ImageRes> result = webClient.post()
                .uri(uri)
                .bodyValue(imageReq)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(body -> {
                                System.err.println("FastAPI 422 Error Body: " + body);
                                return Mono.error(new RuntimeException("FastAPI 요청 실패: " + response.statusCode() + " - " + body));
                            });
                })
                .bodyToMono(ImageRes.class);
        return result.block();
    }

    public CommentRes requestComment(Long userId, String content) {
        String uri = baseURL + "/comment";
        CommentReq commentReq = new CommentReq(userId, content);
        Mono<CommentRes> result = webClient.post()
                .uri(uri)
                .bodyValue(commentReq)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(body -> {
                                System.err.println("FastAPI 422 Error Body: " + body);
                                return Mono.error(new RuntimeException("FastAPI 요청 실패: " + response.statusCode() + " - " + body));
                            });
                })
                .bodyToMono(CommentRes.class);
        return result.block();
    }
}
