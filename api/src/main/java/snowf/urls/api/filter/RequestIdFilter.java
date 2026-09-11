package snowf.urls.api.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import snowf.urls.api.utils.RequestIdWrapper;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Integer.MIN_VALUE)
public class RequestIdFilter implements Filter {

    private static final String REQUEST_ID_NAME = "X-Request-ID";

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest servletRequest) {

            String currentReqIdHeader = servletRequest.getHeader(REQUEST_ID_NAME);
            if (currentReqIdHeader == null || currentReqIdHeader.trim().isEmpty()) {
                currentReqIdHeader = UUID.randomUUID().toString();
            }

            RequestIdWrapper requestIdWrapper = new RequestIdWrapper(
                    servletRequest,
                    currentReqIdHeader,
                    REQUEST_ID_NAME);

            chain.doFilter(requestIdWrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
