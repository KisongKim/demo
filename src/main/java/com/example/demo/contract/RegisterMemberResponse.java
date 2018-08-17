package com.example.demo.contract;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

/**
 * Register member response payload
 *
 * @author Kisong
 */
@ApiModel(description = "Register member response payload")
@JsonAutoDetect
public class RegisterMemberResponse {

    @ApiModelProperty(example = "1", required = true, value = "member primary id")
    @NotNull
    @JsonProperty(required = true)
    private Long memberSeq;

    public static RegisterMemberResponse from(final Long memberSeq) {
        return new RegisterMemberResponse(memberSeq);
    }

    public RegisterMemberResponse() {
    }

    private RegisterMemberResponse(final Long memberSeq) {
        this.memberSeq = memberSeq;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public Long getMemberSeq() {
        return memberSeq;
    }

    public void setMemberSeq(Long memberSeq) {
        this.memberSeq = memberSeq;
    }

}
