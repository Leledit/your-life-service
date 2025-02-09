package com.yourlife.your.life.repository.finance;

import com.yourlife.your.life.model.entity.finance.BenefitItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BenefitItemReposity extends MongoRepository<BenefitItem,String> {
    Optional<List<BenefitItem>> findAllByUser_IdAndDeleted(String id, Boolean deleted);

    Optional<BenefitItem> findByIdAndDeleted(String id, Boolean deleted);
}
