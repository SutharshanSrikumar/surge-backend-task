package com.surge.studentmanagement.serviceimpl;

import com.surge.studentmanagement.domain.BaseAuthRequest;
import com.surge.studentmanagement.domain.Response;
import com.surge.studentmanagement.domain.UserInfo;
import com.surge.studentmanagement.entity.Role;
import com.surge.studentmanagement.entity.User;
import com.surge.studentmanagement.exception.UserException;
import com.surge.studentmanagement.respository.RoleRepository;
import com.surge.studentmanagement.respository.UserRepository;
import com.surge.studentmanagement.security.CustomUserDetails;
import com.surge.studentmanagement.security.JwtTokenUtil;
import com.surge.studentmanagement.service.UserService;
import com.surge.studentmanagement.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    @Transactional
    public Response createUser(String email) {

        logger.info("Entering into singUp email :: {}", email);
        try {
            User user = new User();
            //have to email validation
            user.setEmail(email);
            user.setPassword(encoder.encode("tempassword"));
            user.setProfileCompleted(Boolean.FALSE);
            user.setCreatedDate(new Date());

            Optional<Role> role = roleRepository.findById(1);
            if (role.isPresent()) {
                user.getUserRole().add(role.get());
                role.get().getUsers().add(user);
            }
            //after success send email to user
            return Response.success(userRepository.save(user));

        } catch (UserException exception) {
            throw new UserException("Exception occurred during singUp and Message ::" + exception.getMessage());
        }

    }

    @Override
    public Response singIn(BaseAuthRequest singInRequest) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(singInRequest.getLoginName(),
                            singInRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String jwt = jwtTokenUtil.generateTokenFromEmail(userDetails.getUsername());

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            Optional<User> user = userRepository.findById(Util.getUserDetails().getId());

            if (user.isPresent()) {
                user.get().setLastLoginDate(new Date());

                UserInfo userInfo = new UserInfo();
                userInfo.setAccessToken(jwt);
                userInfo.setId(userDetails.getId());
                userInfo.setProfileCompleted(userDetails.getProfileCompleted());
                userInfo.setLoginName(userDetails.getUsername());
                userInfo.setRoles(roles);

                userRepository.save(user.get());
                return Response.success(userInfo);
            } else {
                return Response.failure("User Not found");
            }
        } catch (UserException exception) {
            throw new UserException("Exception occurred during singIn and Message ::" + exception.getMessage());
        }
    }

    @Override
    public Response singOut(HttpServletResponse httpServletResponse) {

        try {
            httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, null);

            return Response.success("User Successfully logout");
        } catch (UserException exception) {
            throw new UserException("Exception occurred during singUp and Message ::" + exception.getMessage());
        }
    }
}
