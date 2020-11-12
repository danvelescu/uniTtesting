package com.internship.bookstore.api.controller;
import com.internship.bookstore.api.dto.BookRequestDto;
import com.internship.bookstore.api.dto.BookResponseDto;
import com.internship.bookstore.api.dto.WishesRequestDto;
import com.internship.bookstore.api.exchange.Response;
import com.internship.bookstore.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Objects;


@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishRestController {

    private final WishListService wishListService;

    @GetMapping
    public ResponseEntity<Response<WishesRequestDto>> getWish() {
        return ResponseEntity.ok(Response.build(wishListService.getWishesForUser()));
    }

    @PostMapping
    public ResponseEntity<Response<WishesRequestDto>> addWish(
            @RequestBody @Valid WishesRequestDto wishesRequestDto, Errors validationErrors) {

        if (validationErrors.hasErrors()) {
            throw new ValidationException(Objects.requireNonNull(validationErrors.getFieldError()).getDefaultMessage());
        }

        return ResponseEntity.ok(Response.build(wishListService.addNewBookToWish(wishesRequestDto)));
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<Response<Response>> deleteWish(@PathVariable Long id){
        WishesRequestDto wish = wishListService.getWishByID(id);
        wishListService.deleteWish(wish);
        return ResponseEntity.ok(Response.build(Response.build("wish with id:"+wish.getId()+"deleted")));
    }

    @PostMapping("/update")
    public ResponseEntity<Response<WishesRequestDto>> updateWish(
            @RequestBody @Valid WishesRequestDto wishesRequestDto , Errors validationerr
    ){
        if(validationerr.hasErrors()){
            throw new ValidationException(Objects.requireNonNull(validationerr.getFieldError().getDefaultMessage()));
        }
        return ResponseEntity.ok(Response.build(wishListService.updateWish(wishesRequestDto)));
    }

}
