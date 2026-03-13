import React, { useState } from "react";
import authApi from "../api/AuthApi";



function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleLogIn = async () => {
        try {
            const response = await authApi.get("/token",
                { 
                    params: {email, password}
                });

            console.log("Response:", response.data);

            if (response.data.token) {
                localStorage.setItem("token", response.data.token);
                localStorage.setItem("userId", response.data.userId);
                alert("Login successful!");
                window.location.href="/";
            } else {
                alert("Invalid username or password");
            }
        } catch (error) {
            console.error(error);
            alert("login invalid");
        }
    };

    return (
        <div className="login-container">
            <h2>Login Page</h2>

            <input type="email" className="login-input" placeholder="email" onChange={(e) => setEmail(e.target.value)}></input>

            <br></br>

            <input type="password" className="login-input" placeholder="Password" onChange={(e) => setPassword(e.target.value)}></input>


            <br></br>

            <button className="login-btn" onClick={handleLogIn}>Login</button>
            <p>
                Don't have an account? <a href="/register">Register here</a>
            </p>

        </div>

    );

}

export default Login;

