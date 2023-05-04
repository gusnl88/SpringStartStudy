package com.acorn.springstartstudy.controller;

import com.acorn.springstartstudy.dto.BoardsDto;
import com.acorn.springstartstudy.mapper.BoardsMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.util.List;

@Controller
@RequestMapping("/boards")
public class L03connBoardController {
    DataSource dataSource;
    BoardsMapper boardsMapper;

    public L03connBoardController(DataSource dataSource, BoardsMapper boardsMapper) {
        this.dataSource = dataSource;
        this.boardsMapper = boardsMapper;
    }
    @GetMapping("/list.do")
    public String findAll(Model model){
        List<BoardsDto>boards=boardsMapper.findAll();
        model.addAttribute("boards",boards);
        return "/boards/list";
    }
    @GetMapping("/detail.do")
    public String detail(@RequestParam(name = "b_id") int bId,Model model){
        BoardsDto board=boardsMapper.findByBId(bId);
        model.addAttribute("board",board);
        return "/boards/detail";
    }
    @GetMapping("/update.do")
    public void updateList(@RequestParam(name = "b_id")int bId,Model model){
        BoardsDto board=boardsMapper.findByBId(bId);
        System.out.println("board = " + board);
        model.addAttribute("board",board);
    }
    @PostMapping("/update.do")
    public String update(BoardsDto board){
        System.out.println("board2 = " + board);
        int update=0;
        update=boardsMapper.updateOne(board);
        if (update>0){
            return "redirect:./detail.do?b_id="+board.getBId();
        }else {
            return "redirect:./update.do?b_id="+board.getBId();
        }
    }


}
