package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.FinanceFixedAccountDTO;
import com.yourlife.your.life.model.vo.finance.FinanceRegisterFixedAccountVO;

public interface FinanceService {

    FinanceFixedAccountDTO createdFixedAccount(FinanceRegisterFixedAccountVO financeRegisterFixedAccountVO);
}
