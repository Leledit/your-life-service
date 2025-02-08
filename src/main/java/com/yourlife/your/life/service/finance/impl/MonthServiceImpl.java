package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.Month.MonthAddFixedAccountDTO;
import com.yourlife.your.life.model.entity.finance.FixedAccount;
import com.yourlife.your.life.model.entity.finance.Installment;
import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.repository.finance.FixedAccountRepository;
import com.yourlife.your.life.repository.finance.InstallmentRepository;
import com.yourlife.your.life.repository.finance.MonthRepository;
import com.yourlife.your.life.service.finance.MonthService;
import com.yourlife.your.life.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class MonthServiceImpl implements MonthService {

    @Autowired
    private MonthRepository monthRepository;

    @Autowired
    private UserContext userContext;

    @Autowired
    private FixedAccountRepository fixedAccountRepository;

    @Autowired
    private InstallmentRepository installmentRepository;

    //TODO: Verificar SE O MES JA TA CADASTRO E VALIDAR
    @Override
    public Month save() {

        LocalDateTime currentDate = LocalDateTime.now();

        List<Installment> installmentList = installmentRepository.findByFirstInstallmentDateLessThanEqualAndLastInstallmentDateGreaterThanEqualAndDeleted(currentDate, currentDate,false);

        Month month = new Month();
        month.setName(currentDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));
        month.setDate(currentDate);
        month.setMonth(currentDate.getMonthValue());
        month.setYear(currentDate.getYear());
        month.setUser(userContext.returnUserCorrespondingToTheRequest());
        month.setEntry(new ArrayList<>());
        month.setBenefitItems(new ArrayList<>());
        month.setInstallments(installmentList);
        month.setFixedAccounts(new ArrayList<>());
        month.setExits(new ArrayList<>());

        return monthRepository.save(month);
    }

    @Override
    public Month findByMonth(Integer month, Integer year, String userId) {
        return monthRepository.findByYearAndMonthAndUser_Id(year,month,userId);
    }

    @Override
    public List<Month> findAll(String userId) {
        return monthRepository.findAllByUser_Id(userId).orElse(null);
    }

    @Override
    public Month findById(String id) {
        return findMonthById(id);
    }

    @Override
    public Month addFixedAccount(String id, MonthAddFixedAccountDTO monthAddFixedAccountDTO) {
        Month month = findMonthById(id);

        List<FixedAccount> fixedAccounts = fixedAccountRepository.findAllById(monthAddFixedAccountDTO.getFixedAccountList());

        if(!fixedAccounts.isEmpty()){
            List<FixedAccount> fixedAccountsMonth = month.getFixedAccounts();

            if(!fixedAccountsMonth.isEmpty()){
                List<FixedAccount> newFixedAccount = fixedAccounts.stream().filter((fixedAccount -> !fixedAccountsMonth.contains(fixedAccount))).toList();
                fixedAccountsMonth.addAll(newFixedAccount);
            }else{
                fixedAccountsMonth.addAll(fixedAccounts);
            }

            month.setFixedAccounts(fixedAccountsMonth);
            monthRepository.save(month);
        }

        return month;
    }

    private Month findMonthById(String id){
        Month month = monthRepository.findById(id).orElse(null);

        if(month==null){
            throw new RuntimeException(ExceptionMessages.MONTH_NOT_FOUND);
        }

        return month;
    }

}
