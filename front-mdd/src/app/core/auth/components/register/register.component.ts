import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {TokenService} from '../../services/token.service';
import {Router} from '@angular/router';
import {BearerResponse} from '../../models/bearerResponse';
import {RegisterRequest} from '../../models/registerRequest';
import {Button} from 'primeng/button';
import {InputText} from 'primeng/inputtext';
import {Password} from 'primeng/password';
import {Message} from 'primeng/message';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-register',
  imports: [
    Button,
    InputText,
    Password,
    ReactiveFormsModule,
    Message,
    NgIf,
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent implements OnInit {

  formGroup!: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private tokenService: TokenService,
    private router: Router,
  ) {
  }

  ngOnInit() {
    this.initFormControls()
  }


  initFormControls() {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>])[A-Za-z\d!@#$%^&*(),.?":{}|<>]{8,}$/

    this.formGroup = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.pattern(passwordRegex)]],
    })
  }

  onRegister() {
    if (this.formGroup.valid) {
      const registerRequest: RegisterRequest = {
        username: this.formGroup.value.username,
        email: this.formGroup.value.email,
        password: this.formGroup.value.password,
      }
      this.authService.register(registerRequest).subscribe({
          next: (response: BearerResponse) => {
            this.tokenService.setTokens(response)
            this.router.navigate(['/articles']);
          },
          error: (err) => {
            console.error(err);
            this.errorMessage = err.error != null ? err.error.message : err.message;
          }
        }
      )
    }
  }

  goHome() {
    this.router.navigate(['/'])
  }
}
