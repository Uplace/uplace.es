import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeTeamComponent } from './home-team.component';

describe('HomeTeamComponent', () => {
  let component: HomeTeamComponent;
  let fixture: ComponentFixture<HomeTeamComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeTeamComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeTeamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
