package com.yourlife.your.life.repository.finance;

import com.yourlife.your.life.model.entity.finance.FixedAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceFixedAccountRepository extends MongoRepository<FixedAccount,String> {
}
