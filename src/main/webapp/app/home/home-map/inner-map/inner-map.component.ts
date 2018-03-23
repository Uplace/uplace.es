import {Component, Input, OnInit} from '@angular/core';
import {GoogleMapsAPIWrapper} from "@agm/core";
import {Marker} from "../../../entities/marker/marker.model";
import {MarkerService} from "../../../entities/marker/marker.service";
import {HttpResponse} from "@angular/common/http";

declare var RichMarker: any;
declare var MarkerClusterer: any;

@Component({
    selector: 'up-inner-map',
    templateUrl: './inner-map.component.html',
    styles: [],
    providers: []
})
export class InnerMapComponent implements OnInit {

    markers: any = [];
    map: any;

    constructor(public _mapsWrapper: GoogleMapsAPIWrapper,
                public markersService: MarkerService) {
    }

    ngOnInit() {
        this.markersService.query().subscribe(
            (res: HttpResponse<Marker[]>) => {
                this._mapsWrapper.getNativeMap()
                    .then((map) => {
                        require('../../../../content/js/google-map-richmarker.min.js');
                        require('../../../../content/js/markerclusterer.js');
                        this.map = map;

                        res.body.forEach((marker) => {

                            console.log(marker);

                            const markerCenter = new google.maps.LatLng(marker['latitude'], marker['longitude']);

                            const markerVerified = marker['isNew'] ? '<div class="marker-verified"><i class="fa fa-check"></i></div>' : '';

                            const markerImage = marker['photo'] != null ? 'data:' + marker['photo'].photoContentType + ';base64,' + marker['photo'].photo : '';

                            const markerPrice = marker['price'] != null ? marker['price'].toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,') + '€' : 'N/D';

                            const markerContent =
                                '<div class="marker">' +
                                '<div class="marker-inner">' +
                                '<span class="marker-image" style="background-image: url(' + markerImage + ');"></span>' +
                                '</div>'
                                + markerVerified +
                                '<div class="marker-price">' + markerPrice + '</div>' +
                                '</div>';

                            let richMarker = new RichMarker({
                                id: marker['reference'],
                                data: 'Im data',
                                flat: true,
                                position: markerCenter,
                                shadow: 0,
                                content: markerContent
                            });

                            this.markers.push(richMarker);
                        });

                        new MarkerClusterer(this.map, this.markers, {
                            cssClass: 'custom-pin',
                            styles: [
                                {
                                    imagePath: '../../../../content/img/cluster.png',
                                    height: 36,
                                    width: 36
                                }
                            ]
                        });

                    });
            }
        );
    }

}
