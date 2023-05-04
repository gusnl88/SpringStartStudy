package com.acorn.springstartstudy.mapper;

import com.acorn.springstartstudy.dto.BoardsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardsMapper {
    List<BoardsDto> findAll();

    BoardsDto findByBId(int bId);

    int updateOne(BoardsDto board);
}
