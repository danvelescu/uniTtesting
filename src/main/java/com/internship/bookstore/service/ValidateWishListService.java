package com.internship.bookstore.service;

import com.internship.bookstore.model.User;
import com.internship.bookstore.model.WishList;
import com.internship.bookstore.repository.WishListRepository;
import com.internship.bookstore.utils.exceptions.WishException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidateWishListService {

    private final WishListRepository wishListRepository;
    private final UserService userService;
    public boolean validateWishForUser(){
        User user  = userService.getUser();
        return  !wishListRepository.getWishListByUserId(user.getId()).isPresent();
    }

    public boolean validateWishIfExist(WishList wish) {
        return wishListRepository.findById(wish.getId()).isPresent();
    }
    public boolean validateWishIfExistByID(Long id) {
        return wishListRepository.findById(id).isPresent();
    }
}
