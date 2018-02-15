package com.arnaugarcia.uplace.web.rest;

import com.arnaugarcia.uplace.service.MarkerService;
import com.arnaugarcia.uplace.service.dto.MarkerDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MarkerResource {

    private MarkerService markerService;

    public MarkerResource(MarkerService markerService) {
        this.markerService = markerService;
    }

    @GetMapping("/markers")
    public List<MarkerDTO> getMarkers() {
        return markerService.getAllMarkers();
    }
}
