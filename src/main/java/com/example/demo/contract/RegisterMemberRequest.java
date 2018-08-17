package com.example.demo.contract;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Register member request payload
 *
 * @author Kisong
 */
@ApiModel(description = "Register member request payload")
@JsonAutoDetect
public class RegisterMemberRequest {

    @ApiModelProperty(example = "test@hanpass.com", required = true, value = "email address about to register")
    @Email
    @Size(max = 256)
    @JsonProperty(required = true)
    private String email;

    @ApiModelProperty(example = "z5364akeHg87Q9==", required = true, value = "SHA-256 hashed member password")
    @Size(min = 64, max = 64)
    @JsonProperty(required = true)
    private String password;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
