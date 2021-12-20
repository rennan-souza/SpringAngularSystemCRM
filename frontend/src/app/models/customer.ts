export class Customer {
    id?: number;
    firstName?: string;
    lastName?: string;
    cpf?: string;
    birthDate?: Date;
    email?: string;
}

export interface CustomerPage {
    content?: Customer[];
    last: boolean;
    totalElements: number;
    totalPages: number;
    size?: number;
    number: number;
    first: boolean;
    numberOfElements?: number;
    empty?: boolean;
  }