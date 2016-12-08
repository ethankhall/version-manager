import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router, Params } from "@angular/router";
import { Version } from "../models/crom.models";
import { VersionApiService } from "../services/rest-api/version-api.services";

@Component({
    templateUrl: "version-list.component.html"
})
export class VersionListComponent implements OnInit {

    projectName: string;
    repoName: string;
    versions: Version[];
    latest: Version;

    constructor(private route: ActivatedRoute, private router: Router,
                private versionApi: VersionApiService) {
    }

    toRepo(): void {
        this.router.navigate(["/project", this.projectName, this.repoName]);
    }

    toProject(): void {
        this.router.navigate(["/project", this.projectName]);
    }

    toVersion(id: String): void {
        this.router.navigate([id], { relativeTo: this.route });
    }

    ngOnInit(): void {
        this.route.params.forEach((params: Params) => {
            this.projectName = params["projectName"];
            this.repoName = params["repoName"];
        });


        this.versionApi.findAllVersions(this.projectName, this.repoName).then(details => {
            this.versions = details.versions;
            this.latest = details.latest;
        });
    }
}