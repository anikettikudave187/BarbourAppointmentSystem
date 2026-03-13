import React,{ useState, useEffect } from "react";
import axios from "axios";

function AddShop(){
    const [name, setName] = useState("");
    const [address, setAddress] = useState("");
    const[city, setCity]=useState("");
    const [pincode, setPincode] = useState("");
    const [shopServices, setShopServices] = useState([]);
    const [ownerId, setOwnerId] = useState("");
    const [state, setState] = useState("");
    const [status, setStatus] = useState("ACTIVE");

    useEffect(() => {
        const userId = localStorage.getItem("userId");
        if (userId) {
            setOwnerId(userId);
        }
    }, []);

    const handleAddShop = async () => {
        try{
            const token=localStorage.getItem("token");
            if(!token){
                alert("Please login first!");
                window.location.href="/login";
                return;
            }
            const body = {
                id: null,
                name,
                address,
                city,
                pincode: Number(pincode),
                shopServiceSet:[],
                owner:ownerId,
                state,
                status
            };

            const response = await axios.post(
                "https://central-api-m33q.onrender.com/api/v1/central/shop/create",
                body,{
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );
            
            alert("Shop added successfully!");
            window.location.href = "/shops";
        }
        catch(e){
            console.error(e);
            alert(e.response?.data || "Failed to add shop!");
        }
    }

    return(
        <div className="login-container">
            <h2>Add Shop</h2>
            <input type="text" className="login-input" placeholder="Shop Name" onChange={e => setName(e.target.value)} />
            <input type="text" className="login-input" placeholder="Address" onChange={e => setAddress(e.target.value)} />
            <input type="text" className="login-input" placeholder="City" onChange={e => setCity(e.target.value)} />
            <input type="text" className="login-input" placeholder="Pincode" onChange={e => setPincode(e.target.value)} />
            <input type="text" className="login-input" placeholder="State" onChange={e => setState(e.target.value)} />
            <input value={ownerId} readOnly></input>
            <button className="login-btn" onClick={handleAddShop}>Add Shop</button>
        </div>
    );
}
export default AddShop;