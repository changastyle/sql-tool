package com.allianz.sqltool.service;


import com.allianz.sqltool.repo.DcxRepository;
import com.allianz.sqltool.model.DCXEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ConfToolServiceSearch {

    private static final Log LOG = LogFactory.getLog(ConfToolServiceSearch.class);

    @Autowired
    public DcxRepository dcxRepository;

    /**
     * Checks in the database if a specified dcx exists
     *
     * params:  Map<String, String>
     * returns: DCXEntity list
     */
    public List<DCXEntity> searchDcx(Map<String, String> httpRequest){

        List<DCXEntity> dcxEntityList = new ArrayList<>();
        try {
            dcxEntityList = dcxRepository.findByPartnerAndMarketAndSalesChannelAndLobAndFlowType(httpRequest.get("partner"), httpRequest.get("market"),
                    httpRequest.get("salesChannel"), httpRequest.get("lob"), httpRequest.get("flowType"));
        } catch (Exception e) {
            LOG.info("Error: " + e.getMessage());
        }
        return dcxEntityList;
    }

    /**
     * Checks in the database if a specified dcx exists
     *
     * params:  DCXEntity
     * returns: DCXEntity list
     */
    public List<DCXEntity> searchDCX(DCXEntity dcx) {
        List<DCXEntity> dcxEntityList = new ArrayList<DCXEntity>();
        try {
            dcxEntityList = dcxRepository.findByPartnerAndMarketAndSalesChannelAndLobAndFlowType(dcx.getPartner()
                    , dcx.getMarket(), dcx.getSalesChannel(), dcx.getLob(), dcx.getFlowType());
            return dcxEntityList;
        } catch (Exception e) {
            LOG.info("Error: " + e.getMessage());
            return dcxEntityList;
        }

    }
}
