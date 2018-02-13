import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeRecentComponent } from './home-recent.component';

describe('HomeRecentComponent', () => {
  let component: HomeRecentComponent;
  let fixture: ComponentFixture<HomeRecentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeRecentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeRecentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
