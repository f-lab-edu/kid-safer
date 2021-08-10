package com.flab.kidsafer.controller;

import com.flab.kidsafer.domain.Kid;
import com.flab.kidsafer.service.KidService;
import com.flab.kidsafer.utils.SessionUtil;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kids")
public class KidController {

    @Autowired
    private KidService kidService;

    /*
     * 특정 아이의 상세 정보 조회
     */
    @GetMapping("/{kidId}")
    public ResponseEntity<Kid> getOneKid(@PathVariable int kidId) {
        Kid kid = kidService.getOneKid(kidId);
        return ResponseEntity.ok(kid);
    }

    /*
     * 부모로부터 등록된 아이 조회
     */
    @GetMapping
    public Optional<List<Kid>> getAllKid(HttpSession httpSession) {
        int parentId = SessionUtil.getLoginUserId(httpSession);
        return Optional.ofNullable(kidService.getAllKid(parentId));
    }

    /*
     * 아이 등록
     */
    @PostMapping
    public ResponseEntity<Kid> registerKid(@Valid @RequestBody Kid kid) {
        int id = kidService.registerKid(kid);
        return ResponseEntity.ok(kidService.getOneKid(id));
    }

    /*
     * 아이 내역 수정
     */
    @PutMapping("/{kidId}")
    public void updateKid(@PathVariable int kidId, @Valid @RequestBody Kid kid,
        HttpSession httpSession) {
        int parentId = SessionUtil.getLoginUserId(httpSession);
        kidService.updateKid(kid, parentId);
    }

    /*
     * 아이 내역 삭제
     */
    @DeleteMapping("/{kidId}")
    public void deleteKid(@PathVariable int kidId, HttpSession httpSession) {
        int parentId = SessionUtil.getLoginUserId(httpSession);
        kidService.deleteKid(kidId, parentId);
    }
}
