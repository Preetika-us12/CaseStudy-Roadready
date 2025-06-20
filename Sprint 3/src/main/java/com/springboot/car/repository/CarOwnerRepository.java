package com.springboot.car.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.car.model.CarOwner;

public interface CarOwnerRepository extends JpaRepository<CarOwner, Integer> {
    
    @Query("select co from CarOwner co where co.user.username = ?1")
    CarOwner getCarOwnerByUsername(String username);
    
    @Query("select o from CarOwner o where lower(o.name) like lower(concat('%', ?1, '%')) or lower(o.email) like lower(concat('%', ?1, '%'))")
    Page<CarOwner> searchByKeyword(String keyword, Pageable pageable);
}
