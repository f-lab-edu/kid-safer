package com.flab.kidsafer.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.kidsafer.domain.PostLike;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.domain.enums.UserType;
import com.flab.kidsafer.dto.PostDTO;
import com.flab.kidsafer.error.exception.PostNotFoundException;
import com.flab.kidsafer.error.exception.UserNotFoundException;
import com.flab.kidsafer.mapper.PostLikeMapper;
import com.flab.kidsafer.mapper.PostMapper;
import com.flab.kidsafer.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class PostLikeServiceTest {

    @InjectMocks
    private PostLikeService postLikeService;

    @Mock
    private PostLikeMapper postLikeMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PostMapper postMapper;

    private PostLike postLike;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void generatePostLike() {
        postLike = new PostLike.Builder().id(1).postId(1).userId(1).build();
    }

    public User generateUser() {
        return new User.Builder("test", "test", UserType.PARENT).build();
    }

    public PostDTO generatePost() {
        return new PostDTO.Builder().build();
    }

    @Test
    @DisplayName("좋아요 성공")
    public void like_success() {
        // given
        doNothing().when(postLikeMapper).insertPostLike(postLike);
        when(userMapper.findById(postLike.getUserId())).thenReturn(generateUser());
        when(postMapper.findPostById(postLike.getPostId())).thenReturn(generatePost());

        // when
        postLikeService.like(postLike.getPostId(), postLike.getUserId());

        // then
        verify(postLikeMapper).insertPostLike(any(PostLike.class));
    }

    @Test
    @DisplayName("해당 유저 없어 좋아요 실패")
    public void like_failure() {
        // given
        doNothing().when(postLikeMapper).insertPostLike(postLike);
        when(userMapper.findById(postLike.getUserId())).thenReturn(null);
        when(postMapper.findPostById(postLike.getPostId())).thenReturn(generatePost());

        // then
        assertThrows(UserNotFoundException.class, () -> {
            postLikeService.like(postLike.getPostId(), postLike.getUserId());   // when
        });

        verify(postLikeMapper, times(0)).insertPostLike(postLike);

    }

    @Test
    @DisplayName("해당 포스트 없어 좋아요 실패")
    public void like_failure2() {
        // given
        doNothing().when(postLikeMapper).insertPostLike(postLike);
        when(userMapper.findById(postLike.getUserId())).thenReturn(generateUser());
        when(postMapper.findPostById(postLike.getPostId())).thenReturn(null);

        // then
        assertThrows(PostNotFoundException.class, () -> {
            postLikeService.like(postLike.getPostId(), postLike.getUserId());   // when
        });

        verify(postLikeMapper, times(0)).insertPostLike(postLike);

    }

    @Test
    @DisplayName("좋아요 취소 성공")
    public void unlike_success() {
        // given
        doNothing().when(postLikeMapper).deletePostLike(postLike);
        when(userMapper.findById(postLike.getUserId())).thenReturn(generateUser());
        when(postMapper.findPostById(postLike.getPostId())).thenReturn(generatePost());
        when(
            postLikeMapper.hasPostLikeByPostIdAndUserId(postLike.getPostId(), postLike.getUserId()))
            .thenReturn(true);

        // when
        postLikeService.unlike(postLike.getPostId(), postLike.getUserId());

        // then
        verify(postLikeMapper).deletePostLike(any(PostLike.class));
    }

    @Test
    @DisplayName("해당 유저가 좋아요 한 기록이 없어 좋아요 취소 실패")
    public void unlike_failure1() {
        // given
        doNothing().when(postLikeMapper).deletePostLike(postLike);
        when(userMapper.findById(postLike.getUserId())).thenReturn(generateUser());
        when(postMapper.findPostById(postLike.getPostId())).thenReturn(null);

        // then
        assertThrows(PostNotFoundException.class, () -> {
            postLikeService.unlike(postLike.getPostId(), postLike.getUserId());   // when
        });
        verify(postLikeMapper, times(0)).deletePostLike(postLike);

    }

    @Test
    @DisplayName("해당 유저가 없어 좋아요 취소 실패")
    public void unlike_failure2() {
        // given
        doNothing().when(postLikeMapper).insertPostLike(postLike);
        when(userMapper.findById(postLike.getUserId())).thenReturn(generateUser());
        when(postMapper.findPostById(postLike.getPostId())).thenReturn(null);

        // then
        assertThrows(UserNotFoundException.class, () -> {
            postLikeService.like(postLike.getPostId(), -999);   // when
        });
        verify(postLikeMapper, times(0)).deletePostLike(postLike);

    }
}
