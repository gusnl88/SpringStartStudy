package com.acorn.springstartstudy.controller;

import com.acorn.springstartstudy.dto.UsersDto;
import com.acorn.springstartstudy.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class L02ConnDIController {
    //spring  설정으로 datasource 를 정의하면 컨테이너에 Connection 객체가 존재한다.(java.sql.Connection 을 반환)
//    @Autowired
    DataSource dataSource;
    UsersMapper usersMapper;//Mybatis 의 session factory 가 생성한 객체가 주입

    public L02ConnDIController(DataSource dataSource,UsersMapper usersMapper) { // ==@Autowired 와 동일 (권장)
        this.usersMapper=usersMapper;
        this.dataSource = dataSource;
    }

    @GetMapping("/update.do")
    public void updateForm(@RequestParam(name = "u_id") String uId,Model model){
        UsersDto user=usersMapper.findByUId(uId);
        model.addAttribute("user",user);
    }
    @PostMapping("/update.do")
    public String updateAction(UsersDto user){ //폼의 파라미터를 맵핑한다.
        //객체의 필드는 자료형 null , 기본형은 0으로 되어있다.
        int update=0;
        update=usersMapper.updateOne(user);
        if(update>0){
            return "redirect:/users/detail.do?u_id="+user.getUId();
        }else {
            return "redirect:/users/update.do?u_id="+user.getUId();
        }
    }
    @GetMapping("/delete.do")
    public String delete(@RequestParam(name = "u_id")String uId,Model model){
        int delete=0;
        delete=usersMapper.deleteOne(uId);
        if(delete>0){
            return "redirect:/users/mybatisList.do";
        }else {
            return "redirect:/users/update.do?u_id="+uId;
        }
    }
    @GetMapping("/mybatisList.do")
    public  String mybatisList(Model model){
        List<UsersDto> users=usersMapper.findAll();
        model.addAttribute("users",users);
        return "/users/mybatisList";
    }
    @GetMapping("/detail.do")
    public String mybatisDetail(@RequestParam("u_id") String uId, Model model){
        UsersDto user = usersMapper.findByUId(uId); // id에 해당하는 유저를 검색합니다.
        model.addAttribute("user", user); // 검색된 유저를 모델에 추가합니다.
        return "/users/mybatisDetail"; // mybatisDetail 뷰로 전환합니다.
    }




    @GetMapping("/list.do")
    public String list(Model model) throws SQLException {
        String sql="SELECT * FROM users";
        try {
            Connection conn=dataSource.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql);
            ResultSet rs=pstmt.executeQuery();
            List<UsersDto> users=new ArrayList<>();
            while (rs.next()){
                UsersDto user=new UsersDto();
                user.setUId(rs.getString("u_id"));
                user.setPw(rs.getString("pw"));
                user.setAddress(rs.getString("address"));
                user.setDetailAddress(rs.getString("detail_address"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setGender(rs.getString("gender"));
                user.setPermission(rs.getString("permission"));
                user.setBirth(rs.getString("birth"));
                user.setName(rs.getString("name"));
                user.setImgPath(rs.getString("img_path"));
                user.setPostTime(rs.getDate("post_time"));
                users.add(user);
            }
            model.addAttribute("users",users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/users/list";
    }

}
