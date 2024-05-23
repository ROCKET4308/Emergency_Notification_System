import React from 'react';
import HomePageWithLoginCSS from './HomePage.module.css';
import { useNavigate } from 'react-router-dom';
import Message from './MessageList/Message';
import axios from 'axios';
import { useState, useEffect } from 'react';

const Home = (props) => {
  const [messages, setMessages] = useState([]);
  const [refreshMessages, setRefreshMessages] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const getMessages = async () => {
      try {
        await new Promise(resolve => setTimeout(resolve, 500));
        const response = await axios.get('http://localhost:8080/message-service/message/get');
        console.log('Response:', response.data);
        setMessages(response.data);
      } catch (error) {
        setMessages([]);
        console.error('Failed to get messages:', error);
      }
    };
    getMessages();
  }, [refreshMessages]);

  const createMessage = () => {
    navigate('/message/create'); 
  }

  const refreshMessageList = () => {
    setRefreshMessages(prevState => !prevState);
  };

  //TODO: set same icon to show avatar of user

  return (
    <div className={HomePageWithLoginCSS.mainContainer}>
      <div className={HomePageWithLoginCSS.buttonContainer}>
        <div className={HomePageWithLoginCSS.homeButton}>
          <button className={HomePageWithLoginCSS.inputButton} onClick={createMessage}>Create Message</button>
        </div>
        <div className={HomePageWithLoginCSS.inputButton}>
          <img></img>
        </div>
      </div>
      <div className={HomePageWithLoginCSS.messageContainer} onClick={refreshMessageList}>
        {Object.keys(messages).flat().map(message => (
          <Message key={message.id} message={message}/>
        ))}
      </div>
    </div>
  )
}

export default Home