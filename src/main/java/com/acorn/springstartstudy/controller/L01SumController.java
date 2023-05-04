package com.acorn.springstartstudy.controller;

import jakarta.servlet.http.HttpServlet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class L01SumController{
    @GetMapping("/sum.do")
    public void sum(int a, int b, Model model){
        model.addAttribute("a",a);
        model.addAttribute("b",b);
        //model.addAttribute == req.setAttribute("a",a)
        //model.addAttribute :thymeleaf 문서에서 a,b 변수를 사용가능
        //req.getRequestDispatcher("/templates/sum.html").forward(req,resp) 랑 같다
    }
    @GetMapping("/mult.do")
    public String multiply(
            @RequestParam(name = "a",required = true) int a,
            //required = true 파라미터 a 가 없으면 400
            @RequestParam(name = "b",defaultValue = "0") int b,
            Model model){ // 디폴트값을 주면 자동으로 required = false 된다.
            //자바의 기본은 null 이 될수 없어서 required = false 일수 없다.
            //required = false 기본형으로 파라미터를 받을 때는 required = false  일수 없다 왜?
            //required = false : 기본형으로 파라미터를 받고 싶으면 defaultValue 로 기본값을 정의하면 된다.
            model.addAttribute("a",a);
            model.addAttribute("b",b);

            return "/multipliy.html";//html 은 생략해도 된다. =>/templates/multiply.html 을 렌더링한다.
    }
}
