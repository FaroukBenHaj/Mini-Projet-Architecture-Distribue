import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BlocComponent } from './bloc.component';

describe('BlocComponent', () => {
  let component: BlocComponent;
  let fixture: ComponentFixture<BlocComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BlocComponent]
    });
    fixture = TestBed.createComponent(BlocComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
