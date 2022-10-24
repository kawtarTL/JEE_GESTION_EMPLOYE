export interface UserToken{
  subject:string,
  audience:string[],
  roles:string[],
  issuer:string,
  expiration_date:Date
}
