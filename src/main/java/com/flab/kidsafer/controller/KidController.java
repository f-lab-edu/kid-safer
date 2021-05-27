package com.flab.kidsafer.controller;

import com.flab.kidsafer.domain.Kid;
import com.flab.kidsafer.service.KidService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kids")
public class KidController {

    @Autowired
    private KidService kidService;

    @PostMapping
    public ResponseEntity registerKid(@Valid @RequestBody Kid kid) {
        return ResponseEntity.ok().build(); //TO-DO: 아이 추가 개발 예정
    }
}
