package com.example.demo.external;

import com.example.demo.ServiceException;

import java.util.Optional;

/**
 * This component provides the account holder name verification with external system.
 *
 * <p>
 *     In the this application, do not perform any actual verification but return immediately the account holder name.
 * </p>
 *
 * @author Kisong
 */
public interface AccountHolderNameVerificationComponent extends AccessTokenRequired {

    /**
     * Lookup the account holder name. if account and holder name mismatch returns Optional.empty
     *
     * @param bankCode bank code
     * @param accountNumber account number about to verification
     * @param holderName account holder name to verification
     * @return matched holder name
     * @throws ServiceException error occurred.
     */
    Optional<String> execute(final String bankCode, final String accountNumber, final String holderName) throws ServiceException;

}
