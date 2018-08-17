package com.example.demo.service;

import com.example.demo.ServiceException;
import com.example.demo.contract.RegisterMemberRequest;
import com.example.demo.contract.RegisterMemberResponse;

/**
 * This service defines the member registration function.
 *
 * @author Kisong
 */
public interface RegisterMemberService {

    String EXECUTE_400_SERIES = "id: 40000, description: Provided argument validation error.</br>"
            + "id: 40001, description: Provided email address already exist.";

    String EXECUTE_500_SERIES = "id: 50000, description: Internal server error occurred.";

    /**
     * Register a new member
     *
     * @param request instance of RegisterMemberRequest
     * @return instance of RegisterMemberResponse
     * @throws ServiceException when error occurred
     */
    RegisterMemberResponse execute(final RegisterMemberRequest request) throws ServiceException;

}
