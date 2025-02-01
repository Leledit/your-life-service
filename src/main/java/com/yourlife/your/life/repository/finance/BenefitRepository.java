package com.yourlife.your.life.repository.finance;


import com.yourlife.your.life.model.entity.finance.Benefit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitRepository extends MongoRepository<Benefit,String> {
}
