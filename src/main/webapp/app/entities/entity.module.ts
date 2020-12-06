import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'job',
        loadChildren: () => import('./job/job.module').then(m => m.Ptm01JobModule),
      },
      {
        path: 'client',
        loadChildren: () => import('./client/client.module').then(m => m.Ptm01ClientModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class Ptm01EntityModule {}
