package com.example.Ecommerce_Web.Controller;

import com.example.Ecommerce_Web.Dto.AddressDto;
import com.example.Ecommerce_Web.Service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/user/create-address")
    public ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto address){

        return ResponseEntity.ok(addressService.createAddress(address));
    }

    @PostMapping("/user/getAll")
    public ResponseEntity<List<AddressDto>> getAllAddress(){

        return ResponseEntity.ok(addressService.getAllAddress());
    }

    @PostMapping("/user-address")
    public ResponseEntity<List<AddressDto>> getAddressByUser(){

        return ResponseEntity.ok(addressService.getAddressByUser());
    }

    @PostMapping("/user/address/{addressId}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long addressId){

        return ResponseEntity.ok(addressService.getAddressById(addressId));
    }

    @PutMapping("/user/update/Address/{id}")
    public ResponseEntity<AddressDto> updateAddress(@RequestBody AddressDto addressDto,@PathVariable Long id){

        return ResponseEntity.ok(addressService.updateAddress(addressDto,id));
    }

    @DeleteMapping("/user/delete/Address")
    public ResponseEntity<String> deleteAddress(@RequestParam Long addressId){

        return ResponseEntity.ok(addressService.deleteAddress(addressId));
    }
}
