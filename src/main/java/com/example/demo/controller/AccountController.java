package com.example.demo.controller;

import com.example.demo.ServiceException;
import com.example.demo.contract.RegisterAccountRequest;
import com.example.demo.contract.RegisterAccountResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Account resource controller
 *
 * @author Kisong
 */
@RestController
public class AccountController {

    @PostMapping(path = "/account")
    @ResponseBody
    public RegisterAccountResponse register(@Valid @RequestBody RegisterAccountRequest request) throws ServiceException {
        return null;
    }

}
