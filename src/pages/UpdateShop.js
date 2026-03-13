import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";
import UpdateService from "./UpdateService";


function UpdateShop() {
    const navigate = useNavigate();
    const { shopId } = useParams();
    const token = localStorage.getItem("token");
    const [name, setName] = useState("");
    const [address, setAddress] = useState("");
    const [pincode, setPincode] = useState("");
    const [state, setState] = useState("");
    const [status, setStatus] = useState("");
    const[city, setCity]=useState("");

    const [owner, setOwner] = useState("");
    const ownerId = localStorage.getItem("userId");

    const [shop, setShop] = useState(null);

    // For services
    const [services, setServices] = useState([]);
    const [showAddService, setShowAddService] = useState(false);

    const [serviceName, setServiceName] = useState("");
    const [price, setPrice] = useState("");
    const [duration, setDuration] = useState("");
    const [ratings, setRatings] = useState("");

    useEffect(() => {
        api.get(`/shop/${shopId}`)
            .then(res => {
                const s = res.data;
                setShop(s);

                // copy to form state
                setName(s.name);
                setAddress(s.address);
                setCity(s.city);
                setPincode(s.pincode);
                setState(s.state);
                setStatus(s.status || "ACTIVE");

            })
            .catch(err => console.error(err));
    }, [shopId]);

    useEffect(() => {
        api.get(`/shop/getServicesByShop/${shopId}`)
            .then(async res => {
                const ids = res.data;

                const serviceResponses = await Promise.all(ids.map(id => api.get(`/shopService/${id}`)));

                const fullServices = serviceResponses.map(r => r.data);

                setServices(fullServices);
            })
            .catch(err => console.error(err));


    }, [shopId]);

    const refreshServices = async () => {
    try {
        const idsResponse = await api.get(`/shop/getServicesByShop/${shopId}`);
        const ids = idsResponse.data;

        const results = await Promise.allSettled(
            ids.map(id => api.get(`/shopService/${id}`))
        );

        const successful = results
            .filter(r => r.status === "fulfilled")
            .map(r => r.value.data);

        setServices(successful);
    } catch (err) {
        console.error("Error fetching service IDs:", err);
    }
};


    






    const handleUpdateShop = async () => {
        try {
            if (!token) {
                alert("Please login first!");
                navigate("/login");
                return;
            }

            const body = {
                id: shopId,
                name,
                address,
                city,
                pincode: Number(pincode),
                owner: ownerId,
                state,
                status
            };

            const response = await api.put(`/shop/update/${shopId}`, body, {
                headers: { Authorization: `Bearer ${token}` }
            });
            alert("Shop updated successfully!");
            navigate("/bepart");
        } catch (e) {
            console.error(e);
            alert(e.response?.data || "Failed to update shop!");
        }
    };

    const removeService = async (serviceId) => {
        await api.put(`/shopService/removeService/shop/${shopId}/service/${serviceId}`);
        setServices(prev => prev.filter(s => s.id !== serviceId));
        refreshServices();        
    };


    const addService = async () => {
        const res = await api.put(`/shopService/addService/${shopId}`, {
            serviceName,
            price: Number(price),
            duration: Number(duration),
            ratings: Number(ratings)
        });

        setServices(prev => [...prev, res.data]);
        setShowAddService(false);
        setServiceName("");
        setPrice("");
        setDuration("");
        setRatings("");

        refreshServices();
       
    }

    const handleBookedSlotsInfo = (serviceId) => {
        navigate(`/bookedSlotsInfo/${shopId}/${serviceId}/${new Date().toISOString().split("T")[0]}`);
    }


    if (!shop) {
        return <p>Loading shop details...</p>;
    }

    return (
        <>
            <div className="login-container">
                <h2>Update Shop</h2>

                <input className="login-input" placeholder="name" value={name} onChange={e => setName(e.target.value)} />
                <input className="login-input" placeholder="address" value={address} onChange={e => setAddress(e.target.value)} />
                <input className="login-input" placeholder="city" value={city} onChange={e => setCity(e.target.value)} />
                <input className="login-input" placeholder="pincode" value={pincode} onChange={e => setPincode(e.target.value)} />
                <input className="login-input" placeholder="state" value={state} onChange={e => setState(e.target.value)} />

                <select className="login-input" value={status} onChange={e => setStatus(e.target.value)}>
                    <option value="ACTIVE">ACTIVE</option>
                    <option value="INACTIVE">INACTIVE</option>
                </select>

                <button className="login-btn" onClick={handleUpdateShop}>Update Shop</button>
                <button onClick={() => setShowAddService(true)}>Add Service</button>




            </div>

            <hr />

            <h3>Services</h3>

            <div className="shop-list">
                {services.map(s => (
                    <div className="shop-card" key={s.id}>
                        <h4>{s.serviceName}</h4>
                        <p>Price: {s.price}</p>
                        <p>Duration: {s.duration} mins</p>
                        <p>Ratings: {s.ratings}</p>
                        <button className="login-btn" onClick={() => navigate(`/updateService/${s.id}`)}>Update Service</button>
                        <button className="login-btn" onClick={() => handleBookedSlotsInfo(s.id)}>Booked Slots Info</button>
                        <button className="delete-btn" onClick={() => removeService(s.id)}>Delete</button>
                    </div>
                ))}
            </div>
            {showAddService && (
                <div className="login-container">
                    <h2>Add Service</h2>
                    <input className="login-input" type="text" placeholder="Service Name" value={serviceName} onChange={e => setServiceName(e.target.value)} />
                    <input className="login-input" type="number" placeholder="Price" value={price} onChange={e => setPrice(e.target.value)} />
                    <input className="login-input" type="number" placeholder="Duration (minutes)" value={duration} onChange={e => setDuration(e.target.value)} />
                    <input className="login-input" type="number" placeholder="Ratings" value={ratings} onChange={e => setRatings(e.target.value)} />

                    <button className="login-btn" onClick={addService}>Add Service</button>
                    <button className="delete-btn" onClick={() => setShowAddService(false)}>Cancel</button>
                </div>
            )}


        </>


    );
}

export default UpdateShop;