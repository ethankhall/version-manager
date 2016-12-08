import { NgModule } from "@angular/core";
import { HttpModule } from "@angular/http";
import { BrowserModule } from "@angular/platform-browser";

import { ServicesModule } from "../services/services.module";
import { userRouting } from "./user-details.routing";
import { UserComponent } from "./user.component";

@NgModule({
    imports: [BrowserModule, HttpModule, ServicesModule, userRouting],
    declarations: [UserComponent]
})
export class UserDetailsModule {}