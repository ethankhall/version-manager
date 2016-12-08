import { NgModule } from "@angular/core";
import { HttpModule } from "@angular/http";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule } from "@angular/forms";

import { ServicesModule } from "../services/services.module";
import { magicRouting } from "./configuration.routing";
import { ConfigurationComponent } from "./configuration.component";

@NgModule({
    imports: [BrowserModule, FormsModule, HttpModule, ServicesModule, magicRouting],
    declarations: [ConfigurationComponent]
})
export class ConfigurationModule {}