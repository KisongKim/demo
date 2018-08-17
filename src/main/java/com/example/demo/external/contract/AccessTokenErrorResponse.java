package com.example.demo.external.contract;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * access token response when error occurred
 *
 * @author Kisong
 */
@JsonAutoDetect
public class AccessTokenErrorResponse {

    @JsonProperty(required = true, value = "error")
    private String error;

    @JsonProperty(required = true, value = "error_description")
    private String errorDescription;

    /**
     * instantiate the AccessTokenErrorResponse from json response.
     *
     * @param rawMessage error response message (json formatted)
     * @return instance of AccessTokenErrorResponse
     */
    public static AccessTokenErrorResponse from(final String rawMessage) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            return mapper.readValue(rawMessage, AccessTokenErrorResponse.class);
        } catch (Exception e) {
            return new AccessTokenErrorResponse("Unknown", "[from] mapper.readValue() exception.");
        }
    }

    public AccessTokenErrorResponse() {
    }

    private AccessTokenErrorResponse(final String error, final String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

}
