/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.protocol;

import javax.net.ssl.SSLContext;
import javax.security.auth.callback.CallbackHandler;
import java.net.URI;
import java.util.Collections;
import java.util.Map;

import org.jboss.as.protocol.logging.ProtocolLogger;
import org.jboss.remoting3.Endpoint;
import org.xnio.OptionMap;

/**
 * @author Emanuel Muckenhuber
 */
public class ProtocolConnectionConfiguration {

    private static final long DEFAULT_CONNECT_TIMEOUT = 5000;
    private static final String JBOSS_CLIENT_SOCKET_BIND_ADDRESS = "jboss.management.client_socket_bind_address";

    private URI uri;
    private Endpoint endpoint;
    private OptionMap optionMap = OptionMap.EMPTY;
    private long connectionTimeout = DEFAULT_CONNECT_TIMEOUT;
    private CallbackHandler callbackHandler;
    private Map<String, String> saslOptions = Collections.emptyMap();
    private SSLContext sslContext;
    private String clientBindAddress;
    private ProtocolTimeoutHandler timeoutHandler;

    protected ProtocolConnectionConfiguration() {
        // TODO AS7-6223 propagate clientBindAddress configuration up to end user level and get rid of this system property
        this.clientBindAddress = SecurityActions.getSystemProperty(JBOSS_CLIENT_SOCKET_BIND_ADDRESS);
    }

    protected void validate() {
        if (endpoint == null) {
            throw ProtocolLogger.ROOT_LOGGER.nullVar("endpoint");
        }
        if (optionMap == null) {
            throw ProtocolLogger.ROOT_LOGGER.nullVar("optionMap");
        }
        if (uri == null) {
            throw ProtocolLogger.ROOT_LOGGER.nullVar("uri");
        }
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public OptionMap getOptionMap() {
        return optionMap;
    }

    public void setOptionMap(OptionMap optionMap) {
        this.optionMap = optionMap;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public CallbackHandler getCallbackHandler() {
        return callbackHandler;
    }

    public void setCallbackHandler(CallbackHandler callbackHandler) {
        this.callbackHandler = callbackHandler;
    }

    public Map<String, String> getSaslOptions() {
        return saslOptions;
    }

    public void setSaslOptions(Map<String, String> saslOptions) {
        this.saslOptions = saslOptions;
    }

    public SSLContext getSslContext() {
        return sslContext;
    }

    public void setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    public String getClientBindAddress() {
        return clientBindAddress;
    }

    public void setClientBindAddress(String clientBindAddress) {
        this.clientBindAddress = clientBindAddress;
    }

    public ProtocolTimeoutHandler getTimeoutHandler() {
        return timeoutHandler;
    }

    public void setTimeoutHandler(ProtocolTimeoutHandler timeoutHandler) {
        this.timeoutHandler = timeoutHandler;
    }

    public ProtocolConnectionConfiguration copy() {
        return copy(this);
    }

    public static ProtocolConnectionConfiguration create(final Endpoint endpoint, final URI uri) {
        return create(endpoint, uri, OptionMap.EMPTY);
    }

    public static ProtocolConnectionConfiguration create(final Endpoint endpoint, final URI uri, final OptionMap options) {
        final ProtocolConnectionConfiguration configuration = new ProtocolConnectionConfiguration();
        configuration.setEndpoint(endpoint);
        configuration.setUri(uri);
        configuration.setOptionMap(options);
        return configuration;
    }

    public static ProtocolConnectionConfiguration copy(final ProtocolConnectionConfiguration old) {
        ProtocolConnectionConfiguration configuration = new ProtocolConnectionConfiguration();
        configuration.uri = old.uri;
        configuration.endpoint = old.endpoint;
        configuration.optionMap = old.optionMap;
        configuration.connectionTimeout = old.connectionTimeout;
        configuration.callbackHandler = old.callbackHandler;
        configuration.saslOptions = old.saslOptions;
        configuration.sslContext = old.sslContext;
        configuration.clientBindAddress = old.clientBindAddress;
        configuration.timeoutHandler = old.timeoutHandler;
        return configuration;
    }

}
