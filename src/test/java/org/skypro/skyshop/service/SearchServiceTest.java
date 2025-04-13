package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    void search_shouldReturnEmptyList_whenNoObjectsInStorage() {
        when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());

        Collection<org.skypro.skyshop.model.search.SearchResult> result = searchService.search("test");

        assertTrue(result.isEmpty());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_shouldReturnEmptyList_whenNoMatchingObjects() {
        Searchable mockSearchable = mock(Searchable.class);
        when(mockSearchable.getSearchTerm()).thenReturn("other");
        when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(mockSearchable));

        Collection<org.skypro.skyshop.model.search.SearchResult> result = searchService.search("test");
        assertTrue(result.isEmpty());
    }

    @Test
    void search_shouldReturnResults_whenMatchingObjectsExist() {
        Searchable mockSearchable1 = mock(Searchable.class);
        when(mockSearchable1.getSearchTerm()).thenReturn("test product");
        when(mockSearchable1.getId()).thenReturn(UUID.randomUUID());
        when(mockSearchable1.getContentType()).thenReturn("Product");

        Searchable mockSearchable2 = mock(Searchable.class);
        when(mockSearchable2.getSearchTerm()).thenReturn("other");

        when(storageService.getAllSearchables()).thenReturn(Arrays.asList(mockSearchable1, mockSearchable2));

        Collection<org.skypro.skyshop.model.search.SearchResult> result = searchService.search("test");

        assertEquals(1, result.size());
        SearchResult searchResult = result.iterator().next();
        assertEquals("test product", searchResult.getName());
    }
}
