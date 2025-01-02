import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {InputText} from 'primeng/inputtext';
import {LoginRequest} from '../../models/loginRequest';
import {Button} from 'primeng/button';
import {AuthService} from '../../services/auth.service';
import {BearerResponse} from '../../models/bearerResponse';
import {TokenService} from '../../services/token.service';
import {Router} from '@angular/router';
import {Password} from 'primeng/password';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule,
    InputText,
    Button,
    Password,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {

  formGroup!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private tokenService: TokenService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.initFormControls()
  }

  initFormControls() {
    this.formGroup = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    })
  }

  onLogin() {
    if (this.formGroup.valid) {
      const loginRequest: LoginRequest = {
        email: this.formGroup.value.email,
        password: this.formGroup.value.password,
      }
      this.authService.login(loginRequest).subscribe({
          next: (response: BearerResponse) => {
            this.tokenService.setTokens(response)
            this.router.navigate(['/articles']);
          },
          error: (err) => {
            console.error(err);
          }
        }
      )
    }
  }
}

