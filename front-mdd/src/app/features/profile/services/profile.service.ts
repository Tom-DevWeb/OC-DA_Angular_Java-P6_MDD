import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ModifyUserRequest} from '../models/modifyUserRequest';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private baseUrl: string = "/api/users"


  constructor(private http: HttpClient) { }

  updateUser(userId: number, body: ModifyUserRequest) {
    return this.http.put(`${this.baseUrl}`, body)
  }

}
