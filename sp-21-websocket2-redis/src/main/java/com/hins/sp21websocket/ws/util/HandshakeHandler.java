package com.hins.sp21websocket.ws.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.support.AbstractHandshakeHandler;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * A default {@link org.springframework.web.socket.server.HandshakeHandler} implementation,
 * extending {@link AbstractHandshakeHandler} with Servlet-specific initialization support.
 * See {@link AbstractHandshakeHandler}'s javadoc for details on supported servers etc.
 *
 * @author Rossen Stoyanchev
 * @author Juergen Hoeller
 * @since 4.0
 */
public class HandshakeHandler extends AbstractHandshakeHandler implements ServletContextAware {

    public HandshakeHandler() {
    }

    public HandshakeHandler(RequestUpgradeStrategy requestUpgradeStrategy) {
        super(requestUpgradeStrategy);
    }


    @Override
    public void setServletContext(ServletContext servletContext) {
        RequestUpgradeStrategy strategy = getRequestUpgradeStrategy();
        if (strategy instanceof ServletContextAware) {
            ((ServletContextAware) strategy).setServletContext(servletContext);
        }
    }


    @Override
    protected void handleInvalidUpgradeHeader(ServerHttpRequest request, ServerHttpResponse response) throws IOException {
        if (logger.isWarnEnabled()) {
            logger.warn("Handshake failed due to invalid Upgrade header: " + request.getHeaders().getUpgrade());
        }
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        response.getBody().write("Can \"Upgrade\" only to \"WebSocket\".".getBytes(StandardCharsets.UTF_8));
    }
}