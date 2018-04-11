import {UserSearch} from "./search.model";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Injectable} from "@angular/core";

@Injectable()
export class SearchService {

    private userSearchSource = new BehaviorSubject<UserSearch>({});
    userSearch = this.userSearchSource.asObservable();

    constructor() { }

    changeUserSearch(userSearch: UserSearch) {
        console.log('changed user search');
        console.log(this.userSearch);
        this.userSearchSource.next(userSearch);
    }

}
