import {UserSearch} from "./search.model";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Observable";
import {User} from "..";

@Injectable()
export class SearchService {

    private userSearchSource = new BehaviorSubject<UserSearch>({});
    userSearch: Observable<UserSearch> = this.userSearchSource.asObservable();

    constructor() { }

    changeUserSearch(userSearch: UserSearch) {
        this.userSearchSource.next(userSearch);
    }

}
