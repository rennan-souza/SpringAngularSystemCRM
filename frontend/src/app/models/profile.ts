export class Profile {
    id?: number;
    firstName?: string;
    lastName?: string;
    email?: string;
    password?: string;
}

export class UpdatePassword {
    password?: string;
    newPassword?: string;
    newPasswordConfirmation?: string;
}