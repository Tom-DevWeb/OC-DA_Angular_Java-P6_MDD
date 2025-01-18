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
import {Password} from 'primeng/password';
import {Toast} from 'primeng/toast';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-update-profile',
  imports: [
    Button,
    DropdownModule,
    InputText,
    Message,
    NgIf,
    ReactiveFormsModule,
    Password,
    Toast
  ],
  templateUrl: './update-profile.component.html',
  styleUrl: './update-profile.component.scss',
  providers: [MessageService]
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
    private messageService: MessageService,
  ) {
  }

  ngOnInit() {
    this.initFormControls()
    this.getUserId()
  }

  initFormControls() {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>])[A-Za-z\d!@#$%^&*(),.?":{}|<>]{8,}$/

    this.formGroup = this.formBuilder.group({
      username: ["", Validators.required],
      email: ["", Validators.required],
      password: [null, [Validators.pattern(passwordRegex)]],
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
        password: this.formGroup.value.password,
      }
      this.profileService.updateUser(this.userId ,modifyUserRequest).subscribe({
          next: () => {
            this.errorMessage = null;
            this.router.navigate(['/profile']);
            this.messageService.add({ severity: 'success', summary: 'Modification enregistrée' });
          },
          error: (err) => {
            console.error(err);
            this.errorMessage = err.error != null ? err.error.message : err.message;
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
