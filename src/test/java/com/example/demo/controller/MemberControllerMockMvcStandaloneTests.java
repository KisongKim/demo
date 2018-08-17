package com.example.demo.controller;

import com.example.demo.ServiceError;
import com.example.demo.ServiceException;
import com.example.demo.contract.RegisterMemberRequest;
import com.example.demo.contract.RegisterMemberResponse;
import com.example.demo.service.RegisterMemberService;
import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


/**
 * This class demonstrates how to test a controller using MockMVC with Standalone setup.
 *
 * @author Kisong
 */
@RunWith(MockitoJUnitRunner.class)
public class MemberControllerMockMvcStandaloneTests {

    private MockMvc mockMvc;

    @Mock
    private RegisterMemberService registerMemberService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MemberController(registerMemberService))
                .setControllerAdvice(new DemoServiceExceptionHandler())
                .build();
    }

    @Test
    public void register() throws Exception {
        RegisterMemberRequest request = MemberControllerTestsUtils.toRequest("test@hanpass.com",
                DigestUtils.sha256Hex("p@assw0rd"));

        RegisterMemberResponse response = RegisterMemberResponse.from(1L);
        Mockito.when(registerMemberService.execute(Mockito.any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/member/ns/v1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MemberControllerTestsUtils.toJson(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.memberSeq", Matchers.is(1)));
    }

    @Test
    public void register_alreadyExistEmail() throws Exception {
        RegisterMemberRequest request = MemberControllerTestsUtils.toRequest("test@hanpass.com",
                DigestUtils.sha256Hex("p@assw0rd"));

        ServiceException serviceException = new ServiceException(ServiceError.EMAIL_ALREADY_EXIST,
                new IllegalStateException());
        Mockito.when(registerMemberService.execute(Mockito.any())).thenThrow(serviceException);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/member/ns/v1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MemberControllerTestsUtils.toJson(request)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(response.getContentAsString()).contains("40001");
    }

}
