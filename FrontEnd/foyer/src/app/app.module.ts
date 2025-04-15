import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReservationAiComponent } from './ReservationDomain/reservation-ai/reservation-ai.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HomePageComponent } from './home-page/home-page.component';
import { EtudiantComponent } from './component/etudiant/etudiant.component';
import { NavbarComponent } from './component/shared/navbar/navbar.component';
import { FooterComponent } from './component/shared/footer/footer.component';
import { PaymentComponent } from './component/payement/payement.component';
@NgModule({
  declarations: [
    AppComponent,
    ReservationAiComponent,
    HomePageComponent,
    EtudiantComponent,
    NavbarComponent,
    FooterComponent,
    PaymentComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule ,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule

   ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
