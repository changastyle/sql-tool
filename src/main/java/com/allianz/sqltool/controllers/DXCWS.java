package com.allianz.sqltool.controllers;

import com.allianz.sqltool.model.DCXEntity;
import com.allianz.sqltool.repo.DcxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "dcx")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DXCWS
{
    @Autowired
    private DcxRepository dcxRepository;


    @GetMapping("/")
    public List<DCXEntity> findAll()
    {
        List<DCXEntity> arrDCX = new ArrayList<>();

        arrDCX = dcxRepository.findAll();

        return arrDCX;
    }
}
