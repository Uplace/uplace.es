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

    private final MarkerService markerService;

    public MarkerResource(MarkerService markerService) {
        this.markerService = markerService;
    }

    @GetMapping("/markers")
    public List<MarkerDTO> getMarkers() {
        /*List<MarkerDTO> markerDTOList = new ArrayList<>();
        markerDTOList.add(new MarkerDTO("YUYUSB2", 47.5883, -122.303));
        markerDTOList.add(new MarkerDTO("YUYUSB2", 47.6233, -122.1952));
        markerDTOList.add(new MarkerDTO("YUYUSB2", 47.5821, -122.1858));
        markerDTOList.add(new MarkerDTO("YUYUSB2", 47.6499, -122.2769));
        markerDTOList.add(new MarkerDTO("YUYUSB2", 47.6098, -122.2247));
        markerDTOList.add(new MarkerDTO("YUYUSB2", 47.6439, -122.2203));
        markerDTOList.add(new MarkerDTO("YUYUSB2", 47.6456, -122.326));
        markerDTOList.add(new MarkerDTO("YUYUSB2", 47.612, -122.2874));
        markerDTOList.add(new MarkerDTO("YUYUSB2", 47.5901, -122.2469));
        markerDTOList.add(new MarkerDTO("YUYUSB2", 47.6106, -122.225));
        return markerDTOList;*/
        return markerService.getAllMarkers();
    }
}
