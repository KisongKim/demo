package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = {
        @Index(columnList = "EMAIL", name = "MEMBER_IDX1")
})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL", length = 256, nullable = false, updatable = false)
    private String email;

    @Column(name = "PASSWORD", length = 64, nullable = false)
    private String password;

    @Column(name = "DELETED", nullable = false)
    private Boolean deleted;

    @Column(name = "DELETED_TIME")
    private LocalDateTime deletedTime;

    @Column(name = "CREATED_TIME", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    /**
     * static factory method of Member
     *
     * @param email email
     * @param password encrypted password
     * @return instance of member
     */
    public static Member of(final String email,
                            final String password) {
        Member member = new Member();
        member.email = email;
        member.password = password;
        member.deleted = false;
        member.createdTime = LocalDateTime.now();
        return member;
    }

    @PreRemove
    public void deleteMember() {
        deleted = true;
        deletedTime = LocalDateTime.now();
    }

}
