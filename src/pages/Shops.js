import React, { useEffect, useState } from "react";
import api from "../api/axios";
import { useNavigate } from "react-router-dom";
import BookShop from "./BookShop";

function Shops() {
    const [shops, setShops] = useState([]);
    const navigate = useNavigate();
    const token = localStorage.getItem("token");

    const [pincode, setPincode] = useState("");

    const [loading, setLoading] = useState(false);

    const [query, setQuery] = useState("");
    const [city, setCity] = useState("");
    const [cities, setCities] = useState([]);
    const [listening, setListening] = useState(false);
    const [showSuggesions, setShowSuggesions] = useState(false);


    useEffect(() => {
        api.get("/shop/all")
            .then(res => { setShops(res.data); })
            .catch(err => console.log(err));
    }, []);

    useEffect(() => {
        api.get("/shop/distinctCities")
            .then(res => {
                setCities(res.data);
            })
            .catch(err => {
                console.log(err);
                alert("Error fetching cities");
            });
    }, []);



    const handlePincodeSearch = () => {
        if (pincode.trim() === "") {
            alert("Please enter a valid pincode to search!");
            return;
        }
        api.get(`/shop/getShopsByPincode?pincode=${pincode}`)
            .then(res => {
                setShops(res.data);
            })
            .catch(err => {
                console.log(err);
                alert("Error fetching shops by pincode");
            });
    };

    const handleSearch = (city) => {
        if (city.trim() === "") {
            alert("Please enter a search city!");
            return;
        }
        setLoading(true);
        api.get(`/shop/getShopsByLocation?city=${(city).trim()}`)
            .then(res => {
                setShops(res.data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                alert("Error searching shops");
                setLoading(false);
            });
    }

    const startListening = () => {
        const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
        if (!SpeechRecognition) {
            alert("Speech Recognition API is not supported in this browser.");
            return;
        }
        const recognition = new SpeechRecognition();
        recognition.lang = 'en-IN';
        recognition.interimResults = false;

        recognition.onresult = (event) => {
            const spokenCity = event.results[0][0].transcript;
            setQuery(spokenCity);
            setCity(spokenCity);
            
        };
        recognition.onerror = (event) => {
            console.error("Speech recognition error:", event.error);
            alert("Error occurred in speech recognition: " + event.error);
        };

        recognition.onend = () => {
            setListening(false);
        };

        recognition.start();
        setListening(true);
    }

    const filteredCities = query.length > 0 ? cities.filter(city => city.toLowerCase().includes(query.toLowerCase())) : [];

    const handleBook = (shopId) => {

        navigate(`/shop/${shopId}`);


    };

    if (shops.length === 0) {
        return <h2 style={{ textAlign: "center" }}>No shops available</h2>
    }


    return (
        <div >
            <div className="search-section">
                <div className="search-box">
                    <div className="search-controls">
                        <input
                            type="text"
                            placeholder="Enter city to search shops"
                            value={query}
                            onChange={(e) => {
                                setQuery(e.target.value);
                                setShowSuggesions(true);
                            }}
                            onKeyDown={(e)=>e.key === "Enter" && handleSearch(query)}
                        />
                        
                        <button onClick={() => handleSearch(query)}>Search</button>
                        <button onClick={startListening}>
                            {listening ? "Listening..." : "🎤 Voice"}
                        </button>
                    </div>

                    {showSuggesions && filteredCities.length > 0 && query.length > 0 && (
                        <ul className="suggestions">
                            {filteredCities.slice(0, 5).map(city => (
                                <li
                                    key={city}
                                    onClick={() => {
                                        setQuery(city);
                                        handleSearch(city);
                                        setShowSuggesions(false); // hide after click
                                    }}
                                >
                                    {city}
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </div>



            <div className="shop-list">
                {shops.map(shop => (
                    shop.owner ? (
                        <div className="shop-card" key={shop.id}>
                            <img src="https://images.unsplash.com/photo-1503951914875-452162b0f3f1"
                                alt="shopImg"></img>


                            <h3>{shop.name}</h3>
                            <p>{shop.address}</p>
                            <p>{shop.city}</p>
                            <p>{shop.status}</p>


                            <button onClick={() => handleBook(shop.id)}>Book</button>

                        </div>
                    ) : null
                ))}
            </div>
        </div >
    );
}

export default Shops;