import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { Ptm01SharedModule } from 'app/shared/shared.module';
import { Ptm01CoreModule } from 'app/core/core.module';
import { Ptm01AppRoutingModule } from './app-routing.module';
import { Ptm01HomeModule } from './home/home.module';
import { Ptm01EntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    Ptm01SharedModule,
    Ptm01CoreModule,
    Ptm01HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    Ptm01EntityModule,
    Ptm01AppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class Ptm01AppModule {}
