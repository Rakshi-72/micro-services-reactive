package com.userservice.userservice.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTransaction {
    @Id
    private Integer id;
    private Integer userId;
    private Integer amount;

    @JsonFormat(shape = Shape.STRING, pattern = "hh:mm:ss dd:MM:yyyy")
    private LocalDateTime transactionDate = LocalDateTime.now();
}
