package com.flab.kidsafer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.flab.kidsafer.dto.PostCommentDTO;
import com.flab.kidsafer.error.exception.OperationNotAllowedException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    private PostCommentDTO 첫번째코멘트;
    private PostCommentDTO 두번째코멘트;

    private final int userIdFrist = 1;
    private final int userIdSecond = 2;

    @BeforeEach
    public void setUp() {
        LocalDateTime currentTime = LocalDateTime.now();
        첫번째코멘트 = new PostCommentDTO(1, 999, 1, 0, "첫번째 댓글 입니다.", userIdFrist, currentTime,
            currentTime, null,
            null);
        두번째코멘트 = new PostCommentDTO(1, 999, 1, 0, "두번째 댓글 입니다.", userIdFrist, currentTime,
            currentTime, null,
            null);
    }

    @Test
    @DisplayName("댓글 조회 실패")
    public void getCommentFailure() {
        //given
        int id = -1;

        //wehn
        List<PostCommentDTO> postComment = commentService.getCommentsByPostId(id, userIdFrist);

        //then
        assertEquals(0, postComment.size());
    }

    @Transactional
    @Test
    @DisplayName("댓글 조회 성공")
    public void getCommentSuccess() {
        //given
        commentService.saveNewComments(첫번째코멘트);
        commentService.saveNewComments(두번째코멘트);
        int postId = 999;

        //wehn
        List<PostCommentDTO> postComment = commentService.getCommentsByPostId(postId, userIdFrist);

        //then
        assertEquals(2, postComment.size());
    }

    @Transactional
    @Test
    @DisplayName("다른 글쓴이로 인해 수정 실패")
    public void updateCommentFail() {
        //given
        int insertCommentId = commentService.saveNewComments(첫번째코멘트);

        LocalDateTime currentTime = LocalDateTime.now();
        두번째코멘트 = new PostCommentDTO(1, 999, insertCommentId, 0, "두번째 댓글 입니다.", userIdSecond,
            currentTime, currentTime, null,
            null);

        //wehn
        //then
        assertThrows(OperationNotAllowedException.class, () -> {
            commentService.updateComment(두번째코멘트, userIdSecond);
        });
    }

    @Transactional
    @Test
    @DisplayName("댓글 수정 성공")
    public void updateCommentSuccess() {
        //given
        int insertCommentId = commentService.saveNewComments(첫번째코멘트);

        LocalDateTime currentTime = LocalDateTime.now();
        두번째코멘트 = new PostCommentDTO(1, 999, insertCommentId, 0, "두번째 댓글 입니다.", 1, currentTime,
            currentTime, null,
            null);

        //wehn
        commentService.updateComment(두번째코멘트, userIdFrist);

        //then
        assertEquals("두번째 댓글 입니다.", 두번째코멘트.getCommentContent());
    }

    @Transactional
    @Test
    @DisplayName("다른 글쓴이로 인해 삭제 실패")
    public void deleteCommentFail() {
        //given
        int insertCommentId = commentService.saveNewComments(첫번째코멘트);

        //wehn
        //then
        assertThrows(OperationNotAllowedException.class, () -> {
            commentService.deleteComment(insertCommentId, userIdSecond);
        });
    }

    @Transactional
    @Test
    @DisplayName("삭제 성공")
    public void deleteCommentSuccess() {
        //given
        int insertCommentId = commentService.saveNewComments(첫번째코멘트);

        //wehn
        commentService.deleteComment(insertCommentId, userIdFrist);

        List<PostCommentDTO> postCommentDTOList = commentService
            .getCommentsByPostId(첫번째코멘트.getPostId(), userIdFrist);

        boolean existComment = postCommentDTOList.stream()
            .filter(i -> i.getCommentId() == insertCommentId)
            .findFirst()
            .isPresent();

        //then
        assertEquals(false, existComment);
    }
}
