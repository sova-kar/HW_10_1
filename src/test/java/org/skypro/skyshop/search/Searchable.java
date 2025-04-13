package org.skypro.skyshop.search;

import java.util.UUID;

public interface Searchable {
    UUID getId();
    String getSearchTerm();
    String getContentType();
}