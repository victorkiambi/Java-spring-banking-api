package com.saltpay.test.repositories;

import com.saltpay.test.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccId(Long id);
}
