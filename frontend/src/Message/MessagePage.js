import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { useEffect } from 'react';
import MessagePageCSS from './MessagePage.module.css';

function MessagePage() {
  const { messageName } = useParams();
  const [messageText, setMessageText] = useState('');
  const [messageContacts, setMessageContacts] = useState([]);

  const handleButtonClick = async () => {
    try {
      const response = await axios.post(`http://localhost:8080/message-service/message/sent/${messageName}`);
      console.log('API Response:', response.data);
      setMessageContacts(prevContacts =>
        prevContacts.map(contact => ({
          email: contact.email,
          status: response.data[contact.email]
        }))
      );
    } catch (error) {
      console.error('API Error:', error);
    }
  };

  useEffect(() => {
    const getMessage = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/message-service/message/get/${messageName}`);
        console.log('Response:', response.data);
        setMessageText(response.data.messageText);
        setMessageContacts(response.data.recipientContacts.map(email => ({ email, status: 'Not sent' })));
      } catch (error) {
        console.error('Failed to get message:', error);
      }
    };
    getMessage();
  }, [messageName]);

  return (
    <div className={MessagePageCSS.mainContainer}>
        <h2>{messageName}</h2>
        <p className={MessagePageCSS.messageContainer}>{messageText}</p>
        <ul className={MessagePageCSS.messageList}>
          {messageContacts.map(contact => (
            <li key={contact.email}>
              <strong>{contact.email}</strong>: {contact.status}
            </li>
          ))}
        </ul>
        <button onClick={handleButtonClick} className={MessagePageCSS.inputButton}>Create message</button>
    </div>
  );
}

export default MessagePage;


