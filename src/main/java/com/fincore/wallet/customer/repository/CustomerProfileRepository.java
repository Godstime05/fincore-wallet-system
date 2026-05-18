package com.fincore.wallet.customer.repository;

import com.fincore.wallet.auth.entity.User;
import com.fincore.wallet.customer.entity.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {

    Optional<CustomerProfile> findByUser(User user);
}
