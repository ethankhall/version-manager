import { NgModule } from "@angular/core";
import { AuthenticationService } from "./authentication.services";
import { BaseUrlService } from "./rest-api/base-url.services";
import { UserService } from "./rest-api/user.services";
import { ProjectApiService } from "./rest-api/project-api.services";
import { CookieService } from "angular2-cookie/services/cookies.service";
import { RepoApiService } from "./rest-api/repo-api.services";
import { VersionApiService } from "./rest-api/version-api.services";
import { MetaDataApiService } from "./rest-api/meta-data-api.services";
import { ConstantApiService } from "./rest-api/constant-api.services";

@NgModule({
    declarations: [],
    providers: [
        BaseUrlService, UserService, ProjectApiService, CookieService,
        AuthenticationService, RepoApiService, VersionApiService, MetaDataApiService,
        ConstantApiService
    ]
})
export class ServicesModule {
}