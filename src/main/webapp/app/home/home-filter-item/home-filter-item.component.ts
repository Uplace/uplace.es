import {Component, Input, OnDestroy, OnInit} from '@angular/core';

@Component({
  selector: 'app-home-filter-item',
  templateUrl: './home-filter-item.component.html',
  styleUrls: ['./home-filter-item.component.css']
})
export class HomeFilterItemComponent implements OnInit, OnDestroy {

  @Input() horizontal: boolean;

  property: any;

  constructor() { }

  ngOnInit(): void { }

  ngOnDestroy(): void {}
}
