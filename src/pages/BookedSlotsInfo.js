import React, { useState, useEffect } from "react";
import api from "../api/axios";
import { useNavigate, useParams } from "react-router-dom";

function BookedSlotsInfo() {
  const navigate = useNavigate();
  const userId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");
  const [bookedSlots, setBookedSlots] = useState([]);
  const [bookedUsers, setBookedUsers] = useState({}); // Map of userId -> user details

  const { shopId, serviceId, date } = useParams();

  useEffect(() => {
    if (!token || !userId) {
      alert("Please login");
      navigate("/login");
      return;
    }

    api.get(`/slot/getBookedSlotsInfo?shopId=${shopId}&serviceId=${serviceId}&date=${date}`)
      .then(res => {
        setBookedSlots(res.data);

        res.data.forEach(slot => {
          if (slot.bookedByUser && !bookedUsers[slot.bookedByUser]) {
            api.get(`/appUser/${slot.bookedByUser}`)
              .then(userRes => {
                setBookedUsers(prev => ({
                  ...prev,
                  [slot.bookedByUser]: userRes.data
                }));
              })
              .catch(err => {
                console.error(`Failed to fetch user info for userId ${slot.bookedByUser}:`, err);
              });
          }
        });
      })
      .catch(err => {
        console.error(err);
        alert("Failed to fetch booked slots info");
      });
  }, [shopId, serviceId, date]);

  function formatHourRange(startTime, endTime) {
    const formatHour = (time) => {
      const [hour, minute] = time.split(":");
      const h = parseInt(hour);
      const m = parseInt(minute);
      const ampm = h >= 12 ? "PM" : "AM";
      const formattedHour = ((h + 11) % 12 + 1) + (m > 0 ? `:${minute}` : "");
      return `${formattedHour} ${ampm}`;
    };

    return `${formatHour(startTime)} - ${formatHour(endTime)}`;
  }

  return (
    <div className="booked-slots-info">
      <h2>Booked Slots for {date}</h2>
      {bookedSlots.length === 0 ? (
        <p>No booked slots available for this date.</p>
      ) : (
        <div>
          {bookedSlots.map(slot => {
            const user = bookedUsers[slot.bookedByUser];
            return (
              <div key={slot.slotId} className="booked-slot-card">
                <p>Booked By: {user ? `${user.name}` : "Loading user info..."}</p>
                <p>Slot Time: {formatHourRange(slot.startTime, slot.endTime)}</p>
                
                <p>Email: {user ? user.email : "N/A"}</p>
                <p>Phone No: {user ? user.phoneNo : "N/A"}</p>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}

export default BookedSlotsInfo;
