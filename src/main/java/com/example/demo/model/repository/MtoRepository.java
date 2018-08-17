package com.example.demo.model.repository;

import com.example.demo.model.Mto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Kisong
 */
@Repository
public interface MtoRepository extends JpaRepository<Mto, Long> {

    /**
     * Find the Mto by Code
     *
     * @param code code
     * @return list of Mto
     */
    List<Mto> findByCode(final String code);

    /**
     * Delete (soft delete) the Mto by Code
     *
     * @param code code
     */
    @Modifying
    @Query("UPDATE Mto SET deleted = TRUE WHERE code = :code")
    void deleteByCode(@Param("code") final String code);

    /**
     * Find the all Mto
     *
     * @return list of Mto
     */
    @Override
    List<Mto> findAll();

}
