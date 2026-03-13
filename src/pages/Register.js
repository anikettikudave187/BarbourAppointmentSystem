import React, { useState } from "react";
import axios from "axios";

function Register() {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [phoneNo, setPhoneNo] = useState("");
    const [pincode, setPincode] = useState("");
    const [state, setState] = useState("");
    const [address, setAddress] = useState("");

    const handleRegister = async () => {
        try {
            const body = {
                userId: null,
                name,
                userType: "customer",
                email,
                password,
                pincode: Number(pincode),
                state,
                phoneNo: Number(phoneNo),
                address
            };

            const response = await axios.post(
                "https://central-api-m33q.onrender.com/api/v1/central/appUser/create",
                body
            );

            console.log("Backend says:", response.data);

            alert("User registered successfully!");

            window.location.href = "/login";
        } catch (error) {
            console.log("Backend error:", error.response?.data);
            alert("Registration failed!");
        }
    };

    return (
        <div className="login-container">
            <h2>Register User</h2>

            <input type="text" className="login-input" placeholder="Name" onChange={e => setName(e.target.value)} />
            <input type="email" className="login-input" placeholder="Email" onChange={e => setEmail(e.target.value)} />
            <input type="password" className="login-input" placeholder="Password" onChange={e => setPassword(e.target.value)} />
            <input type="text" className="login-input" placeholder="Phone Number" onChange={e => setPhoneNo(e.target.value)} />
            <input type="text" className="login-input" placeholder="Pincode" onChange={e => setPincode(e.target.value)} />
            <input type="text" className="login-input" placeholder="State" onChange={e => setState(e.target.value)} />
            <input type="text" className="login-input" placeholder="Address" onChange={e => setAddress(e.target.value)} />

            <button className="login-btn" onClick={handleRegister}>Register</button>
        </div>
    );
}

export default Register;
