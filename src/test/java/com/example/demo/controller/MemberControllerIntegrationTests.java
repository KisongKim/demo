package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.contract.RegisterMemberRequest;
import com.example.demo.contract.RegisterMemberResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void register() {
        RegisterMemberRequest request = MemberControllerTestsUtils.toRequest("test@hanpass.com",
                DigestUtils.sha256Hex("p@assw0rd"));

        ResponseEntity<RegisterMemberResponse> responseEntity = restTemplate.postForEntity("/member/ns/v1",
                request,
                RegisterMemberResponse.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody().getMemberSeq()).isNotNull();
    }
}
