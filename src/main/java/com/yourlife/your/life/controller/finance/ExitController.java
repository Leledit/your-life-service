package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.exit.ExitPostDTO;
import com.yourlife.your.life.model.dto.finance.exit.ExitPutDTO;
import com.yourlife.your.life.model.entity.finance.Exit;
import com.yourlife.your.life.service.finance.ExitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service/api/v1")
public class ExitController {

    @Autowired
    private ExitService exitService;

    @PostMapping(value = "/exit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Exit> saveExit(@RequestBody @Valid ExitPostDTO exitPostDTO){
        Exit exit = exitService.save(exitPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(exit);
    }

    @GetMapping(value = "/exit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Exit>> getAllExit(){
        List<Exit> exitList = exitService.findAllByUser();
        return ResponseEntity.status(HttpStatus.OK).body(exitList);
    }

    @PatchMapping(value = "/exit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Exit> updateExit(@PathVariable String id, @RequestBody ExitPutDTO exitPutDTO){
        Exit exit = exitService.update(id,exitPutDTO);
        return ResponseEntity.status(HttpStatus.OK).body(exit);
    }

    @DeleteMapping(value = "/exit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteExit(@PathVariable String id){
        exitService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}