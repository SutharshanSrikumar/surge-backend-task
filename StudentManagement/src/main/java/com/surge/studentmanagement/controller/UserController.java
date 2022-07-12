package com.surge.studentmanagement.controller;

import com.surge.studentmanagement.domain.BaseAuthRequest;
import com.surge.studentmanagement.domain.Response;
import com.surge.studentmanagement.service.UserService;
import com.surge.studentmanagement.util.DataValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Api(value = "User Auth Controller")
@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:3000")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value = "createUser", response = Response.class, tags = "singUp")
    @PostMapping(value = "/createuser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createUser(@RequestParam("email") String email) {

        if (DataValidation.patternMatches(email, "^(.+)@(\\S+)$")) {
            logger.info("Entering into singUp for email ::{}", email);
            return ResponseEntity.ok().body(userService.createUser(email));
        }
        return ResponseEntity.badRequest().body(null);

    }

    @ApiOperation(value = "singIn", response = Response.class, tags = "singIn")
    @PostMapping("/signin")
    public ResponseEntity<Response> singIn(@RequestBody BaseAuthRequest loginRequest) {

        logger.info("Entering into singIn for UserName ::{}", loginRequest.getLoginName());
        return ResponseEntity.ok().body(userService.singIn(loginRequest));
    }


    @ApiOperation(value = "singOut", response = Response.class, tags = "singOut")
    @GetMapping("/signout")
    public ResponseEntity<Response> singOut(HttpServletResponse httpServletResponse) {


        logger.info("Entering into singOut");
        return ResponseEntity.ok().body(userService.singOut(httpServletResponse));
    }

}
