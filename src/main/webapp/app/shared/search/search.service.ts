import {UserCriteria} from './user-criteria.model';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class SearchService {

    private userCriteriaSource = new BehaviorSubject<UserCriteria>({});
    userCriteria: Observable<UserCriteria> = this.userCriteriaSource.asObservable();

    constructor() {}

    changeUserCriteria(userCriteria: UserCriteria) {
        this.userCriteriaSource.next(userCriteria);
    }

}
