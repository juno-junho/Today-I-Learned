package com.junho.learninfraaws.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @GetMapping("/members")
    @ApiResponse(description = "회원 조회")
    public void getMembers() {

    }

    @PostMapping("/members")
    @ApiResponse(description = "회원 등록")
    public void register() {

    }

    @DeleteMapping("/members")
    @ApiResponse(description = "회원 삭제")
    public void delete() {

    }

}
