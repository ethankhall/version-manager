import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { HttpModule } from "@angular/http";
import { routing, appRoutingProviders } from "./app.routing";
import { ProjectDetailsModule } from "./project-details/project-details.module";
import { AppComponent } from "./app.component";
import { NavBarComponent } from "./nav-bar/nav-bar.component";
import { ServicesModule } from "./services/services.module";
import { UserDetailsModule } from "./user-details/user-details.module";
import { ConfigurationModule } from "./private-configuration/configuration.module";
import { SupportPagesModule } from "./support-pages/support-pages.module";
import { FooterComponent } from "./footer/footer.component";
import { CreateDetailsModule } from "./create-details/create-details.module";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";

@NgModule({
    imports: [BrowserModule, HttpModule, routing, ProjectDetailsModule, UserDetailsModule,
        ServicesModule, ConfigurationModule, SupportPagesModule, CreateDetailsModule, FormsModule, ReactiveFormsModule],
    declarations: [AppComponent, NavBarComponent, FooterComponent],
    providers: [appRoutingProviders],
    bootstrap: [AppComponent]
})
export class AppModule {
}

