package com.example.demo.external.contract;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * external system error response except accessToken
 *
 * @author Kisong
 */
@JsonAutoDetect
public class GeneralErrorResponse {

    @JsonProperty(required = true, value = "code")
    private String code;

    @JsonProperty(required = true, value = "message")
    private String message;

    @JsonProperty(value = "cause")
    private String cause;

    /**
     * instantiate the AccessTokenErrorResponse from json response.
     *
     * @param rawMessage error response message (json formatted)
     * @return instance of AccessTokenErrorResponse
     */
    public static GeneralErrorResponse from(final String rawMessage) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            return mapper.readValue(rawMessage, GeneralErrorResponse.class);
        } catch (Exception e) {
            return new GeneralErrorResponse("Unknown",
                    "[from] mapper.readValue() exception.",
                    e.getMessage());
        }
    }

    public GeneralErrorResponse() {
    }

    private GeneralErrorResponse(final String code, final String message, final String cause) {
        this.code = code;
        this.message = message;
        this.cause = cause;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

}
