import React, { useState, useEffect } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";

function BePart() {
    const navigate = useNavigate();
    const userId = localStorage.getItem("userId");
    const token = localStorage.getItem("token");

    const [shops, setShops] = useState([]);

    useEffect(() => {
        if (!token) {
            alert("Please login first!");
            navigate("/login");
            return;
        }
        api.get(`/shop/myShops/${userId}`)
            .then(res => {
                setShops(res.data);
            })
            .catch(err => {
                console.error("Error fetching shops:", err);
            });
    }, []);

    const refreshShops = () => {
        api.get(`/shop/myShops/${userId}`)
            .then(res => {
                setShops(res.data);
            })
            .catch(err => {
                console.error("Error refreshing shops:", err);
            });     
    }


    const handleDeleteShop = async (shopId) =>{
        try{
            if(!token){
                alert("Please login first!");
                navigate("/login");
                return;
            }
            await api.delete(`/shop/${shopId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            alert("Shop deleted successfully!");
            setShops(shops.filter(shop => shop.id !== shopId));
            refreshShops();
        }
        catch(e){
            console.error(e);
            alert(e.response?.data || "Failed to delete shop!");
        }
    }

    const handleUpdateShop = (shopId) => {
        navigate(`/updateShop/${shopId}`);
    }

    return (
        <div>
            {shops.length === 0 ? (
                <div className="noShopSection">
                    <p style={{ textAlign: "center" }}>You have no shops listed.</p>
                </div>
            ) : (
                <>
                    <h2 className="bepart-h2">My Shops</h2>
                    <div className="shop-list">
                        {shops.map(shop => (
                            <div className="shop-card" key={shop.id}>
                                <img src="https://images.unsplash.com/photo-1503951914875-452162b0f3f1"
                                    alt="shopImg"></img>
                                <h3>{shop.name}</h3>
                                <p>{shop.address}</p>
                                <p>{shop.status}</p>

                                <button onClick={() => handleUpdateShop(shop.id)}>Update Shop</button>
                                <button className="delete-btn" onClick={() => handleDeleteShop(shop.id)}>Delete Shop</button>
                                
                            </div>
                        ))}

                    </div>

                </>
            )
            }
            <div className="add-shop-btn-wrapper">
                <button onClick={() => navigate("/addShop")}>
                    Add New Shop
                </button>
            </div>
        </div>

    )
}

export default BePart;