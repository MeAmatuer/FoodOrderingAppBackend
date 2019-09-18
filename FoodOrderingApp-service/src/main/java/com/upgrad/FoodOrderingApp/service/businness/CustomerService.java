package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerAuthDao customerAuthDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity saveCustomer(CustomerEntity newCustomer) throws SignUpRestrictedException {
        CustomerEntity existingCustomer = customerDao.findByContactNumber(newCustomer.getContactNumber());
        if (existingCustomer != null){
            throw new SignUpRestrictedException("SGR-001", "This contact number is already registered! Try other contact number");
        }
        if (!fieldsComplete(newCustomer)) {
            throw new SignUpRestrictedException("SGR-005", "Except last name all fields should be filled");
        }
        if (!validEmailAddress(newCustomer.getEmail())) {
            throw new SignUpRestrictedException("SGR-002", "Invalid email-id format!");
        }
        if (!validContactNumber(newCustomer.getContactNumber())) {
            throw new SignUpRestrictedException("SGR-003", "Invalid contact number!");
        }
        if (!validPassword(newCustomer.getPassword())) {
            throw new SignUpRestrictedException("SGR-004", "Weak password!");
        }

        encryptPassword(newCustomer);
        return customerDao.createCustomer(newCustomer);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity authenticate(final String username, final String password) throws AuthenticationFailedException {
        CustomerEntity registeredCustomer = customerDao.findByContactNumber(username);
        if ( registeredCustomer == null ) {
            throw new AuthenticationFailedException("ATH-001", "This contact number has not been registered!");
        }
        final String encryptedPassword = passwordCryptographyProvider.encrypt(password, registeredCustomer.getSalt());
        if (registeredCustomer.getPassword().equals(encryptedPassword)) {
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            CustomerAuthEntity customerAuthEntity = new CustomerAuthEntity();
            customerAuthEntity.setUuid(UUID.randomUUID().toString());
            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);
            customerAuthEntity.setAccessToken(jwtTokenProvider.generateToken(registeredCustomer.getUuid(), now, expiresAt));
            customerAuthEntity.setExpiresAt(expiresAt);
            customerAuthEntity.setCustomer(registeredCustomer);
            customerAuthEntity.setLoginAt(now);
            CustomerAuthEntity authCustomer = customerAuthDao.createCustomerAuth(customerAuthEntity);
            return authCustomer;
        }
        else {
            throw new AuthenticationFailedException("ATH-002", "Invalid Credentials");
        }
    }

    private boolean fieldsComplete(CustomerEntity customer) throws SignUpRestrictedException {
        if ( customer.getFirstName().isEmpty()||
                customer.getContactNumber().isEmpty()||
                customer.getEmail().isEmpty() ||
                customer.getPassword().isEmpty() )
            return false;
        else
            return true;
    }

    private boolean validEmailAddress(String email) {
        String regex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher= pattern.matcher(email);
        return  matcher.matches();
    }

    private boolean validContactNumber(String contact) {
        String regex = "[0-9]{10}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contact);
        if (matcher.find() && matcher.group().equals(contact))
            return true;
        return false;
    }

    private boolean validPassword(String password) {
        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#@$%&*!^]).{8,}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    private void encryptPassword(final CustomerEntity newCustomer) {
        String password = newCustomer.getPassword();
        final String[] encryptedData = passwordCryptographyProvider.encrypt(password);
        newCustomer.setSalt(encryptedData[0]);
        newCustomer.setPassword(encryptedData[1]);
    }
}
