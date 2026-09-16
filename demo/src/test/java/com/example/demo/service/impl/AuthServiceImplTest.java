package com.example.demo.service.impl;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    private LoginRequest request;

    @BeforeEach
    void setUp() {
        request = LoginRequest.builder()
                .username("john")
                .password("password123")
                .build();
    }
    @Test
    void login_validCredentials_returnsUserResponse() {
        // Constructor 3 tham số của UsernamePasswordAuthenticationToken tạo
        // ra 1 Authentication đã ở trạng thái authenticated = true - giả lập
        // đúng những gì AuthenticationManager.authenticate() trả về khi
        // thành công.
        Authentication authenticated = new UsernamePasswordAuthenticationToken("john", null, List.of());
        when(authenticationManager.authenticate(any())).thenReturn(authenticated);

        User user = User.builder()
                .id(1L)
                .username("john")
                .email("john@example.com")
                .role(Role.USER)
                .build();
        when(userRepository.findByusername("john")).thenReturn(Optional.of(user));

        UserResponse response = authService.login(request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getUsername()).isEqualTo("john");
    }
    @Test
    void login_invalidCredentials_throwsAppExceptionInsteadOfLeakingReason() {
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(AppException.class)
                .extracting(ex -> ((AppException) ex).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_CREDENTIALS);
    }
}
