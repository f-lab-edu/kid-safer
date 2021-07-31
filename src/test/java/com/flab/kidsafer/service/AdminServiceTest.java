package com.flab.kidsafer.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.domain.enums.Status;
import com.flab.kidsafer.domain.enums.UserType;
import com.flab.kidsafer.error.exception.UserNotFoundException;
import com.flab.kidsafer.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private int userId = 1;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void initData() {
        user = new User.Builder("test@test.com", "test", UserType.ADMIN).build();
    }

    @Test
    @DisplayName("차단 성공")
    public void blockUser_sucess() {
        // given
        doNothing().when(userMapper).updateUserStatus(userId, Status.BLOCKED);
        when(userMapper.findById(userId))
            .thenReturn(user);

        // when
        adminService.blockUser(userId);

        // then
        verify(userMapper).updateUserStatus(any(Integer.class), eq(Status.BLOCKED));
    }

    @Test
    @DisplayName("존재하지 않는 대상자로 차단 실패")
    public void blockUser_failure() {
        // given
        doNothing().when(userMapper).updateUserStatus(userId, Status.BLOCKED);
        when(userMapper.findById(userId))
            .thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            adminService.blockUser(userId);  // when
        });

        // then
        verify(userMapper, times(0)).updateUserStatus(any(Integer.class), eq(Status.BLOCKED));
    }

    @Test
    @DisplayName("차단해제 성공")
    public void unblockUser_sucess() {
        // given
        doNothing().when(userMapper).updateUserStatus(userId, Status.DEFAULT);
        when(userMapper.findById(userId))
            .thenReturn(user);

        // when
        adminService.unblockUser(userId);

        // then
        verify(userMapper).updateUserStatus(any(Integer.class), eq(Status.DEFAULT));
    }

    @Test
    @DisplayName("존재하지 않는 대상자로 차단 해제 실패")
    public void unblockUser_failure() {
        // given
        doNothing().when(userMapper).updateUserStatus(userId, Status.DEFAULT);
        when(userMapper.findById(userId))
            .thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            adminService.unblockUser(userId);  // when
        });

        // then
        verify(userMapper, times(0)).updateUserStatus(any(Integer.class), eq(Status.DEFAULT));
    }

}
