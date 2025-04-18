package org.skypro.skyshop.controller;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.CounterService;
import org.skypro.skyshop.service.StorageService;
import org.skypro.skyshop.service.SearchService;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/shop")
public class ShopController {
    private final SearchService searchService;
    private final StorageService storageService;
    private final BasketService basketService;
    private final CounterService counterService;

    public ShopController(StorageService storageService, SearchService searchService, BasketService basketService, CounterService counterService) {
        this.storageService = storageService;
        this.searchService = searchService;
        this.basketService = basketService;
        this.counterService = counterService;
    }

    @GetMapping("/products")
    public Collection<Product> getAllProducts() {
        return storageService.getAllProducts();
    }

    @GetMapping("/articles")
    public Collection<Article> getAllArticles() {
        return storageService.getAllArticles();
    }

    @GetMapping("/search")
    public Collection<SearchResult> search(@RequestParam String pattern) {
        return searchService.search(pattern);
    }

    @GetMapping("/basket/{id}")
    public String addProduct(@PathVariable("id") UUID id) {
        basketService.addProductToBasket(id);
        return "Продукт успешно добавлен";
    }

    @GetMapping("/basket")
    public UserBasket getUserBasket() {
        return basketService.getUserBasket();
    }
    @GetMapping("/counter")
    public String count() {

        StopWatch watch = StopWatch.createStarted();
        counterService.increment();
        watch.stop();

        String response = "Количество запросов: " + counterService.getCount();
        String time = "Время обработки запроса: " + watch.getTime()
                + " миллисекунд";
        return response + "\n" + time;
    }
}
