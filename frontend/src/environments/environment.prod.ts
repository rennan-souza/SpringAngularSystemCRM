const dotenv = require('dotenv')

export const environment = {
  production: true,
  apiUrlBase: dotenv.process.env.API_URL_BASE,
  clientId: dotenv.process.env.CLIENT_ID,
  clientSecret: dotenv.process.env.CLIENT_SECRET,
  urlBaseSignin: dotenv.process.env.API_URL_BASE_SIGNIN,
};
