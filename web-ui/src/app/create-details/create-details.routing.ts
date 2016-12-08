import { Routes, RouterModule } from "@angular/router";
import { ModuleWithProviders } from "@angular/core";
import { ProjectCreateComponent } from "./project-create.component";
import { CanActivateLoggedInUser } from "../services/can-activate-loggedin-user";
import { RepoCreateComponent } from "./repo-create.component";

export const createRoutes: Routes = [{
    path: "create",
    children: [
        { path: "project", component: ProjectCreateComponent, canActivate: [CanActivateLoggedInUser] },
        {
            path: "repo",
            children: [
                { path: ":projectName", component: RepoCreateComponent, canActivate: [CanActivateLoggedInUser] },
            ]
        }
    ]
}];

export const createRouting: ModuleWithProviders = RouterModule.forChild(createRoutes);