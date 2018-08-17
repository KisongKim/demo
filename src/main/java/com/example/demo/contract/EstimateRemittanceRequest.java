package com.example.demo.contract;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author Kisong
 */
@JsonAutoDetect
public class EstimateRemittanceRequest {

    private String base;

    private String term;

    private String amount;



}
