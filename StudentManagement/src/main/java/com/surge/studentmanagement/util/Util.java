package com.surge.studentmanagement.util;


import com.surge.studentmanagement.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;


public class Util {

    public static CustomUserDetails getUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }
}
