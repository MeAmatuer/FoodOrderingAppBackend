package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.PaymentDao;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.exception.PaymentMethodNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    public PaymentEntity getPaymentByUUID(UUID paymentId) throws PaymentMethodNotFoundException {
        PaymentEntity paymentEntity = paymentDao.getPayment(paymentId);
        if(paymentEntity == null){
            throw new PaymentMethodNotFoundException("PNF-002", "No payment method found by this id");
        }else {
            return paymentEntity;
        }
    }

    public List<PaymentEntity> getPaymentMethods() {
        List<PaymentEntity> paymentEntities = paymentDao.getPaymentMethods();
        return paymentEntities;

    }
}
