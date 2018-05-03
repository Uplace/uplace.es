import {HttpParams} from '@angular/common/http';
import {UserSearch} from "../search/search.model";

export const createRequestOption = (req?: any, criteria?: UserSearch): HttpParams => {
    let options: HttpParams = new HttpParams();
    if (req) {
        Object.keys(req).forEach((key) => {
            if (key !== 'sort') {
                options = options.set(key, req[key]);
            }
        });
        if (req.sort) {
            req.sort.forEach((val) => {
                options = options.append('sort', val);
            });
        }
    }

    if (criteria) {
        Object.keys(criteria).forEach((key) => {
            console.log("Criteria selected " + key);
        });

        if (criteria.category) {
            options = options.append("category.equals", criteria.category);
        }

        if (criteria.city) {
            options = options.append("city.equals", criteria.city);
        }

        if (criteria.keywords) {
            options = options.append("keywords.contains", criteria.keywords);
        }

        if (criteria.priceFrom) {
            options = options.append("price.greaterOrEqualThan", String(criteria.priceFrom));
        }

        if (criteria.priceTo) {
            options = options.append("price.lessOrEqualThan", String(criteria.priceTo));
        }

        console.log(options);
        return options;
    }
};

/*
export const createRequestOption = (req?: any, ): HttpParams => {
    let options: HttpParams = new HttpParams();

    if (req) {
        Object.keys(req).forEach((key) => {
            if (key !== 'sort') {
                options = options.set(key, req[key]);
            }
        });
        if (req.sort) {
            req.sort.forEach((val) => {
                options = options.append('sort', val);
            });
        }
    }

    if (userSearch) {
        Object.keys(userSearch).forEach((key) => {
            console.log(key);
        });
        /!*if (req.sort) {
            req.sort.forEach((val) => {
                options = options.append('sort', val);
            });
        }*!/
    } else {

    }
    return options;
};
*/

