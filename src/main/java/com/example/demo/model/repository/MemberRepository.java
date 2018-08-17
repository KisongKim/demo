package com.example.demo.model.repository;

import com.example.demo.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Member repository interface
 *
 * @author Kisong
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * Find the member that match with email address and not deleted.
     *
     * @param email email address
     * @return instance of Member
     */
    Member findByEmailAndDeletedFalse(final String email);

    /**
     * Find the member with primary key.
     *
     * @param id member primary key
     * @return instance of Member
     */
    Member findByIdAndDeletedTrue(final Long id);

    /**
     * Find all the member information that matches email address.
     *
     * @param email email address
     * @return list of Member
     */
    List<Member> findAllByEmailAndDeletedTrue(final String email);

}
