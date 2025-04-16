import { TestBed } from '@angular/core/testing';
import { PaymentService } from './payement.service'; // Adjust the path as needed



describe('PayementService', () => {
  let service: PaymentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PaymentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
