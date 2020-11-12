package com.internship.bookstore.utils;

import com.internship.bookstore.api.dto.DiscountRequestDto;
import com.internship.bookstore.model.Book;
import com.internship.bookstore.model.User;
import com.sun.xml.bind.v2.model.core.ID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.internship.TestConstants.*;
import static com.internship.TestConstants.DISCOUNT_ONE_END_DATE;
import static com.internship.bookstore.utils.AuthorTestUtils.AUTHOR_ONE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTestUtils {
    public static final User USER_ONE = User.builder()
            .id(ID_ONE)
            .email(AUTH_USER_EMAIL)
            .password(AUTH_USER_PASSWORD)
            .build();
}
