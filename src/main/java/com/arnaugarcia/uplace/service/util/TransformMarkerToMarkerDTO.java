package com.arnaugarcia.uplace.service.util;

import com.arnaugarcia.uplace.domain.Marker;
import com.arnaugarcia.uplace.service.dto.MarkerDTO;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Function;

public class TransformMarkerToMarkerDTO {

   public static Function<Marker, MarkerDTO> markerToMarkerDTO = marker -> {
       MarkerDTO markerDTO = new MarkerDTO();

       // Transform the date by period of today vs one month
       if (marker.getDate() != null) {
           markerDTO.setNew(isNew(marker.getDate().toLocalDate()));
       } else {
           markerDTO.setNew(false);
       }

       markerDTO.setDate(marker.getDate());
       markerDTO.setLatitude(marker.getLatitude());
       markerDTO.setLongitude(marker.getLongitude());
       markerDTO.setPropertyType(marker.getPropertyType());
       markerDTO.setPropertyReference(marker.getPropertyReference());

       // Transform price
       markerDTO.setPrice(transformPrice(marker));
       markerDTO.setPhoto(null);
       return markerDTO;
   };

    private static Boolean isNew(LocalDate date) {
        Period period = Period.between(date, LocalDate.now());
        return period.getMonths() < 1;
    }

    private static Double transformPrice(Marker marker) {
        switch (marker.getTransactionType()) {
            case BUY:
                return marker.getPriceSell();
            case RENT:
                return marker.getPriceRent();
            case RENT_BUY:
                return marker.getPriceSell();
            case TRANSFER:
                return marker.getPriceTransfer();
        }
        return null;
    }
}
