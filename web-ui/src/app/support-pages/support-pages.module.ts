import { NgModule } from "@angular/core";
import { HttpModule } from "@angular/http";
import { BrowserModule } from "@angular/platform-browser";

import { ServicesModule } from "../services/services.module";
import { supportRouting } from "./support-pages.routing";
import { AboutPageComponent } from "./about-page.component";

@NgModule({
    imports: [BrowserModule, HttpModule, ServicesModule, supportRouting],
    declarations: [AboutPageComponent]
})
export class SupportPagesModule {}