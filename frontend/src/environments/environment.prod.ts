
export const environment = {
  production: true,
  apiUrlBase: process.env["API_URL_BASE"] as string,
  clientId: process.env["CLIENT_ID"] as string,
  clientSecret: process.env["CLIENT_SECRET"] as string,
  urlBaseSignin: process.env["API_URL_BASE_SIGNIN"] as string,
};
