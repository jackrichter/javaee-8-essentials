package com.linkedin.hsports.cdi.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Logging
@Interceptor
public class LoggingInterceptor {

    public LoggingInterceptor() {
    }

    @AroundInvoke
    public Object aroundInvoke(InvocationContext ic) throws Exception {
        System.out.println("Method invoked is: " + ic.getMethod().getName());
        return ic.proceed();
    }
}
