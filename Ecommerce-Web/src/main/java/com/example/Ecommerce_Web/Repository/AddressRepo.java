package com.example.Ecommerce_Web.Repository;

import com.example.Ecommerce_Web.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address,Long> {
    Address findByUserUserName(String userName);
}
