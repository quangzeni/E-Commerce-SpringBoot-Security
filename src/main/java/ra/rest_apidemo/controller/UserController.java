package ra.rest_apidemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.rest_apidemo.dto.request.SignInRequest;
import ra.rest_apidemo.dto.request.SignUpRequest;
import ra.rest_apidemo.dto.response.SignInResponse;
import ra.rest_apidemo.dto.response.SignUpResponse;
import ra.rest_apidemo.dto.response.UserDTOResponse;
import ra.rest_apidemo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@CrossOrigin("*")

public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("public/user")
    public ResponseEntity<SignUpResponse> register(@RequestBody SignUpRequest signUpRequest){
        SignUpResponse signUpResponse = userService.register(signUpRequest);
        return new ResponseEntity<>(signUpResponse, HttpStatus.CREATED);
    }

    @PostMapping("public/user/login")
    public ResponseEntity<SignInResponse> login(@RequestBody SignInRequest signInRequest){
        SignInResponse signInResponse = userService.login(signInRequest);
        return new ResponseEntity<>(signInResponse,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("admin/user")

    public ResponseEntity<List<UserDTOResponse>> findAll(){
        return new ResponseEntity<>(userService.findAll(),HttpStatus.OK);
    }
}
