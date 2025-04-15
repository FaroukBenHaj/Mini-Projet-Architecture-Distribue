import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationAiComponent } from './reservation-ai.component';

describe('ReservationAiComponent', () => {
  let component: ReservationAiComponent;
  let fixture: ComponentFixture<ReservationAiComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReservationAiComponent]
    });
    fixture = TestBed.createComponent(ReservationAiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
