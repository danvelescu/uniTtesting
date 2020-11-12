package com.internship.bookstore.api.dto;


import com.internship.bookstore.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WishesRequestDto {
    private Long id;
    private Long book_id;
    private Long user_id;
}
