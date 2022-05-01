import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Course} from "../entities/course";
import {environment} from "../../environments/environment.prod";
import {CourseModel} from "../model/courseModel";
import {Material} from "../entities/material";
import {MaterialModel} from "../model/materialModel";

@Injectable({
  providedIn: 'root'
})
export class MaterialsService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getMaterial(materialId: number): Observable<Material> {
    return this.http.get<Material>(`${this.apiServerUrl}/material/${materialId}`);
  }

  public getAllMaterials(): Observable<Material[]> {
    return this.http.get<Material[]>(`${this.apiServerUrl}/material`);
  }

  public addMaterial(materialModel: MaterialModel): Observable<void> {
    return this.http.post<void>(`${this.apiServerUrl}/material/by_course/${materialModel.courseId}`, materialModel);
  }

  public deleteMaterial(materialId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/material/${materialId}`);
  }

  public updateMaterial(materialId: number, materialModel: MaterialModel): Observable<void> {
    return this.http.put<void>(`${this.apiServerUrl}/material/${materialId}`, materialModel);
  }

  public makePublic(materialId: number): Observable<void> {
    return this.http.get<void>(`${this.apiServerUrl}/material/make_public/${materialId}`);
  }

  public makePrivate(materialId: number): Observable<void> {
    return this.http.get<void>(`${this.apiServerUrl}/material/make_private/${materialId}`);
  }

}
