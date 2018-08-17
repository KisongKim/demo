package com.example.demo.controller;

import com.example.demo.config.SecurityContext;
import com.example.demo.contract.RegisterMemberRequest;
import com.example.demo.contract.RegisterMemberResponse;
import com.example.demo.service.RegisterMemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * This test class how to test a controller using MockMvc loading a Test context
 *
 * <p>
 *     load only the controller layer of the application. This will scan only the @Controller/@RestController annotation
 *     and will not load the fully ApplicationContext. it would not take the security configurations.
 *     Therefore the main Spring Security Configuration class should be imported to Test Class.
 * </p>
 *
 * @author Kisong
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MemberController.class)
@Import(SecurityContext.class)  // https://github.com/spring-projects/spring-boot/issues/6514
public class MemberControllerWebMvcTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegisterMemberService registerMemberService;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.standaloneSetup(new MemberController(registerMemberService)).build();
    }

    @Test
    public void register() throws Exception {
        RegisterMemberRequest request = MemberControllerTestsUtils.toRequest("test@hanpass.com",
                DigestUtils.sha256Hex("p@assw0rd"));

        RegisterMemberResponse response = RegisterMemberResponse.from(1L);
        Mockito.when(registerMemberService.execute(Mockito.any())).thenReturn(response);

        mvc.perform(MockMvcRequestBuilders.post("/member/ns/v1")
                .with(SecurityMockMvcRequestPostProcessors.csrf())  // to prevent 403 response
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MemberControllerTestsUtils.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.memberSeq", Matchers.is(1)));
    }

    @Test
    public void register_invalidEmail() throws Exception {
        RegisterMemberRequest request = MemberControllerTestsUtils.toRequest("tester",
                DigestUtils.sha256Hex("p@assw0rd"));

        mvc.perform(MockMvcRequestBuilders.post("/member/ns/v1")
                .with(SecurityMockMvcRequestPostProcessors.csrf())  // to prevent 403 response
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MemberControllerTestsUtils.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void register_invalidPassword() throws Exception {
        RegisterMemberRequest request = MemberControllerTestsUtils.toRequest("test@hanpass.com", "plainPassword");

        mvc.perform(MockMvcRequestBuilders.post("/member/ns/v1")
                .with(SecurityMockMvcRequestPostProcessors.csrf())  // to prevent 403 response
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MemberControllerTestsUtils.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
