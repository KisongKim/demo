package com.example.demo.external;

import com.example.demo.ServiceException;

/**
 * An external inter-working component that implements/extends this interface must acquire an AccessToken before inter-working.
 *
 * @author Kisong
 */
public interface AccessTokenRequired {

    /**
     * Gets the oauth2 access token
     *
     * @return access token
     * @throws ServiceException error occurred
     */
    String accessToken() throws ServiceException;

}
