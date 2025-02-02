package com.yourlife.your.life.repository.finance;

import com.yourlife.your.life.model.entity.finance.Exit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExitRepository extends MongoRepository<Exit,String> {
}
