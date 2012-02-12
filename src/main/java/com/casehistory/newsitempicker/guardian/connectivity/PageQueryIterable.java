package com.casehistory.newsitempicker.guardian.connectivity;

import java.util.Iterator;

public class PageQueryIterable<T extends PaginatedSearchQuery<T>, R extends PaginatedSearchResult<T>> implements Iterable<T> {

    private final R tagSearchResult;

    public PageQueryIterable(R searchResult) {
        this.tagSearchResult = searchResult;
    }

    public Iterator<T> iterator() {
        return new SearchPageResultIterator();
    }

    private class SearchPageResultIterator implements Iterator<T> {

        private int currentPage = tagSearchResult.getCurrentPage();

        public boolean hasNext() {
            return currentPage < tagSearchResult.getNumberOfPages();
        }

        public T next() {
            if (!hasNext())
                throw new IllegalStateException("next called on iterator with no more pages");

            currentPage = currentPage + 1;
            return tagSearchResult.getQuery().cloneForNextPageQuery(currentPage);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
