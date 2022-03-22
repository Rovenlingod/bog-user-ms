package com.example.boguserms.logging;

import com.example.boguserms.dto.UserDetailsDTO;
import com.example.boguserms.dto.UserResponseDTO;
import com.example.boguserms.utulities.CustomUtils;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@Aspect
@Log4j2
public class LoggingAspect {

    @Pointcut("within(com.example.boguserms..*)")
    protected void loggingAllOperations() {
    }

    @Pointcut("execution(* com.example.boguserms.controller.*.*(..))")
    protected void loggingControllerOperations() {
    }

    @AfterThrowing(pointcut = "loggingAllOperations()", throwing = "exception")
    public void logAllExceptions(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception occurred while executing method \"" + joinPoint.getSignature() + "\"");
        log.error("Cause: " + exception.getCause() + ". Exception : " + exception.getClass().getName() + ". Message: " + exception.getMessage());
        log.error("Provided arguments: ");
        Object[] arguments = joinPoint.getArgs();
        for (Object a :
                arguments) {
            if (Objects.isNull(a)) {
                log.error("[null]");
            } else {
                log.error("Class name: " + a.getClass().getSimpleName() + ". Value: " + a);
            }
        }
    }

    @Before("loggingControllerOperations()")
    public void logEndpointAccess(JoinPoint joinPoint) {
        Optional<UserDetailsDTO> currentUser = CustomUtils.getCurrentUser();
        String username = currentUser.isPresent() ? currentUser.get().getUsername() : "Anonymous";
        log.info("User with username \"" + username + "\" got access to the controller method " + joinPoint.getSignature());
    }

    @AfterReturning(value = "execution(* com.example.boguserms.service.UserService.createUser(..))", returning = "user")
    public void logUserCreation(JoinPoint joinPoint, UserResponseDTO user) {
        log.info("User with id \"" + user.getUserId() + "\" successfully created his account.");
    }

    @AfterReturning(value = "execution(* com.example.boguserms.service.UserService.updateUser(..))", returning = "user")
    public void logUserUpdate(JoinPoint joinPoint, UserResponseDTO user) {
        log.info("User with id \"" + user.getUserId() + "\" successfully updated his account.");
    }
}

