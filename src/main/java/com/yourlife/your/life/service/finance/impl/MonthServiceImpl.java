package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
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
    private InstallmentRepository installmentRepository;

    @Autowired
    private FixedAccountRepository fixedAccountRepository;

    @Override
    public Month save() {
        LocalDateTime currentDate = LocalDateTime.now();

        Month monthFound = monthRepository.findByYearAndMonthAndUser_Id(currentDate.getYear(),currentDate.getMonthValue(),userContext.returnUserCorrespondingToTheRequest().getId()).orElse(null);

        if(monthFound != null){
            throw new RuntimeException(ExceptionMessages.MONT_ALREADY_REGISTERED);
        }

        List<Installment> installmentList = installmentRepository.findByFirstInstallmentDateLessThanEqualAndLastInstallmentDateGreaterThanEqualAndDeleted(currentDate, currentDate,false);

        List<FixedAccount> fixedAccounts = fixedAccountRepository.findAllByUser_IdAndDeleted(userContext.returnUserCorrespondingToTheRequest().getId(),false).orElse(new ArrayList<>());

        Month month = new Month();
        month.setName(currentDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));
        month.setDate(currentDate);
        month.setMonth(currentDate.getMonthValue());
        month.setYear(currentDate.getYear());
        month.setUser(userContext.returnUserCorrespondingToTheRequest());
        month.setEntry(new ArrayList<>());
        month.setBenefitItems(new ArrayList<>());
        month.setInstallments(installmentList);
        month.setFixedAccounts(fixedAccounts);
        month.setExits(new ArrayList<>());

        return monthRepository.save(month);
    }

    @Override
    public Month findByMonth(Integer month, Integer year) {
        Month monthData = monthRepository.findByYearAndMonthAndUser_Id(year,month,userContext.returnUserCorrespondingToTheRequest().getId()).orElse(null);

        if(monthData == null){
            throw new RuntimeException(ExceptionMessages.MONTH_NOT_FOUND);
        }

        return monthData;
    }

    @Override
    public List<Month> findAll() {
        return monthRepository.findAllByUser_Id(userContext.returnUserCorrespondingToTheRequest().getId()).orElse(null);
    }

    @Override
    public Month findById(String id) {
        return findMonthById(id);
    }

    private Month findMonthById(String id){
        Month month = monthRepository.findById(id).orElse(null);

        if(month==null){
            throw new RuntimeException(ExceptionMessages.MONTH_NOT_FOUND);
        }

        return month;
    }
}