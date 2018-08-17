package com.example.demo.model.repository;

import com.example.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Account repository interface
 *
 * @author Kisong
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Find the account that match with account number and not deleted
     *
     * @param number account number
     * @return instance of Account
     */
    Account findByNumberAndDeletedFalse(final String number);

    /**
     * Find the all account that match with account number
     *
     * @param number account number
     * @return list of Account
     */
    List<Account> findAllByNumber(final String number);

    /**
     * Find the all account that match with member id
     *
     * @param id member id
     * @return list of Account
     */
    List<Account> findAllByMemberId(final Long id);

}
