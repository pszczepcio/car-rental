package com.kodilla.carrental.dao;

import com.kodilla.carrental.domain.Coupon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponDao extends CrudRepository<Coupon, Long> {

    @Override
    List<Coupon> findAll();
}
