package com.fincore.wallet.customer.entity;

import com.fincore.wallet.auth.entity.User;
import com.fincore.wallet.common.entity.BaseEntity;
import com.fincore.wallet.customer.enums.KycStatus;
import com.fincore.wallet.customer.enums.TierLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "customer_profiles")
public class CustomerProfile extends BaseEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String bvn;

    @Column(unique = true)
    private String nin;

    private LocalDate dateOfBirth;

    private String address;

    @Enumerated(EnumType.STRING)
    private KycStatus kycStatus = KycStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private TierLevel tierLevel = TierLevel.TIER_1;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
