package com.internship.bookstore.utils;


import com.internship.bookstore.api.dto.DiscountResponseDto;
import com.internship.bookstore.api.dto.WishesRequestDto;
import com.internship.bookstore.model.WishList;
import com.sun.xml.bind.v2.model.core.ID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.internship.TestConstants.*;
import static com.internship.bookstore.utils.BookTestUtils.BOOK_ONE;
import static com.internship.bookstore.utils.BookTestUtils.BOOK_TWO;
import static com.internship.bookstore.utils.UserTestUtils.USER_ONE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WishUtils {

    public static final WishesRequestDto WISHES_REQUEST_DTO = WishesRequestDto
            .builder()
            .book_id(BOOK_ONE.getId())
            .user_id(USER_ONE.getId())
            .build();

    public static final WishList WISH_LIST_ONE = WishList.builder()
            .id(ID_ONE)
            .book(BOOK_ONE)
            .user(USER_ONE)
            .build();
    public static final WishList WISH_LIST_TWO = WishList.builder()
            .id(ID_ONE)
            .book(BOOK_TWO)
            .user(USER_ONE)
            .build();

    public static final WishesRequestDto WISHES_REQUEST_DTO_TWO = WishesRequestDto
            .builder()
            .id(ID_ONE)
            .book_id(BOOK_TWO.getId())
            .user_id(USER_ONE.getId())
            .build();
}
