import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

function AiChatBot() {
    const navigate = useNavigate();
    const [messages, setMessages] = React.useState([])
    const [input, setInput] = React.useState("")
    const [open, setOpen] = React.useState(false);
    const messagesEndRef = React.useRef(null);

    const sendMessage = async () => {
        if (input.trim() === "") return;

        const newMessage = [...messages, { text: input, sender: "user" }]
        setMessages(newMessage)

        try {
            const res = await fetch(`https://central-api-m33q.onrender.com/api/v1/central/ai/askAI?question=${encodeURIComponent(input)}`);
            const data = await res.text();
            setMessages([...newMessage, { text: data, sender: "ai" }]);
        } catch (error) {
            setMessages([...newMessage, { text: "Sorry, something went wrong. Please try again later.", sender: "ai" }]);
        }
        setInput("");
    };
    useEffect(() => {
        if (messagesEndRef.current) {
            messagesEndRef.current.scrollIntoView({ behavior: "smooth" });
        }
    }, [messages])

    return (
        <div className="chatbot-container">
            {open ? (
                <div className="chatbot-box">
                    <div className="chatbot-header">
                        <span>Ask AI</span>
                        <button onClick={() => setOpen(false)}>✖</button>
                    </div>
                    <div className="chatbot-messages">
                        {messages.map((msg, i) => (
                            <div key={i} className={`message ${msg.sender}`}>
                                {msg.text}
                            </div>

                        ))}
                        <div ref={messagesEndRef} />
                    </div>
                    <div className="chatbot-input">
                        <input type="text" value={input} onChange={(e) => setInput(e.target.value)}
                            placeholder="Type your question..."
                            onKeyDown={(e) => e.key === "Enter" && sendMessage()}
                        />
                        <button onClick={sendMessage}>Send</button>
                    </div>
                </div>
            ) : (
                <button className="chatbot-toggle" onClick={() => setOpen(true)}>
                    💬 Chat
                </button>
            )}
        </div>
    )
}

export default AiChatBot;