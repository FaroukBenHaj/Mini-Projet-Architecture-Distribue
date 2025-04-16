import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReservationAiComponent } from './ReservationDomain/reservation-ai/reservation-ai.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import { EtudiantComponent } from './component/etudiant/etudiant.component';
import { NavbarComponent } from './component/shared/navbar/navbar.component';
import { FooterComponent } from './component/shared/footer/footer.component';
import { PaymentComponent } from './component/payement/payement.component';
import {HttpTokenInterceptorInterceptor} from "./interceptors/http-token-interceptor.interceptor";
import {KeycloakService} from "./Keycloak/keycloak.service";
import {RouterModule} from "@angular/router";
import {HomePageComponent} from "./component/home-page/home-page.component";
import { BlocComponent } from './component/bloc/bloc.component';
export function kcFactory(kcService: KeycloakService){
  return () => kcService.init();
}
@NgModule({
  declarations: [
    AppComponent,
    ReservationAiComponent,
    EtudiantComponent,
    NavbarComponent,
    FooterComponent,
    PaymentComponent,
    HomePageComponent,
    BlocComponent // Removed duplicate declaration
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    HttpClient,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpTokenInterceptorInterceptor,
      multi: true
    },
    {
      provide: APP_INITIALIZER,
      deps: [KeycloakService],
      useFactory: kcFactory,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
