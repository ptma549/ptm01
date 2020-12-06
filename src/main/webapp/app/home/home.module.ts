import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Ptm01SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [Ptm01SharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class Ptm01HomeModule {}
