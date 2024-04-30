package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.entity.finance.Month;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface MonthService {
    Month save(Month month);
    Month findByMonth(Integer month, Integer year,String userId);
    List<Month> getAll(String userId);
    Month findById(String id);
}
