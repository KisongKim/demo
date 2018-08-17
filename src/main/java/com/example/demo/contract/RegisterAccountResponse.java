package com.example.demo.contract;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

/**
 * Register account response payload.
 *
 * @author Kisong
 */
@ApiModel(description = "Register account respone payload.")
@JsonAutoDetect
public class RegisterAccountResponse {

    @ApiModelProperty(example = "10", required = true, value = "account primary id")
    @NotNull
    @JsonProperty(required = true)
    private Long accountSeq;

    public static RegisterAccountResponse from(final Long accountSeq) {
        return new RegisterAccountResponse(accountSeq);
    }

    public RegisterAccountResponse() {
    }

    private RegisterAccountResponse(final Long accountSeq) {
        this.accountSeq = accountSeq;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public Long getAccountSeq() {
        return accountSeq;
    }

    public void setAccountSeq(Long accountSeq) {
        this.accountSeq = accountSeq;
    }

}
