package com.fincore.wallet.customer.service;

import com.fincore.wallet.auth.entity.User;
import com.fincore.wallet.auth.repository.UserRepository;
import com.fincore.wallet.customer.dto.CreateCustomerProfileRequest;
import com.fincore.wallet.customer.dto.CustomerProfileResponse;
import com.fincore.wallet.customer.entity.CustomerProfile;
import com.fincore.wallet.customer.repository.CustomerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final UserRepository userRepository;
    private final CustomerProfileRepository customerProfileRepository;

    public String createProfile(String email, CreateCustomerProfileRequest request){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (customerProfileRepository.findByUser(user).isPresent()){
            throw new RuntimeException("Customer profile already exists");
        }

        CustomerProfile profile = new CustomerProfile();

        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setBvn(request.getBvn());
        profile.setNin(request.getNin());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setAddress(request.getAddress());
        profile.setUser(user);

        customerProfileRepository.save(profile);

        return "Customer profile created successfully";
    }

    public CustomerProfileResponse getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        CustomerProfile profile =
                customerProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Customer profile not found"
                                ));

        return CustomerProfileResponse.builder()
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .phoneNumber(profile.getPhoneNumber())
                .bvn(profile.getBvn())
                .nin(profile.getNin())
                .dateOfBirth(profile.getDateOfBirth())
                .address(profile.getAddress())
                .kycStatus(profile.getKycStatus().name())
                .tierLevel(profile.getTierLevel().name())
                .build();
    }


}
