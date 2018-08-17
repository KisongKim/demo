package com.example.demo.controller;

import com.example.demo.ServiceException;
import com.example.demo.contract.ErrorResponse;
import com.example.demo.contract.RegisterMemberRequest;
import com.example.demo.contract.RegisterMemberResponse;
import com.example.demo.service.RegisterMemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Member resource controller
 *
 * @author Kisong
 */
@RestController
public class MemberController {

    private RegisterMemberService registerMemberService;

    @Autowired
    public MemberController(final RegisterMemberService registerMemberService) {
        this.registerMemberService = registerMemberService;
    }

    /**
     * Register new member into demo-remittance service.
     *
     * @param request instance of RegisterMemberRequest
     * @return instance of RegisterMemberResponse
     * @throws ServiceException error occurred
     */
    @ApiOperation(value = "Register new member into demo-remittance service.",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = RegisterMemberResponse.class),
            @ApiResponse(code = 400, message = RegisterMemberService.EXECUTE_400_SERIES, response = ErrorResponse.class),
            @ApiResponse(code = 500, message = RegisterMemberService.EXECUTE_500_SERIES, response = ErrorResponse.class)
    })
    @PostMapping(path = "member/ns/v1")
    @ResponseBody
    public RegisterMemberResponse register(@Valid @RequestBody RegisterMemberRequest request) throws ServiceException {
        return registerMemberService.execute(request);
    }

}
