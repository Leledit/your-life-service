package com.yourlife.your.life.repository.finance;

import com.yourlife.your.life.model.entity.finance.FixedAccount;
import com.yourlife.your.life.model.entity.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface FinanceFixedAccountRepository extends MongoRepository<FixedAccount,String> {

    Optional<ArrayList<FixedAccount>> findAllByUser_Id(String id);
}
