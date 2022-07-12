package com.surge.studentmanagement.service;

import com.surge.studentmanagement.domain.BaseAuthRequest;
import com.surge.studentmanagement.domain.Response;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    Response createUser(String email);

    Response singIn(BaseAuthRequest singInRequest);

    Response singOut(HttpServletResponse httpServletResponse);
}
