import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router, Params } from "@angular/router";
import { RepoApiService } from "../services/rest-api/repo-api.services";
import { RepoDetails, Version } from "../models/crom.models";
import { VersionApiService } from "../services/rest-api/version-api.services";

@Component({
    templateUrl: "repo.component.html"
})
export class RepoComponent implements OnInit {

    projectName: string;
    repoName: string;
    repoDetails: RepoDetails;
    latestVersion: Version;

    constructor(private route: ActivatedRoute, private router: Router, private repoApi: RepoApiService,
                private versionApi: VersionApiService) {
    }

    toVersionList(): void {
        this.router.navigate(["version"], { relativeTo: this.route });
    }

    toRepo(): void {
        this.router.navigate(["/project", this.projectName, this.repoName]);
    }

    toProject(): void {
        this.router.navigate(["/project", this.projectName]);
    }

    toVersion(id: String): void {
        this.router.navigate(["version", id], { relativeTo: this.route });
    }

    ngOnInit(): void {
        this.route.parent.params.forEach((params: Params) => {
            this.projectName = params["projectName"];
        });
        this.route.params.forEach((params: Params) => {
            this.repoName = params["repoName"];
        });

        this.repoApi.getRepoDetails(this.projectName, this.repoName).then(details => this.repoDetails = details);

        this.versionApi.findAllVersions(this.projectName, this.repoName).then(details => this.latestVersion = details.latest);
    }
}