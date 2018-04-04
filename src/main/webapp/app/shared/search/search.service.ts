import {Subject} from "rxjs/Subject";
import {UserSearch} from "./search.model";

export class SearchService {

    private userSearch: UserSearch = {};
    userSearchChanged = new Subject<UserSearch>();

    getSearch(): UserSearch {
        return this.userSearch;
    }

    setSearch(userSearch: UserSearch) {
        this.userSearch = userSearch;
        this.userSearchChanged.next(this.userSearch);
    }

    clearSearch() {
        this.userSearch = {};
        this.userSearchChanged.next(this.userSearch);
    }

}
