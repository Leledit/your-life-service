package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.entity.finance.FixedAccount;
import java.util.ArrayList;

public interface FixedAccountService {
    FixedAccount save(FixedAccount fixedAccount);
    ArrayList<FixedAccount> getAll(String userId);
    FixedAccount getById(String id);
}
