package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.entry.EntryPostDTO;
import com.yourlife.your.life.model.dto.finance.entry.EntryPutDTO;
import com.yourlife.your.life.model.entity.finance.Entry;
import com.yourlife.your.life.service.finance.EntryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service/api/v1")
public class EntryController {

    @Autowired
    private EntryService entryService;

    @PostMapping(value = "/entry", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Entry> saveEntry(@RequestBody @Valid EntryPostDTO entryPostDTO){
        Entry entry = entryService.save(entryPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(entry);
    }

    @GetMapping(value = "/entry", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Entry>> getAllEntryByUser(){
        List<Entry> entries = entryService.findAllByUser();
        return ResponseEntity.status(HttpStatus.OK).body(entries);
    }

    @PutMapping(value = "/entry/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Entry> updateEntry(@RequestBody EntryPutDTO entryPutDTO, @PathVariable String id){
        Entry entry = entryService.update(id,entryPutDTO);
        return ResponseEntity.status(HttpStatus.OK).body(entry);
    }

    @DeleteMapping(value = "/entry/{id}/deleted", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteEntry(@PathVariable String id){
        entryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
