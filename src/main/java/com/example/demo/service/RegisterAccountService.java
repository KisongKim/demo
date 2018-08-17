package com.example.demo.service;

import com.example.demo.ServiceException;
import com.example.demo.contract.RegisterAccountRequest;
import com.example.demo.contract.RegisterAccountResponse;

/**
 * This service defines the account registration function.
 *
 * @author Kisong
 */
public interface RegisterAccountService {

    /**
     * Register new account.
     *
     * @param request instance of RegisterAccountRequest
     * @return instance of RegisterAccountResponse
     * @throws ServiceException error occurred
     */
    RegisterAccountResponse execute(final RegisterAccountRequest request) throws ServiceException;

}
