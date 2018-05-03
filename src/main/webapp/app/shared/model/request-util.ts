import {HttpParams} from '@angular/common/http';
import {UserSearch} from "../search/search.model";

export const createRequestOption = (req?: any, userSearch?: UserSearch): HttpParams => {
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
        if (req.sort) {
            req.sort.forEach((val) => {
                options = options.append('sort', val);
            });
        }

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

