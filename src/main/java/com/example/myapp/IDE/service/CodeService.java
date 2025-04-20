package com.example.myapp.IDE.service;

import com.example.myapp.IDE.dto.CodeIndexRequest;
import com.example.myapp.IDE.dto.FileInfo;
import com.example.myapp.IDE.dto.FolderInfo;
import com.example.myapp.IDE.entity.File;
import com.example.myapp.IDE.entity.Folder;
import com.example.myapp.IDE.entity.Quest;
import com.example.myapp.IDE.entity.Submission;
import com.example.myapp.IDE.repository.FileRepository;
import com.example.myapp.IDE.repository.FolderRepository;
import com.example.myapp.IDE.repository.QuestRepository;
import com.example.myapp.IDE.repository.SubmissionRepository;

import com.example.myapp.IDE.dto.CodeRunRequest;
import com.example.myapp.IDE.dto.CodeRunResponse;
import com.example.myapp.IDE.dto.JdoodleRequest;
import com.example.myapp.IDE.dto.JdoodleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CodeService {

    private final FolderRepository folderRepository;
    private final FileRepository fileRepository;
    private final SubmissionRepository submissionRepository;
    private final QuestRepository questRepository;

    //코드 실행 API들의 주소와 ID
    @Value("${jdoodle.api.url}")
    private String jdoodleApiUrl;
    @Value("${jdoodle.client.id}")
    private String jdoodleClientId;
    @Value("${jdoodle.client.secret}")
    private String jdoodleClientSecret;

    //코드 목록 조회 로직
    public List<FolderInfo> getCodeIndex(Long questId, String userId, CodeIndexRequest request) {
        List<Folder> folders = folderRepository.findByParentId_FolderId(request.getParent_id());
        List<FolderInfo> result = new ArrayList<>();

        for (Folder folder : folders) {
            List<File> files = fileRepository.findByFolder_FolderIdAndSubmission_UserId_UserIdAndSubmission_QuestId_QuestId(
                    folder.getFolderId(), userId, questId.intValue()
            );

            List<FileInfo> fileInfos = files.stream().map(file ->
                    new FileInfo(
                            file.getFileId(),
                            file.getFileName(),
                            file.getCodeContent(),
                            file.getLanguage()
                    )
            ).toList();

            result.add(new FolderInfo(
                    folder.getFolderId(),
                    folder.getFolderName(),
                    folder.getParentId() != null ? folder.getParentId().getFolderId() : null,
                    fileInfos
            ));
        }
        return result;
    }

    //폴더 구조 조회 로직
    public FolderInfo getFolderDetail(Long folderId, String userId, Integer questId) {
        Optional<Folder> folderOptional = folderRepository.findById(folderId);
        if (folderOptional.isPresent()) {
            Folder folder = folderOptional.get();
            List<File> files = fileRepository.findByFolder_FolderIdAndSubmission_UserId_UserIdAndSubmission_QuestId_QuestId(
                    folderId, userId, questId
            );
            List<FileInfo> fileInfos = files.stream().map(file ->
                    new FileInfo(
                            file.getFileId(),
                            file.getFileName(),
                            file.getCodeContent(),
                            file.getLanguage()
                    )
            ).toList();
            return new FolderInfo(
                    folder.getFolderId(),
                    folder.getFolderName(),
                    folder.getParentId() != null ? folder.getParentId().getFolderId() : null,
                    fileInfos
            );
        }
        return null;
    }
    
    //파일 정보 조회 로직
    public FileInfo getFileDetail(Long fileId) {
        Optional<File> fileOptional = fileRepository.findById(fileId);
        return fileOptional.map(file -> new FileInfo(
                file.getFileId(),
                file.getFileName(),
                file.getCodeContent(),
                file.getLanguage()
        )).orElse(null);
    }
    
    //파일 생성 로직
    public File createFile(Long folderId, Integer submissionId, FileInfo fileInfo) {
        Optional<Folder> folderOptional = folderRepository.findById(folderId);
        Optional<Submission> submissionOptional = submissionRepository.findById(submissionId);
        if (folderOptional.isPresent() && submissionOptional.isPresent()) {
            File file = File.builder()
                    .fileName(fileInfo.getFile_name())
                    .codeContent(fileInfo.getCode_content())
                    .language(fileInfo.getLanguage())
                    .folder(folderOptional.get())
                    .submission(submissionOptional.get())
                    .build();
            return fileRepository.save(file);
        }
        return null;
    }

    //파일 삭제 로직
    public void deleteFile(Long fileId) {
        fileRepository.deleteById(fileId);
    }
    
    //파일 업데이트 로직
    public File updateFile(Long fileId, FileInfo fileInfo) {
        Optional<File> fileOptional = fileRepository.findById(fileId);
        if (fileOptional.isPresent()) {
            File existingFile = fileOptional.get();
            existingFile.setFileName(fileInfo.getFile_name());
            existingFile.setCodeContent(fileInfo.getCode_content());
            existingFile.setLanguage(fileInfo.getLanguage());
            return fileRepository.save(existingFile);
        }
        return null;
    }
    
    
    //폴더 생성 로직
    public Folder createFolder(Long parentId, String folderName) { // submissionId 제거
        Optional<Folder> parentFolder = parentId != null ? folderRepository.findById(parentId) : Optional.empty();
        Folder folder = Folder.builder()
                .folderName(folderName)
                .parentId(parentFolder.orElse(null))
                .files(new ArrayList<>())
                .build();
        return folderRepository.save(folder);
    }

    //폴더 삭제로직 
    public void deleteFolder(Long folderId) {
        folderRepository.deleteById(folderId);
    }

    //코드 업데이트 로직
    public boolean updateQuestStatus(Long questId, String userId, String questStatus) {
        log.info("updateQuestStatus 호출 - questId: {}, userId: {}, questStatus: {}", questId, userId, questStatus);
        Optional<Quest> questOptional = questRepository.findById(questId.intValue());
        if (questOptional.isPresent()) {
            Quest quest = questOptional.get();
            log.info("퀘스트 조회 성공 - quest: {}", quest);


            quest.setQuesStatus(questStatus);
            log.info("퀘스트 상태 업데이트 시도 - newStatus: {}", quest.getQuesStatus());
            questRepository.save(quest);
            log.info("퀘스트 상태 업데이트 성공");
            return true;
        } else {
            log.warn("해당 questId로 퀘스트를 찾을 수 없음 - questId: {}", questId);
            return false;
        }
    }

    private final RestTemplate restTemplate;
    
    //코드 실행 로직
    public CodeRunResponse runCode(CodeRunRequest clientRequest) {
        JdoodleRequest jdoodleRequest = new JdoodleRequest();
        jdoodleRequest.setScript(clientRequest.getCode_context());
        jdoodleRequest.setLanguage(clientRequest.getLanguage());
        jdoodleRequest.setClientId(jdoodleClientId);
        jdoodleRequest.setClientSecret(jdoodleClientSecret);

        JdoodleResponse jdoodleResponse = restTemplate.postForObject(
                jdoodleApiUrl,
                jdoodleRequest,
                JdoodleResponse.class
        );

        if (jdoodleResponse != null) {
            log.info("JDOodle API 응답: {}", jdoodleResponse);
            return new CodeRunResponse(jdoodleResponse.getOutput());
        } else {
            log.error("JDOodle API 응답 오류");
            return new CodeRunResponse("코드 실행 오류");
        }
    }
}