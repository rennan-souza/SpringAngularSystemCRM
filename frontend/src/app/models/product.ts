import { Category } from "./category";

export class Product {
    id?: number;
    name?: string;
    description?: string;
    price?: number;
    file?: any;
    category?: Category;
}

export interface ProductResponse {
    id: number;
    name: string;
    description: string;
    price: number;
    imgBase64: string;
    category: Category;
}

export interface ProductPage {
    content?: ProductResponse[];
    last: boolean;
    totalElements: number;
    totalPages: number;
    size?: number;
    number: number;
    first: boolean;
    numberOfElements?: number;
    empty?: boolean;
  }