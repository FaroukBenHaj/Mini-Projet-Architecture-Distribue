import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './component/home-page/home-page.component';
import { EtudiantComponent } from './component/etudiant/etudiant.component';
import { PaymentComponent } from './component/payement/payement.component';
import { ReservationComponent } from './component/reservation/reservation.component';


const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'etudiants', component: EtudiantComponent },
  { path: 'paiements', component: PaymentComponent },  
  { path: 'reservations', component: ReservationComponent },

  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }