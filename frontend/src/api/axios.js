import axios from 'axios';

const api=axios.create({
    baseURL:"https://central-api-m33q.onrender.com/api/v1/central",
});

api.interceptors.request.use((config)=>{
    const token=localStorage.getItem("token");

    if(token){
        config.headers.Authorization=`Bearer ${token}`;
    }
    return config;
});

export default api;