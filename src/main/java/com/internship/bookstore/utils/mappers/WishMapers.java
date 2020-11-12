package com.internship.bookstore.utils.mappers;



import com.internship.bookstore.api.dto.WishesRequestDto;
import com.internship.bookstore.model.WishList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WishMapers {

    public static final Function<WishList, WishesRequestDto> mapWishToWIshDto =
            wishlist -> WishesRequestDto.
                    builder()
                    .id(wishlist.getId())
                    .book_id(wishlist.getBook().getId())
                    .user_id(wishlist.getUser().getId())
                    .build();

    public static final Function<Optional<WishList>, WishesRequestDto> mapOptionalWishToWIshDto =
            wishlist -> WishesRequestDto.
                    builder()
                    .id(wishlist.get().getId())
                    .book_id(wishlist.get().getBook().getId())
                    .user_id(wishlist.get().getUser().getId())
                    .build();

}
