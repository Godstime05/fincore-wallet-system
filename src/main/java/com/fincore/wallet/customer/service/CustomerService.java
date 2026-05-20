package com.fincore.wallet.customer.service;

import com.fincore.wallet.auth.entity.User;
import com.fincore.wallet.auth.repository.UserRepository;
import com.fincore.wallet.common.utils.NumberGeneratorUtil;
import com.fincore.wallet.customer.dto.CreateCustomerProfileRequest;
import com.fincore.wallet.customer.dto.CustomerProfileResponse;
import com.fincore.wallet.customer.dto.UpdateCustomerProfileRequest;
import com.fincore.wallet.customer.entity.CustomerProfile;
import com.fincore.wallet.customer.repository.CustomerProfileRepository;
import com.fincore.wallet.exception.BusinessException;
import com.fincore.wallet.wallet.entity.Wallet;
import com.fincore.wallet.wallet.repository.WalletRepository;
import com.fincore.wallet.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final UserRepository userRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private final WalletService walletService;
    private final WalletRepository walletRepository;

//    public String createProfile(String email, CreateCustomerProfileRequest request){
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new BusinessException("User not found"));
//
//        if (customerProfileRepository.findByUser(user).isPresent()){
//            throw new BusinessException("Customer profile already exists");
//        }
//        if (walletRepository.findByUser(user).isEmpty()){
//            walletService.createWallet(user);
//        }
//
//        CustomerProfile profile = new CustomerProfile();
//
//        profile.setFirstName(request.getFirstName());
//        profile.setLastName(request.getLastName());
//        profile.setPhoneNumber(request.getPhoneNumber());
//        profile.setBvn(request.getBvn());
//        profile.setNin(request.getNin());
//        profile.setDateOfBirth(request.getDateOfBirth());
//        profile.setAddress(request.getAddress());
//        profile.setUser(user);
//
//        long customerCount = customerProfileRepository.count() +1;
//        profile.setCustomerId(NumberGeneratorUtil.generateCustomerId(customerCount));
//        profile.setCustomerNumber(NumberGeneratorUtil.generateCustomerNumber(customerCount));
//
//        customerProfileRepository.save(profile);
//
//        return "Customer profile created successfully";
//    }

    public String createProfile(CreateCustomerProfileRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BusinessException("User not found"));

        if (customerProfileRepository.findByUser(user).isPresent()) {
            throw new BusinessException("Customer profile already exists");
        }

        CustomerProfile profile = new CustomerProfile();

        long customerCount = customerProfileRepository.count() + 1;

        profile.setCustomerId(NumberGeneratorUtil.generateCustomerId(customerCount));

        profile.setCustomerNumber(NumberGeneratorUtil.generateCustomerNumber(customerCount));

        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setBvn(request.getBvn());
        profile.setNin(request.getNin());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setAddress(request.getAddress());

        profile.setUser(user);

        customerProfileRepository.save(profile);

        // AUTO CREATE WALLET
        if (walletRepository.findByUser(user).isEmpty()) {
            walletService.createWallet(user);
        }

        return "Customer profile created successfully";
    }

//    public CustomerProfileResponse getProfile(String email) {
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() ->
//                        new BusinessException("User not found"));
//
//        CustomerProfile profile =
//                customerProfileRepository.findByUser(user)
//                        .orElseThrow(() ->
//                                new RuntimeException(
//                                        "Customer profile not found"
//                                ));
//
//        return CustomerProfileResponse.builder()
//                .customerId(profile.getCustomerId())
//                .customerNumber(profile.getCustomerNumber())
//                .firstName(profile.getFirstName())
//                .lastName(profile.getLastName())
//                .phoneNumber(profile.getPhoneNumber())
//                .bvn(profile.getBvn())
//                .nin(profile.getNin())
//                .dateOfBirth(profile.getDateOfBirth())
//                .address(profile.getAddress())
//                .kycStatus(profile.getKycStatus().name())
//                .tierLevel(profile.getTierLevel().name())
//                .build();
//    }

    public CustomerProfileResponse getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BusinessException("User not found"));

        CustomerProfile profile = customerProfileRepository.findByUser(user)
                        .orElseThrow(() ->
                                new BusinessException("Customer profile not found"));

        Wallet wallet = walletRepository.findByUser(user).orElse(null);

        return CustomerProfileResponse.builder()
                .customerId(profile.getCustomerId())
                .customerNumber(profile.getCustomerNumber())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .phoneNumber(profile.getPhoneNumber())
                .bvn(profile.getBvn())
                .nin(profile.getNin())
                .dateOfBirth(profile.getDateOfBirth())
                .address(profile.getAddress())
                .kycStatus(profile.getKycStatus().name())
                .tierLevel(profile.getTierLevel().name())

                .walletId(wallet != null ? wallet.getWalletId() : null)
                .walletNumber(wallet != null ? wallet.getWalletNumber() : null)

                .build();
    }

    public String updateProfile (String email, UpdateCustomerProfileRequest request){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User not found"));

        CustomerProfile profile = customerProfileRepository.findByUser(user)
                .orElseThrow(() -> new BusinessException("Customer profile not found"));

        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setAddress(request.getAddress());
        profile.setDateOfBirth(request.getDateOfBirth());

        customerProfileRepository.save(profile);

        return "Customer profile updated successfully";
    }

    public String adminUpdateProfile(String customerNumber, UpdateCustomerProfileRequest request) {

        CustomerProfile profile = customerProfileRepository.findByCustomerNumber(customerNumber)
                        .orElseThrow(() ->
                                new BusinessException("Customer profile not found"));

        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setAddress(request.getAddress());
        profile.setDateOfBirth(request.getDateOfBirth());

        customerProfileRepository.save(profile);

        return "Customer profile updated successfully";
    }


}
