package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.service.FilterService;
import com.arnaugarcia.uplace.service.dto.FilterDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FilterResource {

    private FilterService filterService;

    public FilterResource(FilterService filterService) {
        this.filterService = filterService;
    }

    /*@GetMapping("/filters")
    public FilterDTO getFilters() {
        return filterService.getFilters();
    }*/
}
