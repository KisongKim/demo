package com.example.demo.controller;

import com.example.demo.contract.RegisterMemberRequest;
import com.example.demo.contract.RegisterMemberResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;

/**
 * MemberController tests utils package private utils.
 *
 * @author Kisong
 */
class MemberControllerTestsUtils {

    /**
     * convert instance of RegisterMemberRequest to json formatted string.
     *
     * @param request instance of RegisterMemberRequest
     * @return json formatted request
     */
    static String toJson(final RegisterMemberRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(request);
        } catch (Exception e) {
            Assert.fail("[toJson] Exception e");
            return null;
        }
    }

    /**
     * convert instance of RegisterMemberResponse to json formatted string.
     *
     * @param response instance of RegisterMemberResponse
     * @return json formatted response
     * @throws Exception when error occurred
     */
    static String toJson(final RegisterMemberResponse response) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(response);
    }

    /**
     * instantiate the RegisterMemberRequest
     *
     * @param email email address
     * @param password SHA-256 hashed password
     * @return instance of RegisterMemberRequest
     */
    static RegisterMemberRequest toRequest(final String email, final String password) {
        RegisterMemberRequest request = new RegisterMemberRequest();
        request.setEmail(email);
        request.setPassword(password);
        return request;
    }

}
