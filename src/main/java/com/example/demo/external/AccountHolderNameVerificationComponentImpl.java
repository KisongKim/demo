package com.example.demo.external;

import com.example.demo.ServiceError;
import com.example.demo.ServiceException;
import com.example.demo.external.contract.AccessTokenErrorResponse;
import com.example.demo.external.contract.AccessTokenResponse;
import com.example.demo.external.contract.AccountNameResponse;
import com.example.demo.external.contract.GeneralErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

/**
 * @author Kisong
 */
@Component
public class AccountHolderNameVerificationComponentImpl implements AccountHolderNameVerificationComponent {

    private RestTemplate restTemplate;

    @Value("${demo.external.base-uri}")
    private String baseUri;

    @Value("${demo.external.access-token.client-id}")
    private String clientId;

    @Value("${demo.external.access-token.client-secret}")
    private String clientSecret;

    @Value("${demo.external.access-token.grant-type}")
    private String grantType;

    @Value("${demo.external.access-token.scope}")
    private String scope;

    private static final Logger logger = LoggerFactory.getLogger(AccountHolderNameVerificationComponentImpl.class);

    @Autowired
    public AccountHolderNameVerificationComponentImpl(@Qualifier("openRestTemplate") final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String accessToken() throws ServiceException {
        URI uri = URI.create(String.format("%s/%s", baseUri, "auth/oauth/v2/token"));
        // request parameters
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("client_id", clientId);
        parameters.add("client_secret", clientSecret);
        parameters.add("grant_type", grantType);
        parameters.add("scope", scope);
        // request headers
        MultiValueMap<String, String> headers = headers(null,true, null, null);

        AccessTokenResponse response;
        try {
            response = restTemplate.postForObject(uri, new HttpEntity<>(parameters, headers), AccessTokenResponse.class);
        } catch (HttpStatusCodeException e) {
            // received error response
            logger.error("[accessToken] Error status={}, response={}",
                    e.getStatusCode(), AccessTokenErrorResponse.from(e.getResponseBodyAsString()));
            throw new ServiceException(ServiceError.EXTERNAL_SYSTEM_ERROR, new IllegalStateException());
        } catch (RestClientException e) {
            // error without response message.
            logger.error("[accessToken] Error={}", e);
            throw new ServiceException(ServiceError.EXTERNAL_SYSTEM_ERROR, e);
        }
        return response.getAccessToken();
    }

    @Override
    public Optional<String> execute(final String bankCode, final String accountNumber, final String holderName) throws ServiceException {
        /*
        // Get access token.
        String accessToken = accessToken();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(URI.create(String.format("%s/%s", baseUri, "api/v1.1/bankaccount/name")))
                .queryParam("institution", bankCode)
                .queryParam("no", accountNumber);
        URI uri = builder.build().encode().toUri();

        String signatureUri = signatureUri(HttpMethod.GET, uri, baseUri, clientSecret);
        HttpEntity<?> entity = new HttpEntity<>(headers(accessToken, false, signatureUri.get(), null));

        ResponseEntity<AccountNameResponse> responseEntity;
        try {
            responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, AccountNameResponse.class);
        } catch (HttpStatusCodeException e) {
            // received error response
            logger.error("[execute] Error status={}, response={}",
                    e.getStatusCode(), GeneralErrorResponse.from(e.getResponseBodyAsString()));
            throw new ServiceException(ServiceError.EXTERNAL_SYSTEM_ERROR, new IllegalStateException());
        } catch (RestClientException e) {
            // error without response message.
            logger.error("[execute] Error={}", e);
            throw new ServiceException(ServiceError.EXTERNAL_SYSTEM_ERROR, e);
        }
        return Optional.ofNullable(responseEntity.getBody().getDepositor());
        */

        // in demo application we can not inter-working with external system (security reason). just return holder name
        return Optional.ofNullable(holderName);
    }

    private static MultiValueMap<String, String> headers(final String authorizationToken,
                                                         final boolean formType,
                                                         final String signatureUri,
                                                         final String signatureBody) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        if (authorizationToken != null) {
            headers.add("Authorization", String.format("Bearer %s", authorizationToken));
        }
        if (signatureUri != null) {
            headers.add("x-obp-signature-url", signatureUri);
        }
        if (signatureBody != null) {
            headers.add("x-obp-signature-body", signatureBody);
        }
        if (formType) {
            headers.add("Content-Type", "application/x-www-form-urlencoded");
        }
        return headers;
    }

    /**
     * calculate the request uri signature
     *
     * @param method request method
     * @param uri request uri
     * @param baseUri base request uri, pre-defined in application.properties
     * @param clientSecret client secret, pre-defined in application.properties
     * @return signature
     */
    private static String signatureUri(final HttpMethod method,
                                                 final URI uri,
                                                 final String baseUri,
                                                 final String clientSecret) {
        String stringToSign = String.format("%s&%s", method.toString(), uri.toString().substring(baseUri.length()));
        return hmacSha256(stringToSign, clientSecret);
    }

    /**
     * apply hmacSha256 to data
     *
     * @param data about to apply hmacSha256
     * @param key secret key
     * @return hmacSha256 applied data
     */
    private static String hmacSha256(final String data, final String key) {
        try {
            if (data == null) {
                return null;
            }
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
            return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes("UTF-8")));
        } catch (Exception e) {
            logger.error("[hmacSha256] Error", e);
            return null;
        }
    }

}
