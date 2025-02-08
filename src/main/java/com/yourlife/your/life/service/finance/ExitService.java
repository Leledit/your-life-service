package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.exit.ExitPostDTO;
import com.yourlife.your.life.model.dto.finance.exit.ExitPutDTO;
import com.yourlife.your.life.model.entity.finance.Exit;

import java.util.List;

public interface ExitService {

    Exit save(ExitPostDTO exitPostDTO);
    List<Exit> findAllByUser();
    Exit update(String id, ExitPutDTO exitPutDTO);
    void delete(String id);
}
