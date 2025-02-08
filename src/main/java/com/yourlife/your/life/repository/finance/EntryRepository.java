package com.yourlife.your.life.repository.finance;

import com.yourlife.your.life.model.entity.finance.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface EntryRepository extends MongoRepository<Entry,String> {
    Optional<List<Entry>> findAllByUser_IdAndDeleted(String id, Boolean deleted);

    Entry findByIdAndDeleted(String id, Boolean deleted);
}
