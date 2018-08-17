package com.example.demo.model.repository;

import com.example.demo.model.Bank;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BankRepositoryTests {

    @Autowired
    BankRepository bankRepository;

    @Test
    public void findByCode() {
        final String code = "003";
        Bank bank = bankRepository.findByCode(code);
        Assert.assertNotNull(bank);
        System.out.println("[findByCode] Bank=" + bank.toString());
    }

    @Test
    public void findByCodeAndDeletedFalse() {
        final String code = "001";
        Bank bank = bankRepository.findByCode(code);
        Assert.assertNotNull(bank);
        System.out.println("[findByCodeAndDeletedFalse] Bank=" + bank.toString());
    }

    @Test
    public void findBySwift() {
        final String code = "CZNBKRSE";
        Bank bank = bankRepository.findBySwift(code);
        Assert.assertNotNull(bank);
        Assert.assertTrue("004".equals(bank.getCode()));
        System.out.println("[findBySwift] Bank=" + bank.toString());
    }

    @Test
    public void findBySwiftAndDeletedFalse() {
        final String code = "CZNBKRSE";
        Bank bank = bankRepository.findBySwiftAndDeletedFalse(code);
        Assert.assertNotNull(bank);
        Assert.assertTrue("004".equals(bank.getCode()));
        System.out.println("[findBySwiftAndDeletedFalse] Bank=" + bank.toString());
    }

    @Test
    public void findAll() {
        Sort sort = new Sort(Sort.Direction.ASC, "displayOrder");
        List<Bank> banks = bankRepository.findAll(sort);
        Assert.assertTrue(!banks.isEmpty());
        Assert.assertTrue(banks.get(0).getDisplayOrder() == 1);
    }

}
