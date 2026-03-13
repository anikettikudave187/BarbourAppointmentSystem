import axios from "axios";
import React, { use, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

function Home() {
  const navigate = useNavigate();

  const [reviews, setReviews] = React.useState([]);

  const[comment, setComment] = React.useState("");
  const [ratings, setRatings] = React.useState(5);
  const [userName, setUserName] = React.useState("");

  const token= localStorage.getItem("token");
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    api.get("/review/getAllReviews")
      .then(res => res.data)
      .then(data => setReviews(data))
      .catch(err => console.error(err));
  }, []);

  useEffect(() => {
    api.get(`/appUser/${userId}`)
      .then(res => res.data)
      .then(user => setUserName(user.name))
      .catch(err => console.error(err));
  }, [userId]);

  const handleSubmitReview = () => {
    if(!token || !userId){
      alert("Please login to submit a review");
      navigate("/login");
      return;
    }
    const body={
      comment,
      ratings,
      userName
    }

    const response = api.post("/review/addReview", body, {
      headers: { Authorization: `Bearer ${token}` }
    })
    .then(res => {
      alert("Review submitted successfully");
      setReviews(prev => [...prev, res.data]);
      setComment("");
      setRatings(5);
    })
    .catch(err => {
      console.error(err);
      alert("Failed to submit review");
    });
  }

  return (

    <div>
      <div className="hero">
        <h1>Barber Booking System</h1>
        <p>Your style, our responsibility</p>
        <button className="hero-btn" onClick={() => navigate("/shops")}>Explore Shops</button>
      </div>

      <div className="section light">
        <h2>Our Services</h2>

        <div className="card-row">
          <div className="info-card">💇 Haircut</div>
          <div className="info-card">🧔 Beard Grooming</div>
          <div className="info-card">✨ Styling</div>
          <div className="info-card">💆 Facial & Spa</div>
        </div>
      </div>

      <div className="section">
        <h2>Why Choose Us</h2>

        <div className="card-row">
          <div className="info-card">✅ Verified Barbers</div>
          <div className="info-card">⏱ Easy Booking</div>
          <div className="info-card">⭐ Trusted Reviews</div>
        </div>
      </div>

      <div className="section light">
        <h2>Happy Customers</h2>

        <div className="card-row">
          {reviews.slice(-4).map((review, index) => (
            <div key={index} className="review-card">
              {"⭐".repeat(review.ratings)}
              <p>{review.comment}</p>
              <p className="reviewer">- {review.userName}</p>
            </div>
           ))}
        </div>
        <div className="review-form">
          <h3>Rate Us</h3>
          <textarea
            placeholder="Write your review here..."
            value={comment}
            onChange={e => setComment(e.target.value)}
          />
          <div>
            <label>Rating:</label>
            <select value={ratings} onChange={e => setRatings(parseInt(e.target.value))}>
              <option value={1}>1 Star</option>
              <option value={2}>2 Stars</option>
              <option value={3}>3 Stars</option>
              <option value={4}>4 Stars</option>
              <option value={5}>5 Stars</option>
            </select>
          </div>
          <button onClick={handleSubmitReview}>Submit Review</button>
        </div>

      </div>

      <div className="section">
        <h2>About Us</h2>
        <p className="about-text">
          We connect customers with the best barbers in their city using a simple
          and reliable online booking platform.
        </p>
      </div>

      
    </div>


  );
}

export default Home;
