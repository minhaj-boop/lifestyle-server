package com.server.lifestyle.repository;

import com.server.lifestyle.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
