package snowf.urls.api.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class RequestIdWrapper extends HttpServletRequestWrapper {

    private final String requestIdValue;
    private final String requestIdHeaderName;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public RequestIdWrapper(
            HttpServletRequest request,
            String requestIdValue,
            String requestIdHeaderName) {
        super(request);
        this.requestIdValue = requestIdValue;
        this.requestIdHeaderName = requestIdHeaderName;
    }

    @Override
    public String getHeader(String name) {
        if (requestIdHeaderName.equalsIgnoreCase(name)) {
            return requestIdValue;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (requestIdHeaderName.equalsIgnoreCase(name)) {
            return Collections.enumeration(List.of(requestIdValue));
        }
        return super.getHeaders(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());

        if (!names.contains(requestIdHeaderName)) {
            names.add(requestIdHeaderName);
        }

        return Collections.enumeration(names);
    }
}
