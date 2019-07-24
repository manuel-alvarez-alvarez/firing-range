package com.google.testing.security.firingrange;

import com.google.testing.security.firingrange.utils.Responses;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class ResourcesFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (httpRequest.getRequestURI().endsWith("index.html")) {
            try {
                URL resource = httpRequest.getServletContext().getResource(httpRequest.getRequestURI());
                String body = Files.lines(Paths.get(resource.toURI())).collect(Collectors.joining("\n"));
                Responses.sendNormalPage(httpResponse, body);
                return;
            } catch (Exception e) {
                // ignore it
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
