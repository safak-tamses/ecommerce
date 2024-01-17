package com.x.ecommerceback.service;

import com.x.ecommerceback.exception.CustomRuntimeException;
import com.x.ecommerceback.model.Address;
import com.x.ecommerceback.repository.AddressRepository;
import com.x.ecommerceback.service.interfaces.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressServiceImplementation implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public void addressSave(Address address) {
        try {
            addressRepository.save(address);
        }catch (Exception e){
            throw new CustomRuntimeException("Adres kaydedilirken hata meydana geldi");
        }
    }
}
