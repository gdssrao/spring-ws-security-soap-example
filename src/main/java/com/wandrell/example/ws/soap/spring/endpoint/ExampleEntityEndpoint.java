/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2015 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.wandrell.example.ws.soap.spring.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.wandrell.example.ws.generated.entity.Entity;
import com.wandrell.example.ws.generated.entity.GetEntityRequest;
import com.wandrell.example.ws.generated.entity.GetEntityResponse;
import com.wandrell.example.ws.soap.spring.model.ExampleEntity;
import com.wandrell.example.ws.soap.spring.repository.ExampleEntityRepository;

/**
 * Web service endpoint for {@link ExampleEntity}.
 */
@Endpoint
public class ExampleEntityEndpoint {

    private static final String LOCALPART = "getEntityRequest";
    private static final String NAMESPACE_URI = "http://wandrell.com/example/ws/entity";
    /**
     * Repository for the {@code ExampleEntity} instances handled by the
     * service.
     * <p>
     * This is injected by Spring.
     */
    private final ExampleEntityRepository entityRepository;

    /**
     * Constructs a {@code ExampleEntityEndpoint}.
     *
     * @param repository
     *            the repository for the {@code ExampleEntity} instances
     */
    @Autowired
    public ExampleEntityEndpoint(final ExampleEntityRepository repository) {
        this.entityRepository = repository;
    }

    /**
     * Acquires a {@code ExampleEntity} through a SOAP request.
     * <p>
     * The entity should be transformed from the {@link ExampleEntity} instance
     * returned by the repository to the {@link Entity} used by the SOAP
     * classes.
     *
     * @param request
     *            a SOAP request for the entity
     * @return a SOAP response with the entity
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = LOCALPART)
    @ResponsePayload
    public final GetEntityResponse getEntity(final GetEntityRequest request) {
        final GetEntityResponse response; // SOAP response with the result
        final ExampleEntity entity;          // Found entity
        final Entity entityResponse;      // Entity to return

        response = new GetEntityResponse();

        entity = entityRepository.findOne(request.getId());

        if (entity != null) {
            entityResponse = new Entity();
            entityResponse.setId(entity.getId());
            entityResponse.setName(entity.getName());
            response.setEntity(entityResponse);
        }

        return response;
    }

}
