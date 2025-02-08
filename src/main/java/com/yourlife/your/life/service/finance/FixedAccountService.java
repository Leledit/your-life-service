package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.FixedAccount.FixedAccountPostDTO;
import com.yourlife.your.life.model.dto.finance.FixedAccount.FixedAccountPutDTO;
import com.yourlife.your.life.model.entity.finance.FixedAccount;
import java.util.ArrayList;
import java.util.List;

public interface FixedAccountService {
    FixedAccount save(FixedAccountPostDTO fixedAccountPostDTO);
    List<FixedAccount> findAllByUser();
    FixedAccount findById(String id);
    FixedAccount update(String id, FixedAccountPutDTO fixedAccountPutDTO);
    void delete(String id);
}
