import { Component, OnInit } from '@angular/core';
import {GoogleMapsAPIWrapper} from "@agm/core";
import {MarkerService} from "../../../entities/marker/marker.service";
import {MarkerModel} from "../../../entities/marker/marker.model";

declare const InfoBox: any;
declare const MarkerClusterer: any;
declare const RichMarker: any;

@Component({
  selector: 'inner-map',
  templateUrl: './inner-map.component.html',
  styles: []
})
export class InnerMapComponent implements OnInit {

    map: any;
    markers: MarkerModel[] = [];

  constructor(
      private _wrapper: GoogleMapsAPIWrapper,
      private markersService: MarkerService,
  ) {
      this._wrapper.getNativeMap().then((m) => {

          // Imports the required JS when google.maps its loaded and can be accesed
          // I'm dirty AF
          require("../../../../content/js/google-map-richmarker.min.js");
          require("../../../../content/js/google-map-markerclusterer.js");
          require("../../../../content/js/google-map-infobox.min.js");

          this.map = m;

          // Inits the infobox
          /*var infobox = new InfoBox({
              content: 'empty',
              disableAutoPan: false,
              maxWidth: 0,
              pixelOffset: new google.maps.Size(-250, -330),
              zIndex: null,
              closeBoxURL: '',
              infoBoxClearance: new google.maps.Size(1, 1),
              isHidden: false,
              isOpen: false,
              pane: 'floatPane',
              enableEventPropagation: false
          });

          // Adds a listener for closing
          infobox.addListener('domready', () => {
              $('.infobox-close').on('click',  () =>  {
                  infobox.close(this.map, this);
                  infobox.isOpen = false;
              });
          });*/

          this.markersService.query().subscribe((data) => {
              var result = data.json;

              // For each marker sets the content and configures the infobox
              result.forEach((marker) => {

                  const markerCenter = new google.maps.LatLng(marker['latitude'], marker['longitude']);

                  const markerContent =
                      '<div class="marker">' +
                      '<div class="marker-inner">' +
                      '<span class="marker-image" style="background-image: url(' + marker['thumbnail'] + ');"></span>' +
                      '</div>' +
                      '<div class="marker-verified"><i class="fa fa-check"></i></div>' +
                      '<div class="marker-price">1000</div>' +
                      '</div>';

                  // Creates the RichMarker and sets the content
                  const richMarker = new RichMarker({
                      id: marker['reference'],
                      data: 'Im data',
                      flat: true,
                      position: markerCenter,
                      shadow: 0,
                      content: markerContent
                  });

                  this.markers.push(richMarker);

                  google.maps.event.addListener(richMarker, 'click',  () =>  {
                      var c = '<div class="infobox"><div class="infobox-close"><i class="fa fa-close"></i></div>' +
                          '<h3 class="infobox-title"><a href="listing.html">This is a infobox</a></h3>' +
                          '<h4 class="infobox-address">Calle de la piruleta</h4>' +
                          '<div class="infobox-content">' +
                          '<div class="infobox-image"><ul><li><a href="#"><i class="fa fa-facebook"></i></a></li><li><a href="#"><i class="fa fa-twitter"></i></a></li><li><a href="#"><i class="fa fa-google"></i></a></li></ul></div>' +
                          '<div class="infobox-body"><div class="infobox-body-inner"><div class="infobox-price">$119.90</div><div class="infobox-category tag">Restaurant</div><p><strong>Class aptent taciti sociosqu ad litora torquent per conubia nostra.</strong></p><p>Etiam vehicula nisi sem, a volutpat diam lacinia eu. Vivamus lorem est, eleifend et urna sed.</p></div>' +
                          '<div class="infobox-more"><a href="#">Read More <i class="fa fa-chevron-right"></i></a></div>' +
                          '</div>' +
                          '<div>';

                      /*if (!infobox.isOpen) {
                          infobox.setContent(c);
                          infobox.open(this.map, this);
                          infobox.isOpen = true;
                          infobox.markerId = marker.propertyReference;
                      } else {
                          if (infobox.markerId === marker.propertyReference) {
                              infobox.close(this.map, this);
                              infobox.isOpen = false;
                          } else {
                              infobox.close(this.map, this);
                              infobox.isOpen = false;

                              infobox.setContent(c);
                              infobox.open(this.map, this);
                              infobox.isOpen = true;
                              infobox.markerId = marker.propertyReference;
                          }
                      }*/
                  });

              });

              new MarkerClusterer(this.map, this.markers, {
                  styles: [
                      {
                          url: '../../../../content/img/cluster.png',
                          height: 36,
                          width: 36
                      }
                  ]
              });

          });
      });
  }

  ngOnInit() {
      console.log('Custom map loaded!');
  }

}
