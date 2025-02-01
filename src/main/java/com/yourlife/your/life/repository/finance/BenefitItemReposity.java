package com.yourlife.your.life.repository.finance;

import com.yourlife.your.life.model.entity.finance.BenefitItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitItemReposity extends MongoRepository<BenefitItem,String> {
}
