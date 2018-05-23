import {Property} from "../../entities/property";
import {Establishment} from "../model/establishment.model";
import {Hotel} from "../model/hotel.model";
import {IndustrialPlant} from "../model/industrial-plant.model";
import {Building} from "../model/building.model";
import {Office} from "../model/office.model";
import {Parking} from "../model/parking.model";
import {Apartment} from "../model/apartment.model";
import {Business} from "../model/business.model";
import {Terrain} from "../model/terrain.model";

export const transformProperty = (property: any, propertyType?: string): Property => {
        let copy: Property = Object.assign({}, property);
        if (propertyType != null) {
            property.propertyType = propertyType;
        }
        switch (property.propertyType) {
            case 'Apartment':
                copy = Object.assign(new Apartment(), property);
                break;
            case 'Building':
                copy = Object.assign(new Building(), property);
                break;
            case 'Establishment':
                copy = Object.assign(new Establishment(), property);
                break;
            case 'Business':
                copy = Object.assign(new Business(), property);
                break;
            case 'Hotel':
                copy = Object.assign(new Hotel(), property);
                break;
            case 'IndustrialPlant':
                copy = Object.assign(new IndustrialPlant(), property);
                break;
            case 'Office':
                copy = Object.assign(new Office(), property);
                break;
            case 'Parking':
                copy = Object.assign(new Parking(), property);
                break;
            case 'Terrain':
                copy = Object.assign(new Terrain(), property);
        }

        return copy;

};
