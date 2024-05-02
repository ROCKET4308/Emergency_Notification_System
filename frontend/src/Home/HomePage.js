import React from 'react';
import HomePageWithLoginCSS from './HomePage.module.css';
import { useNavigate } from 'react-router-dom';
import Message from './MessageList/Message';
import axios from 'axios';
import { useState, useEffect } from 'react';

const Home = (props) => {
  const [messages, setMessages] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const getMessages = async () => {
      try {
        const response = await axios.get('http://localhost:8080/message-service/message/get');
        console.log('Response:', response.data);
        setMessages(response.data);
      } catch (error) {
        console.error('Failed to get messages:', error);
      }
    };

    getMessages();
  }, []);


  const messagePage = (messageName) => {
    navigate(`/message/${messageName}`);
  }

  const createMessage = () => {
    navigate('/message/create'); 
  }

  const editMessage = () => {
    navigate('/message/edit'); 
  }
  //TODO: set same icon to show avatar of user
  //TODO: show user real template or write that there is no template for user

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
      <div className={HomePageWithLoginCSS.messageContainer}>
        {Object.keys(messages).flat().map(message => (
          <Message key={message.id} message={message} />
        ))}
      </div>
    </div>
  )
}

export default Home