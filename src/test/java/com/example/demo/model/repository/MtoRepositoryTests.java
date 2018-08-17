package com.example.demo.model.repository;

import com.example.demo.model.Mto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MtoRepositoryTests {

    @Autowired
    private MtoRepository mtoRepository;

    @Test
    public void findByCode() {
        List<Mto> matches = mtoRepository.findByCode("FINNET");
        Assert.assertTrue(!matches.isEmpty());
    }

    @Test
    public void deleteByCode() {
        List<Mto> initMto = mtoRepository.findAll();

        Mto toDelete = initMto.get(0);
        mtoRepository.deleteByCode(toDelete.getCode());

        List<Mto> current = mtoRepository.findAll();

        Assert.assertTrue(current.size() != initMto.size());
        System.out.println("[deleteByCode] Before=" + initMto.size() + " Current=" + current.size());
    }

    @Test
    public void findAll() {
        List<Mto> founds = mtoRepository.findAll();
        Assert.assertTrue(!founds.isEmpty());
        for (Mto mto : founds) {
            System.out.println("[findAll] Mto=" + mto.toString());
        }
    }

}
