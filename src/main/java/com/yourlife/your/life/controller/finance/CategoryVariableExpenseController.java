package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.vo.finance.CategoryVariableExpenseRegisterVO;
import com.yourlife.your.life.utils.Logger;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service/api/v1")
public class CategoryVariableExpenseController {


    @PostMapping(value = "/accounts/category/expense",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> registerNewCategory(@RequestBody @Valid CategoryVariableExpenseRegisterVO categoryVariableExpenseRegisterVO){

        Logger.message("OKKK ENTREI NA REQUISIÇÃO");
        Logger.message(categoryVariableExpenseRegisterVO);
        return ResponseEntity.noContent().build();
    }
}
