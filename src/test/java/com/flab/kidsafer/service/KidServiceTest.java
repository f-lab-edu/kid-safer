package com.flab.kidsafer.service;

import static org.junit.jupiter.api.Assertions.*;

import com.flab.kidsafer.domain.Kid;
import com.flab.kidsafer.error.exception.KidNotFoundException;
import com.flab.kidsafer.mapper.KidMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KidServiceTest {

    @Autowired
    private KidMapper kidMapper;
    @Autowired
    private KidService kidService;

    private Kid kid;

    @BeforeEach
    public void setUp() {
        LocalDateTime currentTime = LocalDateTime.now();
        kid = new Kid(1, 1, "F", "2010", "알러지있음", currentTime, currentTime);
    }

    @Test
    @DisplayName("아이 조회 실패")
    public void getOneKid_failure() {
        //when
        int id = -1;

        //then
        assertThrows(KidNotFoundException.class, () -> {
            Kid getKid = kidService.getOneKid(id);   //when
        });
    }

    @Test
    @DisplayName("아이 등록 및 조회 성공")
    public void registerKid_sucess() {
        //when
        int id = kidService.registerKid(kid);
        Kid getKid = kidService.getOneKid(id);

        //then
        assertEquals(kid.getId(), getKid.getId());
    }

    @Test
    @DisplayName("부모의 아이 일괄 조회 성공")
    public void getAllKid_sucess() {
        //when
        List<Kid> getKid = kidService.getAllKid(1);

        //then
        assertNotNull(getKid);
    }

    @Test
    @DisplayName("부모의 아이 일괄 조회 실패")
    public void getAllKid_failure() {
        //when
        List<Kid> getKid = kidService.getAllKid(-1);

        //then
        assertEquals(getKid.size(), 0);
    }

    @Test
    @DisplayName("아이 존재시 삭제 성공")
    public void deleteKid_sucess() {
        //given
        int id = kidService.registerKid(kid);

        //when
        kidService.deleteKid(id, 1);

        //then
        assertThrows(KidNotFoundException.class, () -> {
            Kid getKid = kidService.getOneKid(id);
        });
    }

    @Test
    @DisplayName("아이 존재하지 않을 경우 삭제 실패")
    public void deleteKid_failure() {
        //given
        int id = -1;

        //then
        assertThrows(KidNotFoundException.class, () -> {
            kidService.deleteKid(id, 1);   //when
        });
    }

}
