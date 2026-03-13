import React, { useEffect, useState } from "react";
import api from "../api/axios";
import { useParams, useNavigate } from "react-router-dom";

function BookShop() {
    const { shopId } = useParams();
    const navigate = useNavigate();
    const token = localStorage.getItem("token");

    const [shop, setShop] = useState(null);
    const [serviceIds, setServiceIds] = useState([]);
    const [services, setServices] = useState([]);

    const [owner, setOwner] = useState(null);


    // 1️⃣ Load shop details
    useEffect(() => {
        api.get(`/shop/${shopId}`)
            .then(res => setShop(res.data))
            .catch(err => console.error(err));
    }, [shopId]);




    // 2️⃣ Load service IDs
    useEffect(() => {
        api.get(`/shop/getServicesByShop/${shopId}`)
            .then(res => {
                console.log("Service IDs:", res.data);
                setServiceIds(res.data);
            })
            .catch(err => console.error(err));
    }, [shopId]);

    // 3️⃣ Load full service objects
    useEffect(() => {
        if (serviceIds.length === 0) return;

        Promise.all(
            serviceIds.map(id =>
                api.get(`/shopService/${id}`).then(res => res.data)
            )
        )
            .then(fullServices => {
                console.log("Full services:", fullServices);
                setServices(fullServices);
            })
            .catch(err => console.error(err));
    }, [serviceIds]);

    // 4️⃣ Load owner details

    useEffect(() => {
        if (!shop || !shop.owner) return;

        api.get(`/appUser/${shop.owner}`)
            .then(res => setOwner(res.data))
            .catch(err => console.error(err));
    }, [shop]);

    const handleSlot = (serviceId) => {
        if (!token) {
            alert("Please login to book");
            navigate("/login");
        } else {
            const today = new Date().toISOString().split('T')[0];
            navigate(`/service/${serviceId}/bookSlot/${shopId}/${today}`);
        }
    };

    if (!shop) {
        return <h2 style={{ textAlign: "center" }}>Loading shop...</h2>;
    }

    return (
        <div className="shop-container">
            <h2>{shop.name}</h2>
            <img
                src="https://images.unsplash.com/photo-1503951914875-452162b0f3f1"
                alt="shop"
            />
            {owner && (
                <>
                    <p>Owner: {owner.name}</p>
                    <p>Phone No: {owner.phoneNo}</p>
                </>
            )}

            <p>{shop.address}</p>
            <p>Status: {shop.status}</p>

            <h3>Shop Services</h3>

            {services.length === 0 && <p>No services available</p>}

            <div className="shop-list">
                {services.map(service => (
                    <div className="shop-card" key={service.id}>
                        <h4>Service: {service.serviceName}</h4>
                        <p>₹ {service.price}</p>
                        <p>Duration: {service.duration} mins</p>
                        <p>{service.ratings}⭐ </p>

                        <button onClick={() => handleSlot(service.id)}>
                            Book
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default BookShop;
