import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './component/home-page/home-page.component';
import { EtudiantComponent } from './component/etudiant/etudiant.component';


const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'etudiants', component: EtudiantComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }