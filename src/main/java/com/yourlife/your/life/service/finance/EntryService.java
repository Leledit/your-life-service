package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.entry.EntryPostDTO;
import com.yourlife.your.life.model.dto.finance.entry.EntryPutDTO;
import com.yourlife.your.life.model.entity.finance.Entry;

import java.util.List;

public interface EntryService {

    Entry save(EntryPostDTO entryPostDTO);
    Entry update(String id, EntryPutDTO entryPutDTO);
    List<Entry> findAllByUser();
    void delete(String id);

}
