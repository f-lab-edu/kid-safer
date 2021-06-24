package com.flab.kidsafer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.kidsafer.dto.PostDTO;
import com.flab.kidsafer.error.exception.OperationNotAllowedException;
import com.flab.kidsafer.mapper.PostMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostMapper postMapper;

    private PostDTO post;

    @BeforeEach
    public void generatePost() {
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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("게시글 상세 조회")
    public void getOnePost_success() {
        // given
        when(postMapper.findPostById(anyInt())).thenReturn(post);

        // when
        PostDTO resultPost = postService.getPostInfoByPostId(100);

        // then
        assertEquals(post, resultPost);
    }

    @Test
    @DisplayName("게시글 등록 성공")
    public void registerPost_success() {
        // given
        doNothing().when(postMapper).registerPost(post);

        // when
        postService.registerPost(post);

        // then
        verify(postMapper).registerPost(any(PostDTO.class));
    }

    @Test
    @DisplayName("게시글 작성자가 아니라면 수정 실패")
    public void modifyPost_failure() {
        // given
        doNothing().when(postMapper).modifyPostInfo(post);

        // then
        assertThrows(OperationNotAllowedException.class, () -> {
            postService.modifyPostsInfo(post, post.getParentId() + 1);  // when
        });

        // then
        verify(postMapper, times(0)).modifyPostInfo(any(PostDTO.class));
    }

    @Test
    @DisplayName("게시글 작성자일경우 수정 성공")
    public void modifyPost_success() {
        // given
        doNothing().when(postMapper).modifyPostInfo(post);

        // when
        postService.modifyPostsInfo(post, post.getParentId());

        // then
        verify(postMapper).modifyPostInfo(any(PostDTO.class));
    }

    @Test
    @DisplayName("게시글 작성자가 아니라면 삭제 실패")
    public void deletePost_failure() {
        // given
        when(postMapper.findPostById(post.getId())).thenReturn(post);

        // then
        assertThrows(OperationNotAllowedException.class, () -> {
            postService.deletePosts(post.getId(), post.getParentId() + 1);  // when
        });

        // then
        verify(postMapper, times(0)).deletePost(post.getId());
    }

    @Test
    @DisplayName("게시글 작성자일경우 삭제 성공")
    public void deletePost_success() {
        // given
        doNothing().when(postMapper).deletePost(post.getId());
        when(postMapper.findPostById(post.getId())).thenReturn(post);

        // when
        postService.deletePosts(post.getId(), post.getParentId());

        // then
        verify(postMapper).deletePost(post.getId());
    }
}
