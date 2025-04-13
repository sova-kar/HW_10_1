package org.skypro.skyshop.service;

import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    public StorageService() {
        this.products = new HashMap<>();
        this.articles = new HashMap<>();
        fillTestData();
    }

    private void fillTestData() {
        final UUID canvasId = UUID.fromString("38a0a7b5-547b-43a0-99ea-96b62f60116d");
        final UUID oilId = UUID.fromString("13b941fa-067a-4da4-a77c-3064be740d2e");

        products.put(canvasId, new Product(canvasId, "Холст", 1200) {});
        products.put(oilId, new Product(oilId, "Масло", 800) {});


        articles.put(UUID.randomUUID(), new Article(UUID.randomUUID(), "Прачечная стирающая печали", "О жизни в моменте"));
        articles.put(UUID.randomUUID(), new Article(UUID.randomUUID(), "Бегущая с волками", "Женские архетипы"));
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Collection<Article> getAllArticles() {
        return articles.values();
    }

    public Collection<Searchable> getAllSearchables() {
        List<Searchable> searchables = new ArrayList<>();
        searchables.addAll(products.values());
        searchables.addAll(articles.values());
        return searchables;
    }
    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    public Product getProductOrThrow(UUID id) {
        return getProductById(id)
                .orElseThrow(() -> new NoSuchProductException("Product not found with id: " + id));
    }
}