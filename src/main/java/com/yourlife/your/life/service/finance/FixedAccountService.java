package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.FixedAccountDTO;
import com.yourlife.your.life.model.entity.finance.FixedAccount;
import com.yourlife.your.life.model.vo.finance.FixedAccountChangingVO;
import com.yourlife.your.life.model.vo.finance.FixedAccountRegisterVO;

import java.util.ArrayList;

public interface FixedAccountService {
    FixedAccountDTO createdFixedAccount(FixedAccount fixedAccount);
    ArrayList<FixedAccountDTO> returnRegisteredFixedAccounts();
    FixedAccountDTO returningAFixedAccountById(String id);
    FixedAccountDTO changingFixedAccount(FixedAccount fixedAccount);

    Void deletingAFixedAccount(String id);
}
