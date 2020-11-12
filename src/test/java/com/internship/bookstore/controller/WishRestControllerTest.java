package com.internship.bookstore.controller;

import com.internship.bookstore.api.controller.WishRestController;
import com.internship.bookstore.api.dto.WishesRequestDto;
import com.internship.bookstore.service.UserService;
import com.internship.bookstore.service.WishListService;
import com.internship.bookstore.utils.mappers.WishMapers;
import com.internship.it.controller.BaseController;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.internship.TestConstants.ID_ONE;
import static com.internship.bookstore.utils.UserTestUtils.USER_ONE;
import static com.internship.bookstore.utils.WishUtils.WISHES_REQUEST_DTO;

import static com.internship.bookstore.utils.WishUtils.WISH_LIST_ONE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WishRestController.class)
public class WishRestControllerTest extends BaseController {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishListService wishListService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    public void shouldGetWishes() throws Exception {
        when(wishListService.getWishesForUser()).thenReturn(WISHES_REQUEST_DTO);
        mockMvc.perform(get("/wishlist"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createExpectedBody(WISHES_REQUEST_DTO)));

        verify(wishListService).getWishesForUser();
    }

    @Test
    @WithMockUser
    void shouldCreateWishList() throws Exception {
        when(wishListService.addNewBookToWish(any(WishesRequestDto.class))).thenReturn(WISHES_REQUEST_DTO);

        mockMvc.perform(post("/wishlist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createExpectedBody(WISHES_REQUEST_DTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createExpectedBody(WISHES_REQUEST_DTO)));

        verify(wishListService,times(1)).addNewBookToWish(any(WishesRequestDto.class));
    }


    @Test
    @WithMockUser
    void shouldDeleteById() throws Exception {
        when(wishListService.getWishByID(anyLong())).thenReturn(WishMapers.mapWishToWIshDto.apply(WISH_LIST_ONE));
        mockMvc.perform(delete("/wishlist/delete/{id}",ID_ONE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(wishListService).deleteWish(any(WishesRequestDto.class));
    }

    @Test
    @WithMockUser
    void shouldUpdateWish() throws Exception {
        when(wishListService.updateWish(any(WishesRequestDto.class))).thenReturn(WISHES_REQUEST_DTO);


        mockMvc.perform(post("/wishlist/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createExpectedBody(WISHES_REQUEST_DTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(createExpectedBody(WISHES_REQUEST_DTO)));

    }

}
