import { TestBed } from '@angular/core/testing';

import { HttpTokenInterceptorInterceptor } from './http-token-interceptor.interceptor';

describe('HttpTokenInterceptorInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      HttpTokenInterceptorInterceptor
    ]
  }));

  it('should be created', () => {
    const interceptor: HttpTokenInterceptorInterceptor = TestBed.inject(HttpTokenInterceptorInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
