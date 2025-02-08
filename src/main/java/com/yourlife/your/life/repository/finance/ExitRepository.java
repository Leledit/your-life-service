package com.yourlife.your.life.repository.finance;

import com.yourlife.your.life.model.entity.finance.Exit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ExitRepository extends MongoRepository<Exit,String> {

    Optional<List<Exit>> findAllByUser_IdAndDeleted(String id, Boolean deleted);

    Exit findByIdAndDeleted(String id, Boolean deleted);
}
