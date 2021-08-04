package com.flab.kidsafer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.kidsafer.dto.PostDTO;
import com.flab.kidsafer.dto.PostRequestDTO;
import com.flab.kidsafer.error.exception.OperationNotAllowedException;
import com.flab.kidsafer.mapper.PostMapper;
import com.flab.kidsafer.mapper.PostRequestMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class PostRequestServiceTest {

    @InjectMocks
    private PostRequestService postRequestService;

    @Mock
    private PostRequestMapper postRequestMapper;

    @Mock
    private PostMapper postMapper;

    private PostRequestDTO postRequest;
    private PostDTO post;

    @BeforeEach
    public void generatePost() {
        postRequest = new PostRequestDTO.Builder()
            .id(1)
            .postId(1)
            .contents("테스트")
            .registerDate(LocalDateTime.now())
            .build();
        post = new PostDTO.Builder(30)
            .setParentId(1)
            .setDistrictId(100)
            .setTitle("도우미를 구합니다.")
            .setContents("주 3회 등하원 도우미를 구합니다.")
            .setFee(30000)
            .setStartDate(LocalDate.of(2021, 5, 1))
            .setEndDate(LocalDate.of(2021, 12, 31))
            .setRegisterDate(LocalDateTime.now())
            .setDueDate(LocalDateTime.parse("2021-04-30T10:00:00"))
            .build();
    }

    @Test
    @DisplayName("본인의 신청내역 조회")
    public void getOneRequest_success() {
        // given
        when(postRequestMapper.getSingleRequest(anyInt(), anyInt())).thenReturn(postRequest);
        when(postMapper.findPostById(anyInt())).thenReturn(post);

        // when
        PostRequestDTO postRequestDTO = postRequestService.getSingleRequest(postRequest.getPostId(),
            postRequest.getUserId());

        // then
        assertEquals(postRequest, postRequestDTO);
    }

    @Test
    @DisplayName("해당 포스트의 모든 신청내역 조회")
    public void getAllRequests_success() {
        // given
        List<PostRequestDTO> postRequests = new ArrayList<>();
        when(postRequestMapper.getAllRequests(anyInt())).thenReturn(postRequests);
        when(postMapper.findPostById(anyInt())).thenReturn(post);

        // when
        List<PostRequestDTO> postRequestDTOS = postRequestService
            .getAllRequests(postRequest.getPostId());

        // then
        assertEquals(postRequests, postRequestDTOS);
    }

    @Test
    @DisplayName("게시글 신청 성공")
    public void applyRequest_success() {
        // given
        doNothing().when(postRequestMapper).applyRequest(postRequest);

        // when
        postRequestMapper.applyRequest(postRequest);

        // then
        verify(postRequestMapper).applyRequest(any(PostRequestDTO.class));
    }

    @Test
    @DisplayName("본인이 신청한 내역이 이미 존재해 신청 실패")
    public void applyRequest_failure() {
        // given
        when(postRequestMapper.getSingleRequest(anyInt(), anyInt())).thenReturn(postRequest);
        when(postMapper.findPostById(anyInt())).thenReturn(post);

        // then
        assertThrows(OperationNotAllowedException.class, () -> {
            postRequestService.applyRequest(postRequest);  // when
        });

        // then
        verify(postRequestMapper, times(0)).applyRequest(any(PostRequestDTO.class));
    }


    @Test
    @DisplayName("게시글 신청 취소 성공")
    public void cancelRequest_success() {
        // given
        doNothing().when(postRequestMapper).cancelRequest(anyInt(), anyInt());

        // when
        postRequestMapper.cancelRequest(postRequest.getPostId(), postRequest.getUserId());

        // then
        verify(postRequestMapper).cancelRequest(anyInt(), anyInt());
    }

    @Test
    @DisplayName("본인이 신청한 내역이 존재하지 않아 신청 취소 실패")
    public void cancelRequest_failure() {
        // given
        when(postRequestMapper.getSingleRequest(anyInt(), anyInt())).thenReturn(null);
        when(postMapper.findPostById(anyInt())).thenReturn(post);

        // then
        assertThrows(OperationNotAllowedException.class, () -> {
            postRequestService
                .cancelRequest(postRequest.getPostId(), postRequest.getUserId());  // when
        });

        // then
        verify(postRequestMapper, times(0)).cancelRequest(anyInt(), anyInt());
    }
}
