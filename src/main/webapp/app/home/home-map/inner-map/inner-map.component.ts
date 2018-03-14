import {Component, Input, OnInit} from '@angular/core';
import {GoogleMapsAPIWrapper} from "@agm/core";
import {Marker} from "../../../entities/marker/marker.model";

declare var RichMarker: any;
declare var MarkerClusterer: any;

@Component({
    selector: 'up-inner-map',
    templateUrl: './inner-map.component.html',
    styles: [],
    providers: []
})
export class InnerMapComponent implements OnInit {

    @Input('markers') markersFromService: Marker[];
    markers: any = [];
    map: any;

    constructor(
        public _mapsWrapper: GoogleMapsAPIWrapper
    ) { }

    ngOnInit() {
        this._mapsWrapper.getNativeMap()
            .then((map)=> {
                require ('../../../../content/js/google-map-richmarker.min.js');
                require ('../../../../content/js/markerclusterer.js');
                this.map = map;

                this.markersFromService.forEach((marker) => {

                    const markerCenter = new google.maps.LatLng(marker['latitude'], marker['longitude']);

                    const markerVerified = marker['isNew'] ? '<div class="marker-verified"><i class="fa fa-check"></i></div>' : '';

                    const markerContent =
                        '<div class="marker">' +
                            '<div class="marker-inner">' +
                                '<span class="marker-image" style="background-image: url(' + marker['thumbnail'] + ');"></span>' +
                            '</div>'
                                + markerVerified +
                            '<div class="marker-price">' + marker['price'] + '</div>' +
                        '</div>';

                    const richMarker = new RichMarker({
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

}
