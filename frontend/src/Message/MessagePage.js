import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import MessagePageCSS from './MessagePage.module.css'; // Assuming you have a CSS module for styling

function MessagePage() {
  const { messageName } = useParams();
  const [messageStatus, setMessageStatus] = useState({});

  const handleButtonClick = () => {
    axios.post(`http://localhost:8080/message-service/message/sent/${messageName}`)
      .then(response => {
        console.log('API Response:', response.data);
        setMessageStatus(response.data);
      })
      .catch(error => {
        console.error('API Error:', error);
      });
  };

  return (
    <div className={MessagePageCSS.SentMassagePage}>
      <div>
        <h2>{messageName}</h2>
      </div>
      <div className={MessagePageCSS.DragNDrop}>
        <ul>
          {Object.entries(messageStatus).map(([email, status]) => (
            <div>
              <strong>{email}</strong>: {status}
            </div>
          ))}
        </ul>
      </div>
      <button onClick={handleButtonClick}>Sent notification</button>
    </div>
  );
}

export default MessagePage;


