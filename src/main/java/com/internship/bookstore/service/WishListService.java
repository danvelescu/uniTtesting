package com.internship.bookstore.service;

import com.internship.bookstore.api.controller.WishRestController;
import com.internship.bookstore.api.dto.WishesRequestDto;
import com.internship.bookstore.model.Book;
import com.internship.bookstore.model.User;
import com.internship.bookstore.model.WishList;
import com.internship.bookstore.repository.BookRepository;
import com.internship.bookstore.repository.UserRepository;
import com.internship.bookstore.repository.WishListRepository;
import com.internship.bookstore.utils.exceptions.RecordNotFoundException;
import com.internship.bookstore.utils.exceptions.WishException;
import com.internship.bookstore.utils.mappers.WishMapers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishListService {

    private final ValidateWishListService validateWishListService;
    private final WishListRepository wishListRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Value("${message.book.not-found}")
    private String messageBookNotFound;

    @Value("User already have this book")
    private String messageBookFound;

    public WishesRequestDto addNewBookToWish(WishesRequestDto wishesRequestDto) {
        User user = userRepository.findById(wishesRequestDto.getUser_id()).orElseThrow(
                () -> {
                    log.warn("Book with id [{}] was not found in the database", wishesRequestDto.getUser_id());
                    return new RecordNotFoundException(format(messageBookNotFound, wishesRequestDto.getUser_id()));

                });

        Book book = bookRepository.findBookById(wishesRequestDto.getBook_id()).orElseThrow(() -> {
            log.warn("Book with id [{}] was not found in the database", wishesRequestDto.getBook_id());
            return new RecordNotFoundException(format(messageBookNotFound, wishesRequestDto.getBook_id()));
        });

        WishList wishList = new WishList();
        wishList.setBook(book);
        wishList.setUser(user);


        if (validateWishListService.validateWishForUser()) {
            wishListRepository.save(wishList);
            log.info("Saved wish for user with: id [{}]", wishList.getUser().getId());
            return WishMapers.mapWishToWIshDto.apply(wishList);
        } else {
            log.warn("User already have book with id [{}]", wishesRequestDto.getBook_id());
            throw new WishException(messageBookFound);
        }

    }


    public WishesRequestDto getWishesForUser() {
        User user = userService.getUser();

        return WishMapers.mapOptionalWishToWIshDto.apply(Optional.ofNullable(wishListRepository.getWishListByUserId(user.getId()).orElseThrow(
                () -> {
                    log.warn("This item with user id:[{}] doesnt exist in db ", user.getId());
                    return new WishException("Wish not exist in db");
                }
        )));
    }

    public WishesRequestDto getWishesForUser(Long id) {
        return WishMapers.mapOptionalWishToWIshDto.apply(Optional.ofNullable(wishListRepository.getWishListByUserId(id).orElseThrow(
                () -> {
                    log.warn("This item with user id:[{}] doesnt exist in db ", id);
                    return new WishException("Wish not exist in db");
                }
        )));
    }

    public void deleteWish(WishesRequestDto wishDto) {

        if (validateWishListService.validateWishIfExistByID(wishDto.getId())) {
            Optional<WishList> wish = wishListRepository.getWishListById(wishDto.getId());
            wishListRepository.delete(wish.get());
            log.warn("Wish deleted id:[{}]", wish.get().getId());
        } else {
            log.warn("Wish not found");
            throw new WishException("Wish not found");
        }
    }

    public WishesRequestDto updateWish(WishesRequestDto wishList) {
        if (validateWishListService.validateWishIfExistByID(wishList.getId())) {
            Optional<WishList> optionalWishList = wishListRepository.getWishListById(wishList.getId());
            WishList wish = optionalWishList.get();

            Book book = bookRepository.findBookById(wishList.getBook_id()).orElseThrow(() -> {
                log.warn("Book with id [{}] was not found in the database", wishList.getBook_id());
                return new RecordNotFoundException(format(messageBookNotFound, wishList.getBook_id()));
            });

            wish.setUser(userService.getUser());
            wish.setBook(book);

            wishListRepository.save(wish);
            log.info("Wish id:[{}] was updated", wishList.getId());
            return WishMapers.mapWishToWIshDto.apply(wish);
        } else {
            throw new WishException("Wish with id: " + wishList.getId() + " not found");
        }
    }

    public WishesRequestDto getWishByID(Long id) {
        if (validateWishListService.validateWishIfExistByID(id)) {
            Optional<WishList> wishList = wishListRepository.getWishListById(id);
            log.info("Wish founded");
            return WishMapers.mapWishToWIshDto.apply(wishList.get());
        } else {
            throw new WishException("No wish in DB");
        }
    }
}
