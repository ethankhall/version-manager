import { Routes, RouterModule } from "@angular/router";
import { ModuleWithProviders } from "@angular/core";
import { ConfigurationComponent } from "./configuration.component";

export const magicRoutes: Routes = [{
    path: "magic",
    component: ConfigurationComponent
}];

export const magicRouting: ModuleWithProviders = RouterModule.forChild(magicRoutes);