import React from "react";
import {Link} from "react-router-dom";

function NavBar(){
    const token=localStorage.getItem("token");

    return(
        <div className="navbar">
            <h2>Barbour Booking</h2>

            <div className="nav-links">
                <li><Link to="/">Home</Link></li>
                <li><Link to="/shops">Shops</Link></li>
                <li><Link to="/bepart">Be a Part</Link></li>
            

                {!token && (
                    <>
                        <Link to="/login">Login</Link>
                        <Link to="/register">Register</Link>
                    </>
                )}

                {token && (
                    <button onClick={()=>{
                        localStorage.removeItem("token");
                        window.location.href="/";
                    }}>Logout</button>
                )}
            </div>
        </div>
    );
}

export default NavBar;