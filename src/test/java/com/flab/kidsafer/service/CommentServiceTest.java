package com.flab.kidsafer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.kidsafer.domain.PostComment;
import com.flab.kidsafer.dto.PostCommentDTO;
import com.flab.kidsafer.error.exception.OperationNotAllowedException;
import com.flab.kidsafer.mapper.CommentMapper;
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
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentMapper commentMapper;

    private PostCommentDTO 첫번째코멘트DTO;
    private PostCommentDTO 두번째코멘트DTO;

    private PostComment 첫번째코멘트;
    private PostComment 두번째코멘트;

    private final int firstUserId = 1;
    private final int secondUserId = 2;

    @BeforeEach
    public void setUp() {
        첫번째코멘트DTO = new PostCommentDTO.Builder()
            .commentLevel(1)
            .postId(1)
            .commentId(1)
            .commentParentId(0)
            .commentContent("첫번째 댓글 입니다.")
            .commentWriterId(firstUserId)
            .role(null)
            .commentStatus(null)
            .build();

        두번째코멘트DTO = new PostCommentDTO.Builder()
            .commentLevel(1)
            .postId(1)
            .commentId(2)
            .commentParentId(0)
            .commentContent("두번째 댓글 입니다.")
            .commentWriterId(secondUserId)
            .role(null)
            .commentStatus(null)
            .build();

        첫번째코멘트 = new PostComment(첫번째코멘트DTO);
        두번째코멘트 = new PostComment(두번째코멘트DTO);
    }

    public List<PostComment> generateComment() {
        return new ArrayList<PostComment>(){
            {this.add(첫번째코멘트); this.add(두번째코멘트);}
        };
    }
    @Test
    @DisplayName("댓글 조회 실패")
    public void getCommentFailure() {
        //given
        int id = -1;

        //when
        List<PostCommentDTO> postComment = commentService.getCommentsByPostId(id, firstUserId);

        //then
        assertEquals(0, postComment.size());
    }

    @Test
    @DisplayName("댓글 조회 성공")
    public void getCommentSuccess() {
        //given
        doNothing().when(commentMapper).insertNewComment(첫번째코멘트);
        doNothing().when(commentMapper).insertNewComment(두번째코멘트);
        when(commentMapper.selectCommentsByPostId(첫번째코멘트.getPostId())).thenReturn(generateComment());

        //when
        List<PostCommentDTO> postComment = commentService.getCommentsByPostId(anyInt(), firstUserId);

        //then
        assertEquals(2, postComment.size());
    }

    @Test
    @DisplayName("다른 글쓴이로 인해 수정 실패")
    public void updateCommentFail() {
        //given
        doNothing().when(commentMapper).insertNewComment(첫번째코멘트);
        when(commentMapper.selectCommentById(첫번째코멘트.getCommentId())).thenReturn(첫번째코멘트);

        //then
        assertThrows(OperationNotAllowedException.class, () -> {
            commentService.updateComment(두번째코멘트DTO, secondUserId);
        });

        // then
        verify(commentMapper, times(0)).updateComment(any(PostComment.class));
    }

    @Test
    @DisplayName("댓글 수정 성공")
    public void updateCommentSuccess() {
        //given
        doNothing().when(commentMapper).insertNewComment(첫번째코멘트);
        when(commentMapper.selectCommentById(첫번째코멘트.getCommentId())).thenReturn(첫번째코멘트);

        //when
        commentService.updateComment(첫번째코멘트DTO, firstUserId);

        //then
        verify(commentMapper).updateComment(any(PostComment.class));
    }

    @Test
    @DisplayName("다른 글쓴이로 인해 삭제 실패")
    public void deleteCommentFail() {
        //given
        doNothing().when(commentMapper).insertNewComment(첫번째코멘트);
        when(commentMapper.selectCommentById(첫번째코멘트.getCommentId())).thenReturn(첫번째코멘트);

        //then
        assertThrows(OperationNotAllowedException.class, () -> {
            commentService.deleteComment(첫번째코멘트.getCommentId(), secondUserId);
        });

        // then
        verify(commentMapper, times(0)).deleteComment(첫번째코멘트.getCommentId());
    }

    @Test
    @DisplayName("삭제 성공")
    public void deleteCommentSuccess() {
        //given
        doNothing().when(commentMapper).insertNewComment(첫번째코멘트);
        when(commentMapper.selectCommentById(첫번째코멘트.getCommentId())).thenReturn(첫번째코멘트);

        //wehn
        commentService.deleteComment(첫번째코멘트.getCommentId(), firstUserId);

        //then
        verify(commentMapper).deleteComment(첫번째코멘트.getCommentId());
    }
}
