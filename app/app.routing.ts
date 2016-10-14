import { Routes, RouterModule } from "@angular/router";
import { ModuleWithProviders } from "@angular/core";
import { HomePageComponent } from "./home-page.component";
import { projectRoutes } from "./project-details/project-details.routing";
import { userRoutes } from "./user-details/user-details.routing";
import { magicRoutes } from "./private-configuration/configuration.routing";
import { supportRoutes } from "./support-pages/support-pages.routing";

const appRoutes: Routes = [
    ...projectRoutes,
    ...userRoutes,
    ...magicRoutes,
    ...supportRoutes,
    {
        path: "",
        component: HomePageComponent
    }
];

export const appRoutingProviders: any[] = [];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
