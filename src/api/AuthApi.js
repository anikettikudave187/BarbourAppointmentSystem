import axios from "axios";

const AuthApi = axios.create({
    baseURL: "https://anikettikudave-auth-api.onrender.com/api/v1/auth",
});

export default AuthApi;