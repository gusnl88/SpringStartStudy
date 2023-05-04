package com.acorn.springstartstudy.mapper;

import com.acorn.springstartstudy.dto.UsersDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper //Mybatis db 컨테이너에서 객체 구현후 관리
public interface UsersMapper {
    List<UsersDto> findAll();
    UsersDto findByUId(String uId);//#{uId}

    int updateOne(UsersDto user);
    int deleteOne(String uId);
}
