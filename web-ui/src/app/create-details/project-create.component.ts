import { Component } from "@angular/core";
import { ProjectApiService, ProjectCreateResponse } from "../services/rest-api/project-api.services";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Router } from "@angular/router";

@Component({
    templateUrl: "project-create.component.html"
})
export class ProjectCreateComponent {

    projectName: String = "";
    form: FormGroup;

    constructor(private projectService: ProjectApiService, private router: Router, fb: FormBuilder) {
        this.form = fb.group({
            "projectName":["", Validators.required]
        });

    }

    onSubmit(): void {
        this.projectService.createProject(this.form.value.projectName).then(result => {
            if(result == ProjectCreateResponse.REJECTED) {
                window.alert("Unable to create project with name " + this.form.value.projectName);
            } else {
                this.router.navigate(['/project', this.form.value.projectName])
            }
        });
    }
}