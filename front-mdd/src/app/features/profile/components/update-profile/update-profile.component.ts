import {Component, OnInit} from '@angular/core';
import {Button} from "primeng/button";
import {DropdownModule} from "primeng/dropdown";
import {InputText} from "primeng/inputtext";
import {Message} from "primeng/message";
import {NgIf} from "@angular/common";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ProfileService} from '../../services/profile.service';
import {AuthService} from '../../../../core/auth/services/auth.service';
import {ThemeService} from '../../../themes/services/theme.service';
import {Router} from '@angular/router';
import {ModifyUserRequest} from '../../models/modifyUserRequest';

@Component({
  selector: 'app-update-profile',
  imports: [
    Button,
    DropdownModule,
    InputText,
    Message,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './update-profile.component.html',
  styleUrl: './update-profile.component.scss'
})
export class UpdateProfileComponent implements OnInit {

  formGroup!: FormGroup;
  errorMessage: string | null = null;

  public userId!: number

  constructor(
    private formBuilder: FormBuilder,
    private profileService: ProfileService,
    private authService: AuthService,
    private themeService: ThemeService,
    private router: Router,
  ) {
  }

  ngOnInit() {
    this.initFormControls()
    this.getUserId()
  }

  initFormControls() {
    this.formGroup = this.formBuilder.group({
      username: ["", Validators.required],
      email: ["", Validators.required],
    })
  }

  getUserId() {
    return this.authService.authMe().subscribe(user => {
      this.userId = user.id
      this.formGroup.patchValue({
        username: user.username,
        email: user.email
      });
    })
  }


  onSubmit() {
    if (this.formGroup.valid) {
      const modifyUserRequest: ModifyUserRequest = {
        username: this.formGroup.value.username,
        email: this.formGroup.value.email,
      }
      this.profileService.updateUser(this.userId ,modifyUserRequest).subscribe({
          next: () => {
            this.router.navigate(['/profile']);
          },
          error: (err) => {
            console.error(err);
            this.errorMessage = err.message;
          }
        }
      )
    }
  }

  onDisconnect() {
    this.authService.disconnect().subscribe({
      next: () => {
        this.router.navigate(['/auth/login']);
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

}
