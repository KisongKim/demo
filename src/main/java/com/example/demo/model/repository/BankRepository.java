package com.example.demo.model.repository;

import com.example.demo.model.Bank;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Bank repository interface
 *
 * @author Kisong
 */
@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    /**
     * Find the bank by domestic bank code
     *
     * @param code bank code
     * @return instance of Bank
     */
    Bank findByCode(final String code);

    /**
     * Find the bank by domestic bank code and deleted false
     *
     * @param code bank code
     * @return instance of Bank
     */
    Bank findByCodeAndDeletedFalse(final String code);

    /**
     * Find the bank by international swift code
     *
     * @param swift swift code
     * @return instance of Bank
     */
    Bank findBySwift(final String swift);

    /**
     * Find the bank by international swift code and deleted false
     *
     * @param swift swift code
     * @return instance of Bank
     */
    Bank findBySwiftAndDeletedFalse(final String swift);

    /**
     * Find all the banks with sort
     *
     * <p>
     *     Usually set DisplayOrder in ascending order. example {@code new Sort(Sort.Direction.ASC, "displayOrder")}
     * </p>
     *
     * @param sort instance of Sort
     * @return list of Bank
     */
    List<Bank> findAll(Sort sort);

}
