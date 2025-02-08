package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.entity.finance.Month;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface MonthService {
    Month save();
    Month findByMonth(Integer month, Integer year);
    List<Month> findAll();
    Month findById(String id);
}
