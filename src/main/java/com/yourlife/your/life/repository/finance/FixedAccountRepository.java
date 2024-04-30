package com.yourlife.your.life.repository.finance;

import com.yourlife.your.life.model.entity.finance.FixedAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface FixedAccountRepository extends MongoRepository<FixedAccount,String> {

    Optional<ArrayList<FixedAccount>> findAllByUser_IdAndDeleted(String id,Boolean deleted);
}
