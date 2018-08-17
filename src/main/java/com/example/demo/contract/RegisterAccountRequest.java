package com.example.demo.contract;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Account register request payload.
 *
 * @author Kisong
 */
@ApiModel(description = "Account register request payload.")
@JsonAutoDetect
public class RegisterAccountRequest {

    @ApiModelProperty(example = "1", required = true, value = "member primary id")
    @NotNull
    @JsonProperty(required = true)
    private Long memberId;

    @ApiModelProperty(example = "030", required = true, value = "bank code (domestic)")
    @NotEmpty
    @Size(min = 3, max = 3)
    @JsonProperty(required = true)
    private String bankCode;

    @ApiModelProperty(example = "1234567890123456", required = true, value = "account number about to register")
    @NotEmpty
    @Size(min = 16, max = 16)
    @JsonProperty(required = true)
    private String number;

    @ApiModelProperty(example = "John Doe", required = true, value = "account holder name.")
    @NotEmpty
    @JsonProperty(required = true)
    private String holderName;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

}
