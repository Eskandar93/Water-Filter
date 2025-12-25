package com.waterfilter.water.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waterfilter.water.apiResponse.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/")
public class UserController {

  private final UserService userService;

  @PostMapping("register")
  public ResponseEntity<ApiResponse<String>> register(@RequestBody UserRequest userRequest){
      userService.register(userRequest);
      ApiResponse<String> response = ApiResponse.created("User registered successfully");
      return ResponseEntity.ok(response);
  }

  @PostMapping("login")
  public ResponseEntity<ApiResponse<String>> login(@RequestBody UserRequest userRequest){
    String token = userService.verifyUser(userRequest);
    ApiResponse<String> response = ApiResponse.success(token);
    return ResponseEntity.ok(response);
  }

}
