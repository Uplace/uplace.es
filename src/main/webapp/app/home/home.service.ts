import {EventEmitter, Injectable, Output} from '@angular/core';

@Injectable()
export class HomeService {

  isFilterOpen = false;

  @Output() change: EventEmitter<boolean> = new EventEmitter();

  constructor() { }

  toggle() {
    this.isFilterOpen = !this.isFilterOpen;
    this.change.emit(this.isFilterOpen);
  }
}
