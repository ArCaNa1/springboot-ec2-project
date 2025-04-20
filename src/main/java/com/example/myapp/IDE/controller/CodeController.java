package com.example.myapp.IDE.controller;

import com.example.myapp.IDE.dto.CodeIndexRequest;
import com.example.myapp.IDE.dto.CodeStatusResponse;
import com.example.myapp.IDE.dto.CodeStatusUpdateRequest;
import com.example.myapp.IDE.dto.FolderInfo;
import com.example.myapp.IDE.service.CodeService;
import com.example.myapp.IDE.dto.CodeRunRequest;
import com.example.myapp.IDE.dto.CodeRunResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class CodeController {

    private final CodeService codeService;

    //코드목록조회 엔드포인트
    @GetMapping("/{questId}/{userId}")
    public ResponseEntity<List<FolderInfo>> getCodeIndex(
            @PathVariable Long questId,
            @PathVariable String userId,
            @RequestBody CodeIndexRequest request
    ) {
        return ResponseEntity.ok(codeService.getCodeIndex(questId, userId, request));
    }

    //코드상태변경 엔드포인트
    @PatchMapping("/{questId}/{userId}/status")
    public ResponseEntity<CodeStatusResponse> updateCodeStatus(
            @PathVariable Long questId,
            @PathVariable String userId,
            @RequestBody CodeStatusUpdateRequest request
    )  {
        boolean success = codeService.updateQuestStatus(questId, userId, request.getQuest_status());
        if (success) {
            return ResponseEntity.ok(new CodeStatusResponse(true));
        } else {
            return ResponseEntity.badRequest().body(new CodeStatusResponse(false));
        }
    }

    //코드 실행 엔드포인트
    @PostMapping("/run")
    public ResponseEntity<CodeRunResponse> runCode(@RequestBody CodeRunRequest request) {
        CodeRunResponse response = codeService.runCode(request);
        return ResponseEntity.ok(response);
    }


}