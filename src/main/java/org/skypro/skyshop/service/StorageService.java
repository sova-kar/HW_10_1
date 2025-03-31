package org.skypro.skyshop.service;

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

        products.put(UUID.randomUUID(), new Product(UUID.randomUUID(), "Холст", 1200) {});
        products.put(UUID.randomUUID(), new Product(UUID.randomUUID(), "Масло", 800) {});


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
}