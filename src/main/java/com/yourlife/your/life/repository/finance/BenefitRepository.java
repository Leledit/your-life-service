package com.yourlife.your.life.repository.finance;

import com.yourlife.your.life.model.entity.finance.Benefit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface BenefitRepository extends MongoRepository<Benefit,String> {
    Optional<ArrayList<Benefit>> findAllByUser_IdAndDeleted(String id, Boolean deleted);

    Optional<Benefit> findByIdAndDeleted(String id, Boolean deleted);
}
