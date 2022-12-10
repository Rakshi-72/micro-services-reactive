package com.orderservice.orderservice.clients;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.orderservice.orderservice.dto.ProductDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {

    private final WebClient client;

    // Creating a WebClient object and setting the base url to the value of the
    // property
    // product.service.url.
    public ProductClient(@Value("${product.service.url}") String url) {
        this.client = WebClient
                .builder()
                .baseUrl(url)
                .build();
    }

    /**
     * "Get the product with the given id and return it as a Mono of ProductDto."
     * 
     * The first thing we do is call the get() method on the WebClient. This returns
     * a
     * WebClient.RequestHeadersSpec
     * 
     * @param id The id of the product to retrieve
     * @return A Mono of ProductDto
     */
    public Mono<ProductDto> getProductById(String id) {
        return this.client
                .get()
                .uri("{id}", id)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

    public Flux<ProductDto> getAllProducts() {
        return this.client
                .get()
                .uri(URI.create(
                        "http://localhost:8091/api/product/all"))
                .retrieve()
                .bodyToFlux(ProductDto.class);
    }

}
