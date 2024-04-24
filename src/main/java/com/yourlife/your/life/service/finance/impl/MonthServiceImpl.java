package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.repository.finance.MonthRepository;
import com.yourlife.your.life.service.finance.MonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthServiceImpl implements MonthService {

    @Autowired
    private MonthRepository monthRepository;


    @Override
    public Month save(Month month) {
        return monthRepository.save(month);
    }

    @Override
    public Month findByMonth(Integer month, Integer year, String userId) {
        return monthRepository.findByYearAndMonthAndUser_Id(year,month,userId);
    }

    @Override
    public List<Month> getAll(String userId) {
        return monthRepository.findAllByUser_Id(userId).orElse(null);
    }

    @Override
    public Month findById(String id) {
        return monthRepository.findById(id).orElse(null);
    }
}
