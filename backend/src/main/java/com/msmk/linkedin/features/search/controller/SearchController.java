package com.msmk.linkedin.features.search.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msmk.linkedin.features.authentication.model.AuthenticationUser;
import com.msmk.linkedin.features.search.service.SearchService;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/users")
    public List<AuthenticationUser> searchUsers(@RequestParam String query) {
        return searchService.searchUsers(query);
    }
}
