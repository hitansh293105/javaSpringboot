package com.example.Ecommerce_Web.Service;

import com.example.Ecommerce_Web.Dto.AddressDto;
import com.example.Ecommerce_Web.Model.Address;
import com.example.Ecommerce_Web.Model.User;
import com.example.Ecommerce_Web.project.AuthUtil;
import com.example.Ecommerce_Web.Repository.AddressRepo;
import com.example.Ecommerce_Web.Repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
   private  AuthUtil authUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    UserRepo userRepo;

    public AddressDto createAddress(AddressDto addressDto){


        User user =  userRepo.findByUserEmail(authUtil.loggedInEmail()).get();

        Address address =modelMapper.map(addressDto,Address.class);

       address.setUser(user);
       Address address1 = addressRepo.save(address);

       return modelMapper.map(address1, AddressDto.class);
    }

    public List<AddressDto> getAllAddress() {

        return addressRepo.findAll().
                stream().
                map(address-> modelMapper.map(address,AddressDto.class)).toList();
    }

    public List<AddressDto> getAddressByUser() {

        String userName = authUtil.loggedInUserName();
        User user = userRepo.findByUserName(userName).get();

       return user.getAddress().stream().
               map(address -> modelMapper.map(address,AddressDto.class))
               .toList();

    }

    public AddressDto getAddressById(Long id){

        Address address = addressRepo.findById(id).get();
        return modelMapper.map(address,AddressDto.class);
    }

    public AddressDto updateAddress(AddressDto addressDto, Long id) {

        Address address = addressRepo.findById(id).get();

        address.setCountry(addressDto.getCountry());
        address.setState(addressDto.getState());
        address.setCity(addressDto.getCity());
        address.setStreet(addressDto.getStreet());
        address.setBuildingName(addressDto.getBuildingName());
        address.setPostalCode(addressDto.getPostalCode());

        Address updatedAddress =  addressRepo.save(address);


        return modelMapper.map(updatedAddress,AddressDto.class);
    }

    public String deleteAddress(Long addressId) {

        addressRepo.deleteById(addressId);

        return "Address deleted Successfully";

    }
}
