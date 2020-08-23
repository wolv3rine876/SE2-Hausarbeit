package org.se2.hausarbeit.model.dto;

public class SearchRequest {
    private String[] search;

    public SearchRequest() {}
    public SearchRequest(String search) {
        setSearch(search);
    }
    public String[] getSearch() {return this.search;}
    public void setSearch(String search) {
        this.search = search.split(" ");
    }
}
