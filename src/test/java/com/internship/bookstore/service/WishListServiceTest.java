package com.internship.bookstore.service;

import com.internship.bookstore.api.dto.WishesRequestDto;
import com.internship.bookstore.model.WishList;
import com.internship.bookstore.repository.BookRepository;
import com.internship.bookstore.repository.UserRepository;
import com.internship.bookstore.repository.WishListRepository;
import com.internship.bookstore.utils.mappers.WishMapers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static com.internship.TestConstants.ID_ONE;
import static com.internship.bookstore.utils.BookTestUtils.BOOK_TWO;
import static com.internship.bookstore.utils.WishUtils.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.internship.bookstore.utils.BookTestUtils.BOOK_ONE;
import static com.internship.bookstore.utils.UserTestUtils.USER_ONE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WishListServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private WishListRepository wishListRepository;

    @InjectMocks
    private WishListService wishListService;

    @Mock
    private UserService userService;

    @Mock
    private ValidateWishListService validateWishListService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(wishListService, "messageBookNotFound",
                "Book with id %s was not found");
        ReflectionTestUtils.setField(wishListService, "messageBookFound",
                "User already have this book");
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(bookRepository, userRepository);
    }

    @Test
    public void shouldSaveWish() {
        WishesRequestDto expectedWishesRequestDto = WISHES_REQUEST_DTO;//1 1

        when(userRepository.findById(USER_ONE.getId())).thenReturn(java.util.Optional.of(USER_ONE));
        when(bookRepository.findBookById(BOOK_ONE.getId())).thenReturn(java.util.Optional.of(BOOK_ONE));
        when(wishListRepository.save(any(WishList.class))).thenReturn(WISH_LIST_ONE);
        when(validateWishListService.validateWishForUser()).thenReturn(true);



        final WishesRequestDto returnedwishesRequestDto = wishListService.addNewBookToWish(WISHES_REQUEST_DTO);

        assertAll(
                () -> assertEquals(expectedWishesRequestDto.getBook_id(), returnedwishesRequestDto.getBook_id()),
                () -> assertEquals(expectedWishesRequestDto.getUser_id(), returnedwishesRequestDto.getUser_id())
        );
        verify(wishListRepository, times(1)).save(any(WishList.class));
    }

    @Test
    public void shouldGetWishesForUser() {
        WishesRequestDto wishesRequestDto = WISHES_REQUEST_DTO;

        when(userService.getUser()).thenReturn(USER_ONE);
        when(wishListRepository.getWishListByUserId(any(Long.class))).thenReturn(java.util.Optional.of(WISH_LIST_ONE));


        final WishesRequestDto returnedWish = wishListService.getWishesForUser();
        assertAll(
                () -> assertEquals(wishesRequestDto.getBook_id(),returnedWish.getBook_id()),
                () -> assertEquals(wishesRequestDto.getUser_id(),returnedWish.getUser_id())
        );
        verify(wishListRepository, times(1)).getWishListByUserId(any(Long.class));
    }


   @Test
   public void shouldDeleteWish() {
       when(validateWishListService.validateWishIfExistByID(anyLong())).thenReturn(true);
        when(wishListRepository.getWishListById(anyLong())).thenReturn(java.util.Optional.of(WISH_LIST_ONE));
        wishListService.deleteWish(WishMapers.mapWishToWIshDto.apply(WISH_LIST_ONE));
        verify(wishListRepository, times(1)).delete(any(WishList.class));
   }

   @Test
    public void shouldUpdateWish(){
        WishesRequestDto expectedWish = WISHES_REQUEST_DTO_TWO;
        when(userService.getUser()).thenReturn(USER_ONE);
        when(bookRepository.findBookById(anyLong())).thenReturn(java.util.Optional.ofNullable(BOOK_TWO));
        when(validateWishListService.validateWishIfExistByID(anyLong())).thenReturn(true);
        when(wishListRepository.getWishListById(anyLong())).thenReturn(java.util.Optional.of(WISH_LIST_ONE));


        WishesRequestDto returnedWish = wishListService.updateWish(WishMapers.mapWishToWIshDto.apply(WISH_LIST_TWO));

       assertAll(
               () -> assertEquals(expectedWish.getId(),returnedWish.getId()),
               () -> assertEquals(expectedWish.getBook_id(),returnedWish.getBook_id()),
               () -> assertEquals(expectedWish.getUser_id(),returnedWish.getUser_id())
       );

       verify(wishListRepository, times(1)).save(any(WishList.class));
   }

    @Test
    public void shouldGetWishesForUserID() {
        WishesRequestDto wishesRequestDto = WISHES_REQUEST_DTO;
        when(wishListRepository.getWishListByUserId(any(Long.class))).thenReturn(java.util.Optional.of(WISH_LIST_ONE));


        final WishesRequestDto returnedWish = wishListService.getWishesForUser(ID_ONE);
        assertAll(
                () -> assertEquals(wishesRequestDto.getBook_id(),returnedWish.getBook_id()),
                () -> assertEquals(wishesRequestDto.getUser_id(),returnedWish.getUser_id())
        );
        verify(wishListRepository, times(1)).getWishListByUserId(any(Long.class));
    }
}
