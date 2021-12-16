import { Role } from "./role";

export class User {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  roles?: Role[];
}

export interface UserPage {
  content?: User[];
  last: boolean;
  totalElements: number;
  totalPages: number;
  size?: number;
  number: number;
  first: boolean;
  numberOfElements?: number;
  empty?: boolean;
}