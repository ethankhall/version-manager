import { Routes, RouterModule } from "@angular/router";
import { ModuleWithProviders } from "@angular/core";
import { AboutPageComponent } from "./about-page.component";

export const supportRoutes: Routes = [{
    path: "about",
    component: AboutPageComponent
}];

export const supportRouting: ModuleWithProviders = RouterModule.forChild(supportRoutes);