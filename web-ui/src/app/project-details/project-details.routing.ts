import { Routes, RouterModule } from "@angular/router";
import { ModuleWithProviders } from "@angular/core";
import { RepoComponent } from "./repo.component";
import { ProjectListComponent } from "./project-list.component";
import { VersionComponent } from "./version.component";
import { SpecificProjectComponent } from "./specific-project.component";
import { VersionListComponent } from "./version-list.component";

export const projectRoutes: Routes = [{
    path: "project",
    children: [
        { path: "", component: ProjectListComponent },
        {
            path: ':projectName',
            children: [
                { path: "", component: SpecificProjectComponent },
                {
                    path: ":repoName",
                    children: [
                        {
                            path: "",
                            component: RepoComponent
                        },
                        {
                            path: "version",
                            children: [
                                { path: "", component: VersionListComponent },
                                {
                                    path: ":versionId",
                                    children: [
                                        { path: "", component: VersionComponent }
                                    ]
                                }
                            ]
                        }
                    ]
                }
            ]
        }
    ]
}];

export const projectRouting: ModuleWithProviders = RouterModule.forChild(projectRoutes);