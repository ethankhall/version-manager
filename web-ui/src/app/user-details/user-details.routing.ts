import { Routes, RouterModule } from "@angular/router";
import { ModuleWithProviders } from "@angular/core";
import { UserComponent } from "./user.component";

export const userRoutes: Routes = [{
    path: "user",
    children: [
        { path: "", component: UserComponent }
    ]
}];

export const userRouting: ModuleWithProviders = RouterModule.forChild(userRoutes);