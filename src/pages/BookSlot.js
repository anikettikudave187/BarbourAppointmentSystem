import React, { useEffect, useState } from "react";
import api from "../api/axios";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";

function BookSlot() {
    const { shopId, serviceId, date } = useParams();
    const [slots, setSlots] = useState([]);
    const userId = localStorage.getItem("userId");
    const token = localStorage.getItem("token");
    const navigate = useNavigate();

    useEffect(() => {
        if (!shopId || !serviceId || !date) return;
        api.get(`/slot/getOrCreateSlots`, {
            params: { shopId, serviceId, date }
        })
            .then(res => { setSlots(res.data); })
            .catch(err => { console.error(err); })
    }, [shopId, serviceId, date]);



    const handleSlot = (slotId) => {
        if (!token || !userId) {
            alert("Please login");
            navigate("/login");
            return;
        }

        api.put(`/slot/book/${slotId}/user/${userId}`, {}, {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => {
                const updatedSlot = res.data;

                setSlots(prev =>
                    prev.map(s =>
                        s.slotId === updatedSlot.slotId
                            ? {
                                ...s,
                                booked: true,
                                bookedByUser: userId
                            }
                            : s
                    )
                );
                alert("Slot booked successfully");
            })

            .catch(err => {
                const msg =
                    typeof err.response?.data === "string"
                        ? err.response.data
                        : "Slot already booked";

                alert(msg);
            });

    };

    const cancelSlot = (slotId) => {
        if (!token || !userId) {
            alert("Please login");
            navigate("/login");
            return;
        }

        api.put(`/slot/cancel/${slotId}/user/${userId}`, {}, {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => {
                const updatedSlot = res.data;

                setSlots(prev =>
                    prev.map(s =>
                        s.slotId === updatedSlot.slotId
                            ? {
                                ...s,
                                booked: false,
                                bookedByUser: null
                            }
                            : s
                    )
                );
            })

            .catch(err => {
                alert("Failed to cancel slot");
            });
    };

    const formatTime = (timeStr) => {
        if (!timeStr) { return ""; }
        const [hour, minute] = timeStr.split(":");
        const ampm = hour >= 12 ? "PM" : "AM";
        const formattedHour = hour % 12 || 12;
        return `${formattedHour}:${minute} ${ampm}`;
    }

    return (
        <div className="slots-container">
            <h2> Available slots </h2>

            {slots.length === 0 && <p>Slots not available</p>}

            <div className="slots-grid">
                {slots.map(slot => (
                    <div
                        key={slot.slotId}
                        className={`slots-card ${slot.booked ? "booked" : "available"}`}
                    >
                        <p>{formatTime(slot.startTime)} - {formatTime(slot.endTime)}</p>

                        {!slot.booked && (
                            <button onClick={() => handleSlot(slot.slotId)}>
                                Book Slot
                            </button>
                        )}

                        {slot.booked && slot.bookedByUser === userId && (
                            <button className="cancel-btn" onClick={() => cancelSlot(slot.slotId)}>Cancel Slot</button>
                        )}

                        {slot.booked && slot.bookedByUser !== userId && (
                            <button disabled>Booked</button>
                        )}


                    </div>
                ))}
            </div>




        </div>


    );
}

export default BookSlot;
