import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { ProjectApiService } from "../services/rest-api/project-api.services";
import { AuthenticationService } from "../services/authentication.services";
import { UserService } from "../services/rest-api/user.services";

@Component({
    templateUrl: "project-list.component.html"
})
export class ProjectListComponent implements OnInit {

    projects: string[] = [];
    loggedIn: boolean = false;

    constructor(private router: Router, private cromProjectService: ProjectApiService, private userService: UserService,
                private authService: AuthenticationService) {
    }

    toProject(name: String) {
        this.router.navigate(["/project", name]);
    }

    ngOnInit(): void {
        this.projectsToShow().then(projects => this.projects = projects);
        this.loggedIn = this.authService.isLoggedIn();
    }

    private projectsToShow(): Promise<string[]> {
        if (this.authService.isLoggedIn()) {
            return this.userService.getUserWatches();
        } else {
            return this.cromProjectService.getProjects(0);
        }
    }
}