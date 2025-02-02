package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.FixedAccount.FixedAccountPostDTO;
import com.yourlife.your.life.model.dto.finance.FixedAccount.FixedAccountPutDTO;
import com.yourlife.your.life.model.entity.finance.FixedAccount;
import java.util.ArrayList;

public interface FixedAccountService {
    FixedAccount save(FixedAccountPostDTO fixedAccountPostDTO);
    ArrayList<FixedAccount> findAll(String userId);
    FixedAccount findById(String id);
    FixedAccount update(String id, FixedAccountPutDTO fixedAccountPutDTO);
    void delete(String id);
}
