import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ReservationAiComponent } from './ReservationDomain/reservation-ai/reservation-ai.component';

const routes: Routes = [
  { path: 'reservation-ai', component: ReservationAiComponent },
  { path: '', redirectTo: '/reservation-ai', pathMatch: 'full' }, // Optional: Redirect root to reservation-ai
  { path: '**', redirectTo: '/reservation-ai' } // Optional: Handle any undefined routes
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
