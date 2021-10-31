package com.saltpay.test.repositories;

import com.saltpay.test.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccNo(Long id);

    Account getAccountByAccNo(Long id);
}
