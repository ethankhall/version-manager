import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router, Params } from "@angular/router";
import { ProjectApiService } from "../services/rest-api/project-api.services";
import { ProjectDetails } from "../models/crom.models";
import { AuthenticationService } from "../services/authentication.services";

@Component({
    templateUrl: "specific-project.component.html"
})
export class SpecificProjectComponent implements OnInit {

    projectDetails: ProjectDetails;
    hasAdmin: boolean = false;
    pageLoaded: boolean = false;

    constructor(private route: ActivatedRoute, private router: Router, private cromProjectService: ProjectApiService,
                private authService: AuthenticationService) {
    }

    toRepo(repoName: string): void {
        this.router.navigate([repoName], { relativeTo: this.route });
    }

    ngOnInit(): void {
        this.hasAdmin = this.authService.isLoggedIn();
        this.route.params.forEach((params: Params) => {
            let id = params["projectName"];
            this.cromProjectService.getProjectDetails(id).then(proj => {
                this.projectDetails = proj;
                this.pageLoaded = true;
            });
        });
    }
}