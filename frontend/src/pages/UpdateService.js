import React,{useState,useEffect} from "react";
import { useParams } from "react-router-dom";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";

function UpdateService(){
    const navigate=useNavigate();
    const {serviceId}=useParams();
    const token=localStorage.getItem("token");
    const [serviceName,setServiceName]=useState("");
    const [price,setPrice]=useState("");
    const [duration,setDuration]=useState("");
    const [ratings,setRatings]=useState("");
    const [shopService,setShopService]=useState(null);

    

    useEffect(()=>{
        api.get(`/shopService/${serviceId}`)
        .then(res=>{
            const s=res.data;
            setShopService(s);

            setServiceName(s.serviceName);
            setPrice(s.price);
            setDuration(s.duration);
            setRatings(s.ratings);
        })
        .catch(err=> console.error(err));
    }, [serviceId]);

    const handleUpdateService=async()=>{
        try{
            if(!token){
                alert("Please login first!");
                return;
            }
            const body={
                id: serviceId,
                serviceName,
                price: Number(price),
                duration: Number(duration),
                ratings: Number(ratings),
                
            }
            const response=await api.put(`/shopService/update/${serviceId}`, body, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            alert("Service updated successfully!");
            navigate("/bepart");
        }
        catch(e){
            console.error(e);
            alert(e.response?.data || "Failed to update service!");
        }
    };

    
    if(!shopService){
        return<h2 style={{textAlign:"center"}}>Loading service details...</h2>
    }

    return (
        <div className="login-container">
            <h2>Update Service</h2>
            <input className="login-input" type="text" placeholder="Service Name" value={serviceName} onChange={e=>setServiceName(e.target.value)} />
            <input className="login-input" type="number" placeholder="Price" value={price} onChange={e=>setPrice(e.target.value)} />
            <input className="login-input" type="number" placeholder="Duration (minutes)" value={duration} onChange={e=>setDuration(e.target.value)} />
            <input className="login-input" type="number" placeholder="Ratings" value={ratings} onChange={e=>setRatings(e.target.value)} />
            
            <button className="login-btn" onClick={handleUpdateService}>Update Service</button>
        </div>
    )
}

export default UpdateService;