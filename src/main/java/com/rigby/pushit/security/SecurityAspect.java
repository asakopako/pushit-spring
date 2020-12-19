package com.rigby.pushit.security;

import com.rigby.pushit.config.exception.UnauthorizedException;
import com.rigby.pushit.service.tool.JwtTool;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Aspect
@Component
public class SecurityAspect {

    private static Pattern pattern = Pattern.compile("Bearer (?<token>[a-zA-Z0-9-._]+)");
    private static final String TOKEN_TAG = "token";
    private static final String AUTH_HEADER = "Authorization";

    @Autowired private JwtTool jwtTool;


    @Before("within(@org.springframework.web.bind.annotation.RestController *) && !@annotation(com.rigby.pushit.security.SecurityIgnore)")
    public void checkToken(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorization = request.getHeader(AUTH_HEADER);
        if (authorization == null || authorization.trim().isEmpty()) {
            throw new UnauthorizedException("Token null or empty");
        }

        Matcher matcher = pattern.matcher(authorization);
        if (!matcher.matches()) {
            throw new UnauthorizedException("Token doesn't match");
        }

        String token = matcher.group(TOKEN_TAG);

        if (!jwtTool.checkToken(token)) {
            throw new UnauthorizedException("Token not valid");
        }

    }

}
