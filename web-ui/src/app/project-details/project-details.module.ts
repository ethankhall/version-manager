import { NgModule } from "@angular/core";
import { HttpModule } from "@angular/http";
import { BrowserModule } from "@angular/platform-browser";

import { ProjectListComponent } from "./project-list.component";
import { RepoComponent } from "./repo.component";
import { ServicesModule } from "../services/services.module";
import { projectRouting } from "./project-details.routing";
import { VersionComponent } from "./version.component";
import { SpecificProjectComponent } from "./specific-project.component";
import { VersionListComponent } from "./version-list.component";

@NgModule({
    imports: [BrowserModule, HttpModule, ServicesModule, projectRouting],
    declarations: [ProjectListComponent, RepoComponent, VersionComponent, SpecificProjectComponent, VersionListComponent]
})
export class ProjectDetailsModule {}