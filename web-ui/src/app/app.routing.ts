import { Routes, RouterModule } from "@angular/router";
import { ModuleWithProviders } from "@angular/core";
import { projectRoutes } from "./project-details/project-details.routing";
import { userRoutes } from "./user-details/user-details.routing";
import { magicRoutes } from "./private-configuration/configuration.routing";
import { supportRoutes } from "./support-pages/support-pages.routing";
import { createRoutes } from "./create-details/create-details.routing";

const appRoutes: Routes = [
    ...projectRoutes,
    ...userRoutes,
    ...magicRoutes,
    ...supportRoutes,
    ...createRoutes,
    {
        path: "",
        redirectTo: '/project', pathMatch: 'full'
    }
];

export const appRoutingProviders: any[] = [];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
