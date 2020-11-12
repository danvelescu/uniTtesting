package com.internship.bookstore.repository;

import com.internship.bookstore.model.WishList;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList,Long> {
       Optional<WishList> getWishListByUserId(Long id);

       Optional<WishList> getWishListById(Long id);
}
