package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.FixedAccountDTO;
import com.yourlife.your.life.model.entity.finance.FixedAccount;
import com.yourlife.your.life.model.vo.finance.FixedAccountChangingVO;
import com.yourlife.your.life.model.vo.finance.FixedAccountRegisterVO;

import java.util.ArrayList;

public interface FixedAccountService {
    FixedAccount save(FixedAccount fixedAccount);
    ArrayList<FixedAccount> getAll(String userId);
    FixedAccount getById(String id);
}
