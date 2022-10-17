package com.amigoscode.testing.customer;

import com.amigoscode.testing.utils.PhoneNumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerRegistrationService {

    private final CustomerRepository customerRepository;
    private final PhoneNumberValidator phoneNumberValidator;

    @Autowired
    public CustomerRegistrationService(CustomerRepository customerRepository, PhoneNumberValidator phoneNumberValidator) {
        this.customerRepository = customerRepository;
        this.phoneNumberValidator = phoneNumberValidator;
    }

    public void registerNewCustomer(CustomerRegistrationRequest request){
        // 1. PhoneNumber is taken
        // 2. If taken, let's check if it belongs to same customer
        // 2.1. If yes, return
        // 2.2. Throw an exception
        // 3. Save customer

        String phoneNumber = request.getCustomer().getPhoneNumber();

        if(!phoneNumberValidator.test(phoneNumber)){
            throw new IllegalStateException("Phone number " + phoneNumber + " is not valid");
        }

        Optional<Customer> optional = customerRepository.selectCustomerByPhoneNumber(phoneNumber);

        if (optional.isPresent()) {
            Customer customer = optional.get();
            if (customer.getName().equals(request.getCustomer().getName())) {
                System.out.println("The customer is already registered.");
                return;
            }
            throw new IllegalStateException(String.format("This phone number [%s] is already taken.", phoneNumber));
        }

        if(request.getCustomer().getId() == null) {
            request.getCustomer().setId(UUID.randomUUID());
        }

        customerRepository.save(request.getCustomer());
    }
}
