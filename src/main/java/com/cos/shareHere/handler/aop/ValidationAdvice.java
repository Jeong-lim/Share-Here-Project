package com.cos.shareHere.handler.aop;

import com.cos.shareHere.handler.ex.CustomValidationApiException;
import com.cos.shareHere.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component // IoC 컨테이너에 등록해주는 어노테이션들의 최상위 객체
@Aspect // AOP처리를 할 수 있게 만들어주는 어노테이션
public class ValidationAdvice {
    
    @Around("execution(* com.cos.shareHere.web.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("==========================================");
        System.out.println("WEB API Controller 실행 : Data Response");
        System.out.println("==========================================");

        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성검사 실패함", errorMap);
                }
            }
        }

        return proceedingJoinPoint.proceed();
    }

    @Around("execution(* com.cos.shareHere.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("==========================================");
        System.out.println("WEB Controller 실행 : File Response");
        System.out.println("==========================================");

        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
                        System.out.println(error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성검사 실패함", errorMap);
                }
            }
        }

        return proceedingJoinPoint.proceed();
    }
}
