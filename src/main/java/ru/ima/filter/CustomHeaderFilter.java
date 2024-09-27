package ru.ima.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class CustomHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.addHeader(
                "Access-Control-Allow-Origin",
                "http://localhost:5173");
        httpResponse.addHeader(
                "Access-Control-Request-Headers",
                "Origin, X-Api-Key, X-Requested-With, Content-Type, Accept, Authorization"
        );
        httpResponse.addHeader(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD"
        );
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
