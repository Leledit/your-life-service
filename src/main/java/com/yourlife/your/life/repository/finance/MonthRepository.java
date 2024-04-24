package com.yourlife.your.life.repository.finance;

import com.yourlife.your.life.model.entity.finance.Installment;
import com.yourlife.your.life.model.entity.finance.Month;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface MonthRepository extends MongoRepository<Month,String> {
    Month findByYearAndMonthAndUser_Id(Integer year, Integer month,String id);

    Optional<ArrayList<Month>> findAllByUser_Id(String id);
}
