package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    @Test
    void addProductToBasket_shouldThrowWhenProductNotFound() {
        UUID testProductId = UUID.randomUUID();
        when(storageService.getProductById(testProductId))
                .thenReturn(Optional.empty());
        assertThrows(NoSuchProductException.class, () ->
                basketService.addProductToBasket(testProductId)
        );
    }

    @Test
    void addProductToBasket_shouldAddProductWhenExists() {
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Test Product", 100) {};
        when(storageService.getProductById(productId))
                .thenReturn(Optional.of(product));

        basketService.addProductToBasket(productId);
        verify(productBasket).addProduct(productId);
    }
    @Test
    void getUserBasket_shouldReturnEmptyBasket_whenBasketIsEmpty() {
        when(productBasket.getBasketItems()).thenReturn(Collections.emptyMap());
        UserBasket result = basketService.getUserBasket();

        assertTrue(result.getItems().isEmpty());
        assertEquals(0, result.getTotal());
    }

    @Test
    void getUserBasket_shouldReturnCorrectBasket_whenBasketHasItems() {
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Test", 100) {};

        when(productBasket.getBasketItems()).thenReturn(Map.of(productId, 2));
        when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        UserBasket result = basketService.getUserBasket();

        assertEquals(1, result.getItems().size());
        BasketItem item = result.getItems().get(0);
        assertEquals(product, item.getProduct());
        assertEquals(2, item.getQuantity());
        assertEquals(200, result.getTotal());
    }
}