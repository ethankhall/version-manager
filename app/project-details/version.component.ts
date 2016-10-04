import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router, Params } from "@angular/router";
import { RepoApiService } from "../services/rest-api/repo-api.services";
import { RepoDetails, Version } from "../models/crom.models";
import { VersionApiService } from "../services/rest-api/version-api.services";

@Component({
    moduleId: module.id,
    templateUrl: "version.component.html"
})
export class VersionComponent implements OnInit {

    projectName: string;
    repoName: string;
    repoDetails: RepoDetails;
    versionId: string;
    version: Version;

    constructor(private route: ActivatedRoute, private router: Router, private repoApi: RepoApiService,
                private versionApi: VersionApiService) {
    }

    toRepo(): void {
        this.router.navigate(["/project", this.projectName, this.repoName]);
    }

    toProject(): void {
        this.router.navigate(["/project", this.projectName]);
    }

    toVersionList(): void {
        this.router.navigate(["/project", this.projectName, this.repoName, "version"]);
    }

    ngOnInit(): void {
        this.route.parent.params.forEach((params: Params) => {
            this.projectName = params["projectName"];
            this.repoName = params["repoName"];
        });

        this.route.params.forEach((params: Params) => {
            this.versionId = params["versionId"];
        });

        this.repoApi.getRepoDetails(this.projectName, this.repoName).then(details => this.repoDetails = details);

        this.versionApi.findVersion(this.projectName, this.repoName, this.versionId).then(version => this.version = version)
    }
}