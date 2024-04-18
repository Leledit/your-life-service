package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.FinanceFixedAccountDTO;
import com.yourlife.your.life.model.vo.finance.FinanceChangingFixedAccountVO;
import com.yourlife.your.life.model.vo.finance.FinanceRegisterFixedAccountVO;

import java.util.ArrayList;

public interface FixedAccountService {
    FinanceFixedAccountDTO createdFixedAccount(FinanceRegisterFixedAccountVO financeRegisterFixedAccountVO);
    ArrayList<FinanceFixedAccountDTO> returnRegisteredFixedAccounts();
    FinanceFixedAccountDTO returningAFixedAccountById(String id);
    FinanceFixedAccountDTO changingFixedAccount(FinanceChangingFixedAccountVO financeChangingFixedAccountVO);

    Void deletingAFixedAccount(String id);
}
