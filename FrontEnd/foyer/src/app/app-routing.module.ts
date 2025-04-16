import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ReservationAiComponent } from './ReservationDomain/reservation-ai/reservation-ai.component';
import { HomePageComponent } from './home-page/home-page.component';
import { EtudiantComponent } from './component/etudiant/etudiant.component';
import { PaymentComponent } from './component/payement/payement.component';

const routes: Routes = [
  { path: 'home', component: HomePageComponent },
  { path: 'etudiants', component: EtudiantComponent },
  { path: 'paiements', component: PaymentComponent },
  { path: 'reservation-ai', component: ReservationAiComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' }, // Redirect root to /home
  { path: '**', redirectTo: '/home' } // Handle any undefined routes

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
