package com.userservice.userservice.configs;

import com.userservice.userservice.dtos.TransactionDto;
import com.userservice.userservice.dtos.TransactionResponse;
import com.userservice.userservice.dtos.UserDto;
import com.userservice.userservice.models.User;
import com.userservice.userservice.models.UserTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "Spring")
public interface MapperUtil {
    MapperUtil instance = Mappers.getMapper(MapperUtil.class);

    User dtoToUserModel(UserDto dto);

    UserDto modelUserToDto(User User);

    UserTransaction dtoToUserTransactionModel(TransactionDto dto);

    TransactionDto modelUserTransactionToDto(UserTransaction transaction);

    TransactionResponse dtoToResponse(TransactionDto dto);
}
