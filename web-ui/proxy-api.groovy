#!/usr/bin/env groovy
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.http.HttpObject
@Grab("org.littleshoot:littleproxy:1.1.0")
import io.netty.handler.codec.http.HttpRequest
import io.netty.handler.codec.http.HttpResponse
import org.littleshoot.proxy.HttpFilters
import org.littleshoot.proxy.HttpFiltersAdapter
import org.littleshoot.proxy.HttpFiltersSourceAdapter
import org.littleshoot.proxy.impl.DefaultHttpProxyServer

import java.util.regex.Pattern

Pattern REQUEST_TARGET_ORIGIN_FORM_PREFIX = Pattern.compile("/[^/]");
println("Acception requests on 0.0.0.0:4321")
DefaultHttpProxyServer.bootstrap()
        .withPort(4321)
        .withFiltersSource(new HttpFiltersSourceAdapter() {
    public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
        return new HttpFiltersAdapter(originalRequest) {
            @Override
            public HttpResponse clientToProxyRequest(HttpObject httpObject) {
                if (httpObject instanceof HttpRequest) {
                    HttpRequest httpRequest = (HttpRequest) httpObject;
                    if (REQUEST_TARGET_ORIGIN_FORM_PREFIX.matcher(httpRequest.getUri()).lookingAt()) {
                        String uriRemote = "http://api.crom.tech" + httpRequest.getUri();
                        httpRequest.setUri(uriRemote);
                    }
                    httpRequest.headers().remove("Origin")
                    httpRequest.headers().remove("Referer")
                }
                return null;
            }

            @Override
            public HttpObject serverToProxyResponse(HttpObject httpObject) {
                if (httpObject instanceof HttpResponse) {
                    def httpResponse = (HttpResponse) httpObject
                    httpResponse.headers().add("Access-Control-Allow-Origin", "*");
                    httpResponse.headers().add("Access-Control-Allow-Credentials", "true");
                    httpResponse.headers().add("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
                    httpResponse.headers().add("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, x-auth-token");

                }
                return httpObject;
            }
        };
    }
}).start();
