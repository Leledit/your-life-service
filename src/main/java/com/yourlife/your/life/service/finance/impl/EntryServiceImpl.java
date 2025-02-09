package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.entry.EntryPostDTO;
import com.yourlife.your.life.model.dto.finance.entry.EntryPutDTO;
import com.yourlife.your.life.model.entity.finance.Entry;
import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.repository.finance.EntryRepository;
import com.yourlife.your.life.repository.finance.MonthRepository;
import com.yourlife.your.life.service.finance.EntryService;
import com.yourlife.your.life.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EntryServiceImpl implements EntryService {

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private MonthRepository monthRepository;

    @Autowired
    private UserContext userContext;

    @Override
    public Entry save(EntryPostDTO entryPostDTO) {

        LocalDateTime currentDate = LocalDateTime.now();
        Entry entry = entryRepository.save(Entry
                        .builder()
                        .name(entryPostDTO.getName())
                        .value(entryPostDTO.getValue())
                        .description(entryPostDTO.getDescription())
                        .createdAt(currentDate)
                        .deleted(false)
                        .user(userContext.returnUserCorrespondingToTheRequest())
                        .build());

        Month curretMonth = monthRepository.findByYearAndMonthAndUser_Id(currentDate.getYear(),currentDate.getMonthValue(),userContext.returnUserCorrespondingToTheRequest().getId()).orElse(null);
        if(curretMonth != null){
            List<Entry> entries = curretMonth.getEntry();
            entries.add(entry);
            curretMonth.setEntry(entries);
            monthRepository.save(curretMonth);
        }

        return entry;
    }

    @Override
    public Entry update(String id, EntryPutDTO entryPutDTO) {
        Entry entry = getEntryById(id);

        entry.setName(entryPutDTO.getName() != null ? entryPutDTO.getName() : entry.getName());
        entry.setValue(entryPutDTO.getValue() != null ? entryPutDTO.getValue() : entry.getValue());
        entry.setDescription(entryPutDTO.getDescription() != null ? entryPutDTO.getDescription() : entry.getDescription());
        entry.setUpdatedAt(LocalDateTime.now());

        return entryRepository.save(entry);
    }

    @Override
    public List<Entry> findAllByUser() {
        return entryRepository.findAllByUser_IdAndDeleted(userContext.returnUserCorrespondingToTheRequest().getId(),false).orElse(null);
    }

    @Override
    public void delete(String id) {
        Entry entry = getEntryById(id);

        entry.setDeleted(true);
        entry.setDeletedAt(LocalDateTime.now());

        entryRepository.save(entry);
    }

    private Entry getEntryById(String id){
        Entry entry = entryRepository.findByIdAndDeleted(id,false).orElse(null);

        if(entry == null){
            throw new RuntimeException(ExceptionMessages.ENTRY_NOT_FOUND);
        }

        return entry;
    }
}
